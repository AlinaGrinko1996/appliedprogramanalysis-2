package sockets.api.general;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatServer {

    private ServerSocket socket;
    private ArrayList<ClientConnection> connections;
    private Lock connectionsLock;
    private Thread connectionListenerThread;
    private Thread clientReaderThread;

	public static void main(String[] args) {
		try {
			new ChatServer().start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void start() throws IOException {
        socket = new ServerSocket(9999);

        connections = new ArrayList<>();
        connectionsLock = new ReentrantLock();

        connectionListenerThread = new Thread(this::connectionListener);
//        connectionListenerThread.setDaemon(true);
        connectionListenerThread.start();

        clientReaderThread = new Thread(this::clientDataListener);
        clientReaderThread.setDaemon(true);
        clientReaderThread.start();
    }

    private void connectionListener() {
        while (true) {
            try {
                Socket clientSocket = socket.accept();
                System.out.println(clientSocket.toString());
                ClientConnection connection = new ClientConnection();
                connection.setSocket(clientSocket);
                connection.setFileTransferStartListener(this::onFileTransferStart);
                connection.setFileTransferProgressListener(this::onFileTransferProgress);
                connection.setFileTransferEndListener(this::onFileTransferEnd);
                connection.setMessageListener(this::onMessage);
                connection.setDisconnectionListener(this::onDisconnected);

                connectionsLock.lock();
                connections.add(connection);
                connectionsLock.unlock();

                System.out.printf("Client connected: %s\n", clientSocket.getInetAddress().getHostAddress()+", "+ clientSocket.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clientDataListener() {
        while (true) {
            if (connectionsLock.tryLock()) {
                connections.forEach(ClientConnection::read);

                connections.removeIf(ClientConnection::isDisconnected);

                connectionsLock.unlock();
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void onFileTransferStart(FileTransfer fileTransfer) {
        System.out.printf("File transfer started: %s\n", fileTransfer.getFileName());
        connections.forEach(connection -> {
            if (connection != fileTransfer.getClientConnection()) {
                connection.startSendFile(fileTransfer.getFileBytes().length, fileTransfer.getFileName());
            }
        });
    }

    private void onFileTransferProgress(FileTransfer fileTransfer) {
        System.out.printf("File transfer progress: %f\n", (float) fileTransfer.getCurrentOffset() / fileTransfer.getFileBytes().length);
        connections.forEach(connection -> {
            if (connection != fileTransfer.getClientConnection()) {
                connection.sendFileChunk(fileTransfer.getFileBytes(), fileTransfer.getCurrentOffset() - fileTransfer.getLastChunkSize(), fileTransfer.getLastChunkSize());
            }
        });
    }

    private void onFileTransferEnd(FileTransfer fileTransfer) {
        System.out.printf("File transfer end\n");
        connections.forEach(connection -> {
            if (connection != fileTransfer.getClientConnection()) {
                connection.endSendFile();
            }
        });
    }

    private void onMessage(Message message) {
        connections.forEach(connection -> {
            if (connection != message.getFromConnection()) {
                connection.send(message.getMessage());
            }
        });
        System.out.println(message.getMessage());
    }

    private void onDisconnected(ClientConnection clientConnection) {
        System.out.printf("Client disconnected: %s\n", clientConnection.getSocket().getInetAddress().getHostAddress());
    }

}

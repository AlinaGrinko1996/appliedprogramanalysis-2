package sockets.api.general;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    private final static int fileChunkSize = 2048;

    private Socket socket;
    private ClientConnection clientConnection;
    private Thread readerThread;

    private KeyboardInputReader keyboardInputReader;

    public static void main(String[] args){
          try {
			new ChatClient().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void start() throws IOException {
        socket = new Socket("localhost", 9999);
        clientConnection = new ClientConnection();
        clientConnection.setSocket(socket);
        System.out.println(socket.toString());
        clientConnection.setMessageListener(this::onMessage);
        clientConnection.setFileTransferStartListener(this::onFileTransferStart);
        clientConnection.setFileTransferProgressListener(this::onFileTransferProgress);
        clientConnection.setFileTransferEndListener(this::onFileTransferEnd);

        readerThread = new Thread(this::dataListener);
        readerThread.setDaemon(true);
        readerThread.start();

        keyboardInputReader = new KeyboardInputReader();
        keyboardInputReader.setInputListener(this::onKeyboardInput);
        keyboardInputReader.start();
    }

    private void onKeyboardInput(String input) {
        if (input.startsWith("send ")) {
            String fileName = input.substring("send ".length());
            sendFile(fileName);
        } else {
            clientConnection.send(input);
        }

    }

    private void sendFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            byte[] fileData = new byte[(int) file.length()];
            try {
                DataInputStream dis = new DataInputStream(new FileInputStream(file));
                dis.readFully(fileData);
                dis.close();

                String name = file.getName();
                clientConnection.startSendFile(file.length(), name);
                for (int i = 0; i < fileData.length; i += fileChunkSize) {
                    if (i + fileChunkSize > fileData.length) {
                        clientConnection.sendFileChunk(fileData, i, fileData.length - i);
                    } else {
                        clientConnection.sendFileChunk(fileData, i, fileChunkSize);
                    }
                }
                clientConnection.endSendFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.printf("No such file: %s", fileName);
        }
    }

    private void dataListener() {
        while (true) {
            clientConnection.read();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void onMessage(Message message) {
        System.out.println(message.getMessage());
    }

    private void onFileTransferStart(FileTransfer fileTransfer) {
        System.out.printf("File transfer started: %s\n", fileTransfer.getFileName());
    }

    private void onFileTransferProgress(FileTransfer fileTransfer) {
        System.out.printf("File transfer is in progress: %f\n", (float) fileTransfer.getCurrentOffset() / fileTransfer.getFileBytes().length);
    }

    private void onFileTransferEnd(FileTransfer fileTransfer) {
        System.out.printf("File transfer ended\n");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileTransfer.getFileName());
            fos.write(fileTransfer.getFileBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

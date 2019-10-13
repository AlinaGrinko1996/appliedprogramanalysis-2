package sockets.api.general;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class ClientConnection {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private DataOutputStream bufferedOutput;
    private Consumer<FileTransfer> fileTransferStartListener;
    private Consumer<FileTransfer> fileTransferProgressListener;
    private Consumer<FileTransfer> fileTransferEndListener;
    private Consumer<Message> messageListener;
    private Consumer<ClientConnection> disconnectionListener;

    private boolean disconnected;

    private byte[] readBuffer;
    private int messageLength;
    private int messageOffset;
    private boolean isMessageEnded;

    FileTransfer transferringFile;

    public ClientConnection() {
        readBuffer = new byte[3000];
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        input = socket.getInputStream();
        output = socket.getOutputStream();
        bufferedOutput = new DataOutputStream(new BufferedOutputStream(output));
    }

    public void read() {
        int readCount = 0;
        int available = 0;
        try {
            available = input.available();
            if (available != 0) {
                if (available > readBuffer.length - messageOffset - 1) {
                    readCount = readBuffer.length - messageOffset - 1;
                } else {
                    readCount = available;
                }
                readCount = input.read(readBuffer, messageOffset, readCount);
                messageOffset += readCount;
            }

        } catch (IOException e) {
            disconnected = true;
            disconnectionListener.accept(this);
        }

        if (readCount != 0 || (messageOffset > 0)) {
            if (messageOffset == readCount) {
                messageLength = ByteBuffer.wrap(readBuffer, 0, 4).getInt();
            }

            if (messageOffset >= messageLength) {
                isMessageEnded = true;
                parseMessage(readBuffer, 4, messageLength - 4);
            } else {
                isMessageEnded = false;
            }

            if (messageOffset > messageLength) {
                System.arraycopy(readBuffer, messageLength, readBuffer, 0, messageOffset - messageLength);
            }

            if (isMessageEnded) {
                messageOffset = messageOffset - messageLength;
            }

            if (isMessageEnded && messageOffset != 0) {
                messageLength = ByteBuffer.wrap(readBuffer, 0, 4).getInt();
            }
        }
    }

    private void parseMessage(byte[] buffer, int startOffset, int length) {
        MessageType messageType = MessageType.fromValue(readBuffer[startOffset]);
        switch (messageType) {
            case TEXT_MESSAGE:
                try {
                    String message = new String(readBuffer, startOffset + 1, length - 1, "UTF-8");
                    messageListener.accept(new Message(this, message));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;

            case FILE_TRANSFER_START:
                try {
                    int fileLength = ByteBuffer.wrap(readBuffer, startOffset + 1, 4).getInt();
                    String fileName = new String(readBuffer, startOffset + 5, length - 5, "UTF-8");
                    transferringFile = new FileTransfer(this, fileLength, fileName);
                    fileTransferStartListener.accept(transferringFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case FILE_TRANSFER:
                transferringFile.addBytes(readBuffer, startOffset + 1, length - 1);
                fileTransferProgressListener.accept(transferringFile);
                break;
            case FILE_TRANSFER_END:
                fileTransferEndListener.accept(transferringFile);
                break;
            case INVALID:
                break;
        }
    }

    public void send(String message) {
        try {
            byte[] messageBytes = message.getBytes();
            bufferedOutput.writeInt(4 + 1 + messageBytes.length);
            bufferedOutput.write((byte) MessageType.TEXT_MESSAGE.getValue());
            bufferedOutput.write(messageBytes);
            bufferedOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSendFile(long fileLength, String fileName) {
        try {
            byte[] fileNameBytes =  fileName.getBytes();
            bufferedOutput.writeInt(4 + 1 + 4 + fileNameBytes.length);
            bufferedOutput.write((byte) MessageType.FILE_TRANSFER_START.getValue());
            bufferedOutput.writeInt((int) fileLength);
            bufferedOutput.write(fileNameBytes);
            bufferedOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endSendFile() {
        try {
            bufferedOutput.writeInt(4 + 1);
            bufferedOutput.write((byte) MessageType.FILE_TRANSFER_END.getValue());
            bufferedOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFileChunk(byte[] file, int from, int length) {
        try {
            bufferedOutput.writeInt(4 + 1 + length);
            bufferedOutput.write((byte) MessageType.FILE_TRANSFER.getValue());
            bufferedOutput.write(file, from, length);
            bufferedOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMessageListener(Consumer<Message> messageListener) {
        this.messageListener = messageListener;
    }

    public void setDisconnectionListener(Consumer<ClientConnection> disconnectionListener) {
        this.disconnectionListener = disconnectionListener;
    }

    public void setFileTransferStartListener(Consumer<FileTransfer> fileTransferStartListener) {
        this.fileTransferStartListener = fileTransferStartListener;
    }

    public void setFileTransferProgressListener(Consumer<FileTransfer> fileTransferProgressListener) {
        this.fileTransferProgressListener = fileTransferProgressListener;
    }

    public void setFileTransferEndListener(Consumer<FileTransfer> fileTransferEndListener) {
        this.fileTransferEndListener = fileTransferEndListener;
    }

    public boolean isDisconnected() {
        return disconnected;
    }
}


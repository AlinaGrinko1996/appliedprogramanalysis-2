package sockets.api.general;

public class FileTransfer {
    private ClientConnection clientConnection;
    private byte[] fileBytes;
    private String fileName;
    private int currentOffset;
    private int lastChunkSize;

    public FileTransfer(ClientConnection clientConnection, int fileLength, String fileName) {
        this.clientConnection = clientConnection;
        this.fileName = fileName;
        fileBytes = new byte[fileLength];
        currentOffset = 0;
    }

    public void addBytes(byte[] srcBuffer, int srcBufferOffset, int length) {
        System.arraycopy(srcBuffer, srcBufferOffset, fileBytes, currentOffset, length);
        currentOffset += length;
        lastChunkSize = length;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public int getCurrentOffset() {
        return currentOffset;
    }

    public int getLastChunkSize() {
        return lastChunkSize;
    }
}


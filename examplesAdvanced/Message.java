package sockets.api.general;

public class Message {

    private ClientConnection fromConnection;
    private String message;

    public Message(ClientConnection fromConnection, String message) {
        this.fromConnection = fromConnection;
        this.message = message;
    }

    public ClientConnection getFromConnection() {
        return fromConnection;
    }

    public String getMessage() {
        return message;
    }
}


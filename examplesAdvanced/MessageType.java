package sockets.api.general;

public enum MessageType {
    TEXT_MESSAGE(0),
    FILE_TRANSFER_START(1),
    FILE_TRANSFER(2),
    FILE_TRANSFER_END(3),
    INVALID(-1);

    private int value;

    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageType fromValue(int value) {
        switch (value) {
            case 0:
                return TEXT_MESSAGE;
            case 1:
                return FILE_TRANSFER_START;
            case 2:
                return FILE_TRANSFER;
            case 3:
                return FILE_TRANSFER_END;
            default:
                return INVALID;
        }
    }
}

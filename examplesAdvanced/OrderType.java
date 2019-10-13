import java.util.HashMap;
import java.util.Map;

public enum OrderType {
    TIME("first", "1"),
    TIME_DESC("last", "2"),
    NAME("name.asc", "3"),
    NAME_DESC("name.desc", "4");

    private String order;
    private String type;
    private static Map<String, OrderType> orderTypeMap = new HashMap<>();

    static {
        for (OrderType value : OrderType.values()) {
            orderTypeMap.put(value.getType(), value);
        }
    }

    OrderType(String order, String type) {
        this.order = order;
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public static OrderType getOrderType(String code) {
        if (orderTypeMap.containsKey(code)) {
            return orderTypeMap.get(code);
        } else {
            throw new IllegalArgumentException("Incorrect order type.");
        }
    }
}

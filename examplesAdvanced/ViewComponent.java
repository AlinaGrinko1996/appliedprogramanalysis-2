
import java.util.HashMap;
import java.util.Map;

public enum ViewComponent {
    VIEW_ALL("1"),
    VIEW_PAGING("2");

    private String type;
    private static Map<String, ViewComponent> viewComponentMap = new HashMap<>();

    static {
        for (ViewComponent value : ViewComponent.values()) {
            viewComponentMap.put(value.getType(), value);
        }
    }

    ViewComponent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ViewComponent getViewComponent(String code) {
        if (viewComponentMap.containsKey(code)) {
            return viewComponentMap.get(code);
        } else {
            throw new IllegalArgumentException("Incorrect view type.");
        }
    }
}

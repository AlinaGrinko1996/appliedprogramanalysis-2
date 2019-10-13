import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import java.util.Arrays;

public class OrderComponent {

    private static final String SELECT_ORDER_TYPE = "selectOrderType";
    public static final String SWITCH_ORDER_TYPE = "switchOrderType";

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String zoneId;
    @Parameter
    private boolean useNameSort;
    @Parameter
    private OrderType defaultType;

    @Property
    private SelectModel orderModel;
    @Persist
    private OrderType orderType;

    @Inject
    private ComponentResources resources;
    @Inject
    private SelectModelFactory selectModelFactory;

    void setupRender() {
        if (orderType == null) {
            resetOrder();
        }

        final OrderType[] orderTypes = useNameSort
                ? new OrderType[] {OrderType.TIME_DESC, OrderType.TIME, OrderType.NAME, OrderType.NAME_DESC}
                : new OrderType[] {OrderType.TIME_DESC, OrderType.TIME};

        orderModel = selectModelFactory.create(Arrays.asList(orderTypes));
    }

    @OnEvent(component = SELECT_ORDER_TYPE, value = EventConstants.VALUE_CHANGED)
    public void onChangeFromSelectOrderType(OrderType optionSelected) {
        if (this.orderType != optionSelected) {
            this.orderType = optionSelected;
            resources.triggerEvent(SWITCH_ORDER_TYPE, new Object[]{optionSelected}, null);
        }
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public void resetOrder() {
        orderType = getDefaultType();
    }

    public OrderType getDefaultType() {
        return (defaultType == null) ? OrderType.TIME_DESC : defaultType;
    }

    public OrderTypeEncoder getOrderTypeEncoder() {
        return new OrderTypeEncoder();
    }
}
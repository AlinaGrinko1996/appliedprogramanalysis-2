import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.EnumSelectModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class TrackerComponent {
    private final static int MAX_GOAL_AMOUNT = 999999;
    private final static DecimalFormat DECIMAL_FORMAT;

    @Parameter(required = true)
    private Fund fund;
    @Parameter(required = true)
    private boolean verified;
    @Property
    private Level currentLevel;
    @Property
    private double total;
    @Property
    private Date startDate;
    @Property
    private Integer amount;
    @Property
    private PeriodType duration;
    @Property
    private boolean repeat;
    @Persist
    @Property
    private Goal goal;
    @Inject
    private JavaScriptSupport javaScriptSupport;
    @Inject
    private Messages messages;
    @Inject
    private Block levelTrackerBlock;
    @Inject
    private Block levelTrackerUnverified;
    @Inject
    private Block goalTrackerGeneral;
    @Inject
    private Block setYourGoalBlock;
    @Inject
    private Block goalCongratulationsBlock;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @InjectComponent
    private Zone trackerZone;


    private enum GoalState {
        NOT_SET,
        EXPIRED,
        REACHED
    }

    private GoalState goalState;

    static {
        DECIMAL_FORMAT = new DecimalFormat("#.#");
        DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP);
    }

    void setupRender() {
        initTrakerComponent();
        initGoalFields();
        goalState = determineGoalState();
    }

    @OnEvent("switchTracker")
    private void setTracker(boolean isLevelTrackerActive) {
        goalTrackerActive = !isLevelTrackerActive;

        initTrakerComponent();
        renderTrackerZone();
    }

    public final SelectModel getDurationModel() {
        return new EnumSelectModel(PeriodType.class, messages);
    }

    public String getCurrent() {
        return getRate(currentLevel);
    }

    public double getProgress() {
        if (currentLevel.isMaximumLevel()) {
            return 1;
        } else {
            final int max = currentLevel.getMaximum();
            final int min = currentLevel.getMinimum();
            return (total - min) / (max - min);
        }
    }


    public JSONObject getDatepickerParams() {
        return new JSONObject().put("minDate", 1);
    }

    public String getGoalInactive() {
        return (goalState == GoalState.NOT_SET) || (goalState == GoalState.EXPIRED) ? "unverify" : "";
    }
}
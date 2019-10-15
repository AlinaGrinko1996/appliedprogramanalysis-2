package container;

import java.util.HashMap;

public class MetricsTables {
    //className, methods
    HashMap<String, Integer> numberOfMethods = new HashMap<>();
    //className.methodName, methodsCalled
    HashMap<String, Integer> numberOfMethodsCalled = new HashMap<>();
    //className.methodName, loopsNumber
    HashMap<String, Integer> numberOfNestedLoops = new HashMap<>();

    public void incrementNumberOfMethods(String className) {
        if (!numberOfMethods.containsKey(className)) {
            numberOfMethods.put(className, 1);
        } else {
            int methods = numberOfMethods.get(className);
            numberOfMethods.put(className, methods + 1);
        }
    }

    public void incrementNumberOfMethodsCalled(String method) {
        if (!numberOfMethodsCalled.containsKey(method)) {
            numberOfMethodsCalled.put(method, 1);
        } else {
            int methods = numberOfMethodsCalled.get(method);
            numberOfMethodsCalled.put(method, methods + 1);
        }
    }

    public void incrementNumberOfNestedLoops(String method) {
        if (!numberOfNestedLoops.containsKey(method)) {
            numberOfNestedLoops.put(method, 1);
        } else {
            int methods = numberOfNestedLoops.get(method);
            numberOfNestedLoops.put(method, methods + 1);
        }
    }
}

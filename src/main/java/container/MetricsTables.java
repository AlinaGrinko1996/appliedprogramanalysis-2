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
        int methods = numberOfMethods.get(className);
        if (methods <= 0) {
            numberOfMethods.put(className, 1);
        } else {
            numberOfMethods.put(className, methods + 1);
        }
    }

    public void incrementNumberOfMethodsCalled(String method) {
        int methods = numberOfMethodsCalled.get(method);
        if (methods <= 0) {
            numberOfMethodsCalled.put(method, 1);
        } else {
            numberOfMethodsCalled.put(method, methods + 1);
        }
    }

    public void incrementNumberOfNestedLoops(String method) {
        int methods = numberOfNestedLoops.get(method);
        if (methods <= 0) {
            numberOfNestedLoops.put(method, 1);
        } else {
            numberOfNestedLoops.put(method, methods + 1);
        }
    }
}

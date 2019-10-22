package container;

import com.google.common.base.Strings;

import java.util.*;

public class MetricsTables {
    //className, methods
    private HashMap<String, Integer> numberOfMethods = new HashMap<>();
    //className, methodsCalled
    private HashMap<String, Set<String>> methodsCalled = new HashMap<>();
    //className.methodName, loopsNumber
    private HashMap<String, Integer> nestedLoopsInMethod = new HashMap<>();

    public void incrementNumberOfMethods(String className) {
        if (!numberOfMethods.containsKey(className)) {
            numberOfMethods.put(className, 1);
        } else {
            int methods = numberOfMethods.get(className);
            numberOfMethods.put(className, methods + 1);
        }
    }

    public void addMethodCalled(String currentClass, String methodCalled) {
        if (!methodsCalled.containsKey(currentClass)) {
            Set<String> methods = new HashSet<>();
            methods.add(methodCalled);
            methodsCalled.put(currentClass, methods);
        } else {
            Set<String> methods = methodsCalled.get(currentClass);
            methods.add(methodCalled);
        }
    }

    public void refreshNumberOfLoops(Scope scope) {
        String method = scope.getCurrentMethod();
        int loopsInStack = scope.getAmountOfLoopsInStack();

        if (!nestedLoopsInMethod.containsKey(method)) {
            nestedLoopsInMethod.put(method, loopsInStack - 1);
        } else {
            int loops = nestedLoopsInMethod.get(method);
            nestedLoopsInMethod.put(method, Math.max(loops, loopsInStack - 1));
        }
    }

    public List<MetricsOfClass> getMetricsOfClasses() {
        List<MetricsOfClass> metrics = new ArrayList<>();
        HashMap<String, Integer> classesWMC = getWMCOnLoopsNestedDepth();
        numberOfMethods.forEach((className, number) -> {
            if (Strings.isNullOrEmpty(className)) {
                return;
            }
            MetricsOfClass metricsOfClass = new MetricsOfClass(className);
            metricsOfClass.setNOM(number);

            Set<String> methodsCalledFromClass = methodsCalled.get(className);
            int publicMethodsCalled = Objects.isNull(methodsCalledFromClass) ? 0 : methodsCalledFromClass.size();
            metricsOfClass.setRFC(number + publicMethodsCalled);

            int WMC = classesWMC.getOrDefault(className, 0);
            metricsOfClass.setWMC(WMC);
            metrics.add(metricsOfClass);
        });
        return metrics;
    }

    private HashMap<String, Integer> getWMCOnLoopsNestedDepth() {
        HashMap<String, Integer> loopNestingDepth = new HashMap<>();
        nestedLoopsInMethod.forEach((methodName, numberOfLoops) -> {
            if (numberOfLoops > 0) {
                String className = methodName.split("[.]")[0];
                if (loopNestingDepth.containsKey(className)) {
                    int oldValue = loopNestingDepth.get(className);
                    loopNestingDepth.put(className, oldValue + numberOfLoops);
                } else {
                    loopNestingDepth.put(className, numberOfLoops);
                }
            }
        });
        return loopNestingDepth;
    }
}

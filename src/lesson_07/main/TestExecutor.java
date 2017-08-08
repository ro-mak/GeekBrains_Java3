
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestExecutor {

    private static Class aClass;
    private static Method[] methods;
    private static Method beforeSuite;
    private static Method afterSuite;

    public static void start(Class aClass) {
        TestExecutor.aClass = aClass;
        methods = aClass.getMethods();
        checkAnnotations();
        execute();
    }

    public static void start(String className) {
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        methods = aClass.getMethods();
        checkAnnotations();
        execute();
    }

    private static void checkAnnotations() {
        int countBeforeSuite = 0;
        int countAfterSuite = 0;
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                countBeforeSuite++;
                beforeSuite = method;
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                countAfterSuite++;
                afterSuite = method;
            }

        }
        if (countBeforeSuite > 1 || countBeforeSuite < 0) {
            throw new RuntimeException("This class has " + countBeforeSuite + " 'BeforeSuite' annotations. Should be 0 or 1.");
        }

        if (countAfterSuite > 1 || countAfterSuite < 0) {
            throw new RuntimeException("This class has " + countAfterSuite + " 'AfterSuite' annotations. Should be 0 or 1.");
        }
    }

    private static void execute() {
        if(beforeSuite!=null)executeBeforeSuite();
        HashMap<Method, Integer> testMethodsSet = new HashMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethodsSet.put(method, method.getAnnotation(Test.class).priority());
            }
        }
        executeTest(testMethodsSet);
        if(afterSuite!=null)executeAfterSuite();
    }

    private static void executeBeforeSuite() {
        System.out.println("executeBeforeSuite");
        invokeMethod(beforeSuite);
    }

    private static void executeTest(HashMap<Method, Integer> methods) {
        System.out.println("executeTests");
        int currentPriority = 10;
        while (currentPriority >= 0) {
            Method method = searchForPriorityMethod(methods, currentPriority);
            if (method == null) {
                currentPriority--;
                continue;
            }
            invokeMethod(method);
        }
    }

    private static Method searchForPriorityMethod(HashMap<Method, Integer> methods, int currentPriority) {
        Method method = null;
        int methodPriority;
        for (Map.Entry<Method, Integer> entry : methods.entrySet()) {
            methodPriority = entry.getValue();
            if (methodPriority == currentPriority) {
                method = entry.getKey();
            }
        }
        if (method != null) methods.remove(method);
        return method;
    }

    private static void executeAfterSuite() {
        System.out.println("executeAfterSuite");
        invokeMethod(afterSuite);
    }

    private static void invokeMethod(Method method){
        try {
            method.invoke(aClass.newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}

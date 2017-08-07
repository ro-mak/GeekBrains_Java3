
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestExecutor {

    private static Class aClass;
    private static Method[] methods;

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
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                countBeforeSuite++;
            }

            if(method.isAnnotationPresent(Test.class)) {
                int priority = method.getAnnotation(Test.class).priority();

                if (priority > 10 || priority < 1) {
                    throw new RuntimeException("Unsupported priority in method " + method.getName() + " of the class " + aClass.getName() + ":" + priority);
                }
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                countAfterSuite++;
            }

        }
        if (countBeforeSuite > 1 || countBeforeSuite <= 0) {
            throw new RuntimeException("This class has " + countBeforeSuite + " 'BeforeSuite' annotations. Should be 1.");
        }

        if (countAfterSuite > 1 || countAfterSuite <= 0) {
            throw new RuntimeException("This class has " + countAfterSuite + " 'AfterSuite' annotations. Should be 1.");
        }
    }

    private static void execute() {
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                executeBeforeSuite(method);
            }
        }

        HashMap<Method, Integer> methodsSet = new HashMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                methodsSet.put(method, method.getAnnotation(Test.class).priority());
            }
        }
        executeTest(methodsSet);

        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class)) {
                executeAfterSuite(method);
            }
        }
    }

    private static void executeBeforeSuite(Method method) {
        System.out.println("executeBeforeSuite");
        invokeMethod(method);
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

    private static void executeAfterSuite(Method method) {
        System.out.println("executeAfterSuite");
        invokeMethod(method);
    }

    private static void invokeMethod(Method method){
        try {
            method.invoke(aClass.newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}

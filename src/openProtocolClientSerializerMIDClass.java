import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

public class openProtocolClientSerializerMIDClass {
    //
    public String integratorString (String commands) {
        //
//        System.out.println("integrationString input: " + commands);
        //
        String mid = null;
        List<Object> dataField = new ArrayList<>();
        Object integrationString = null;
        //
        if (!(commands.isEmpty() && commands.isBlank())) {
            String[] inputs = commands.split("\\s+");
//            System.out.println("length of inputs? " + inputs.length);
            for (int df = 0; df < inputs.length; df++) {
                if (df == 0) {
                    mid = inputs[df];
                    System.out.println("\n" + mid + "\n");
                } else {
                    dataField.add(inputs[df]);
                }
            }
        } else {
            System.out.println("false input");
            System.exit(0);
        }
        //
        assert mid != null;
        //
        String midValue = mid.substring(3);
//        System.out.println("the mid without folder is " + mid);
        String midPackage = "midCommands" + "." + mid;
//        System.out.println("the mid with folder is " + mid);
        //
        Class<?>[] inputTypes = {String.class, List.class};
        try {
//            Class<?> clazz = Class.forName(className);
//            Object obj = clazz.getDeclaredConstructor().newInstance();
//            Class<?> clazz2 = obj.getClass();
//            System.out.println("what is the class: " + clazz);
//            //
//            Method method = clazz2.getDeclaredMethod("integratorString", inputTypes);
//            System.out.println("what is the method-A: " + method);
//            //
//            integrationString = method.invoke(obj, midValue, dataField);
//            System.out.println("what was integrationString-A\n" + integrationString);
            ////
            ////
            Object midClassObj = Class.forName(midPackage).getDeclaredConstructor().newInstance();
            Class<?> midClass = midClassObj.getClass();
//            System.out.println("what is the midClass: " + midClass);
//            Class<?> midClass2 = ClassLoader.getSystemClassLoader().loadClass(mid);
//            System.out.println("what is the midClass2: " + midClass2);
            //
            Method method = midClass.getDeclaredMethod("integratorString", inputTypes);
//            System.out.println("what is the method-B: " + method);
//            java.lang.reflect.Method method2 = midClass2.getDeclaredMethod("integratorString", inputTypes);
//            System.out.println("what is the method2: " + method2);
            //
            integrationString = method.invoke(midClassObj, midValue, dataField);
//            integrationString = method2.invoke(midClassObj, midValue, dataField);
//            System.out.println("what was integrationString-B\n" + integrationString);
        } catch(Throwable e) {
            System.out.println("Class or Method missing\n" + e.getMessage());
            e.printStackTrace();
        }
//         integrationString = midSerializer.midCommands.MID0001(midValue, dataField);
//        integrationString = midSerializer.midCommands.MID0012(midValue, dataField);
        //
        System.out.println("what was integrationString\n" + integrationString);
        return (String) integrationString;
    }
}
//import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
//import java.util.HashMap;

public class openProtocolClientParserMIDClass {
    //
    public String getMID(String msg){
        return msg.substring(4, 8);
    }
    //
    public String getLength(String msg){
        return msg.substring(0, 4);
    }
    //
    public String getRevision(String msg){
        return msg.substring(8, 11);
    }
    public Object parseMessage(String controllerMessage) {
//        System.out.println("parseMessage\n " + controllerMessage);
        //
        String mid = null;
        String midCommand = null;
        String midLengthString = null;
        String midRevisionString = null;
        Object controllerMessageMap = null;
        Object controllerMessageMapTest1 = null;
        Object controllerMessageMapTest2 = null;
        //
        mid = getMID(controllerMessage);
//        System.out.println("MID from controller\n " + mid);
        //
        midCommand = "MID" + mid;
//        System.out.println("MID+ from controller\n " + midCommand);
        //
        midLengthString = getLength(controllerMessage);
//        System.out.println("command length \n " + midLengthString);
        //
        midRevisionString = getRevision(controllerMessage);
//        System.out.println("command revision \n " + midRevisionString);
        //
        Class<?>[] inputTypes = {String.class, String.class, String.class, String.class};
        try {
            ////
            Object midClassObj = Class.forName(midCommand).getDeclaredConstructor().newInstance();
            Class<?> midClass = midClassObj.getClass();
            System.out.println("what is the midClass: " + midClass);
            //
            Method method = midClass.getDeclaredMethod("controllerString", inputTypes);
//            System.out.println("what is the method: " + method);
            //
            controllerMessageMap = method.invoke(midClassObj, controllerMessage, midCommand, midLengthString, midRevisionString);
            //System.out.println("what was controllerMessageMap\n" + controllerMessageMap);
        } catch(Throwable e) {
            System.out.println("Something went wrong\n" + e.getMessage());
            e.printStackTrace();
        }
        //
        System.out.println("what was integrationString\n" + controllerMessageMap);
        return controllerMessageMap;
    }
}
//--MID0002
//--MID0002
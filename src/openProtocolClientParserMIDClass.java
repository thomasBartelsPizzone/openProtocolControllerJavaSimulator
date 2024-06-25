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
        String mid;
        String midCommand;
        String midLengthString;
        String midRevisionString;
        Object controllerMessageMap = null;
        //
        mid = getMID(controllerMessage);
//        System.out.println("MID from controller\n " + mid);
        //
        midCommand = "MID" + mid;
//        System.out.println("MID+ from controller\n " + midCommand);
        //
        String midPackage = "midCommands" + "." + midCommand;
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
//            Object midClassObj = Class.forName(midCommand).getDeclaredConstructor().newInstance();
            Object midClassObj = Class.forName(midPackage).getDeclaredConstructor().newInstance();
            Class<?> midClass = midClassObj.getClass();
//            System.out.println("what is the midClass: " + midClass);
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
    public String getReply(String controllerMessage) {
//        System.out.println("parseMessage\n " + controllerMessage);
        //
        String mid;
        String midCommand;
        String midLengthString;
        String midRevisionString;
        String replyMID = null;
        //
        mid = getMID(controllerMessage);
        //
        midCommand = "MID" + mid;
        //
        String midPackage = "midCommands" + "." + midCommand;
        //
        Class<?>[] integratorMidInputTypes = {String.class};
        // Get the reply command if available
//        String answer = "acknowledge";
        try {
            //
            Object integratorMidClassObj = Class.forName(midPackage).getDeclaredConstructor().newInstance();
            Class<?> integratorMidClass = integratorMidClassObj.getClass();
            Method integratorMidMethod = integratorMidClass.getDeclaredMethod("replyMID", integratorMidInputTypes);
            //
            replyMID = (String) integratorMidMethod.invoke(integratorMidMethod, "acknowledge");
        } catch(Throwable e) {
            System.out.println("Something went wrong\n" + e.getMessage());
            e.printStackTrace();
        }
        //
        System.out.println("what was integrationString\n" + replyMID);
        return replyMID;
    }
}
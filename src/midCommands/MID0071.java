package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0071 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0072";
        } else {
            return null;
        }
    }
    //
    // MID 0071 Alarm
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        // String midLengthString = "0053";
        // String midRevision = "000";
        String midLengthString = null;
        String midRevision = null;
        //  midAckFlag = "0";
        String midAckFlag = "1";
        String midStationID = "00";
        String midSpindleID = "00";
        String midSpare = "0000";
        //
        StringBuilder tempMidAscii = new StringBuilder();
        StringBuilder midAscii = new StringBuilder();
        // No Ack flag
        tempMidAscii.append(midAckFlag);
        // StationID flag
        tempMidAscii.append(midStationID);
        // SpindleID flag
        tempMidAscii.append(midSpindleID);
        // Spare
        tempMidAscii.append(midSpare);
        //
        // Data Field
        if (!dataFieldValue.isEmpty()) {
            int dFCount = 1;
            for (Object s : dataFieldValue) {
                tempMidAscii.append(String.format("%02d", dFCount));
                tempMidAscii.append(s);
                dFCount += 1;
            }
        }
        // Check Size
        int midLength = 4 + 4 + 3 + tempMidAscii.length(); // length of message + command + revision + remainder of msg
        // combine string
        // length
        midAscii.append(String.format("%04d", midLength));
        //midAscii.append(String.format("%04d", midLengthString));
        // command
        midAscii.append(midCommandValue);
        // revision
        if (midLength < 54) {
            midRevision = "001";
        } else if (midLength < 55) {
            midRevision = "002";            
        } else {
            midRevision = "003";
        }
        midAscii.append(midRevision);
        // remainder
        midAscii.append(tempMidAscii);
        //
        return (midAscii + "\0");
    }
    //
    public HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            //
            if (midRevision.equals("001")) {
                int[] ErrorCodeIdxByteRange = {22, 26};
                //
                int[] controllerReadyStatusIdxByteRange = {28, 29};
                //
                int[] toolReadyStatusIdxByteRange = {31, 32};
                //
                int[] timeIdxByteRange = {34, 53};
                //
                controllerHash.put("ERROR_CODE", controllerMsg.substring(ErrorCodeIdxByteRange[0], ErrorCodeIdxByteRange[1]));
                controllerHash.put("CONTROLLER_READY_STATUS", controllerMsg.substring(controllerReadyStatusIdxByteRange[0], controllerReadyStatusIdxByteRange[1]));
                controllerHash.put("TOOL_READY_STATUS", controllerMsg.substring(toolReadyStatusIdxByteRange[0], toolReadyStatusIdxByteRange[1]));
                controllerHash.put("TIME", controllerMsg.substring(timeIdxByteRange[0], timeIdxByteRange[1]));
            } else {
                int[] ErrorCodeIdxByteRange = {22, 27};
                //
                int[] controllerReadyStatusIdxByteRange = {29, 30};
                //
                int[] toolReadyStatusIdxByteRange = {32, 33};
                //
                int[] timeIdxByteRange = {35, 54};
                //
                controllerHash.put("ERROR_CODE", controllerMsg.substring(ErrorCodeIdxByteRange[0], ErrorCodeIdxByteRange[1]));
                controllerHash.put("CONTROLLER_READY_STATUS", controllerMsg.substring(controllerReadyStatusIdxByteRange[0], controllerReadyStatusIdxByteRange[1]));
                controllerHash.put("TOOL_READY_STATUS", controllerMsg.substring(toolReadyStatusIdxByteRange[0], toolReadyStatusIdxByteRange[1]));
                controllerHash.put("TIME", controllerMsg.substring(timeIdxByteRange[0], timeIdxByteRange[1]));
                //
                if (midRevision.equals("003")) {
                    //
                    int[] toolHealthIdxByteRange = {56, 57};
                    String toolHealthValue = controllerMsg.substring(toolHealthIdxByteRange[0], toolHealthIdxByteRange[1]);
                    if (toolHealthValue.equals("0")) {
                        controllerHash.put("TOOL_HEALTH", "Tool Health not applicable");
                    } else if (toolHealthValue.equals("1")) {
                        controllerHash.put("TOOL_HEALTH", "Tool Health is OK");
                    } else {
                        controllerHash.put("TOOL_HEALTH", "Tool Health is NOK");
                    }
                    //
                    int[] alarmTextIdxByteRange = {59, 110};
                    controllerHash.put("ALARM_TEXT",  controllerMsg.substring(alarmTextIdxByteRange[0], alarmTextIdxByteRange[1]));
                }
            }
        } catch (StringIndexOutOfBoundsException s) {
            System.out.println("StringIndex Out of Bounds\n" + s.getMessage());
            s.printStackTrace();
            midControllerHash.put(midCommand, controllerHash);
            return midControllerHash;
        }
        //
        midControllerHash.put(midCommand, controllerHash);
        return midControllerHash;
    }
}
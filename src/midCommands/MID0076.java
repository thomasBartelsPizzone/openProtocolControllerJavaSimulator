package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0076 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0077";
        } else {
            return null;
        }
    }
    //
    // MID 0076 Alarm status
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        // String midLengthString = "0056";
        // String midRevision = "000";
        String midLengthString = null;
        String midRevision = null;
        String midAckFlag = "0";
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
            opUtilityMethods opM = new opUtilityMethods();
            // Check Size
            int inputCount = dataFieldValue.size();
            // revision
            if (inputCount == 5) {
                midRevision = "001";
                midLengthString = "0056";
            } else if (inputCount == 6) {
                midRevision = "003";
                midLengthString = "0060";
            } else {
                midRevision = "002";
                midLengthString = "0056";
            }
            //
            if (midRevision.equals("001")) {
                int dFCount = 1;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 2) {
                            //midAscii.append(String.format("%04d", s));
                            midAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else {
                            tempMidAscii.append(s);
                        }
                    } else {
                        tempMidAscii.append(s);
                    }
                    dFCount += 1;
                }
            } else {
                int dFCount = 1;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 2) {
                            //midAscii.append(String.format("%05d", s));
                            midAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else {
                            tempMidAscii.append(s);
                        }
                    } else {
                        tempMidAscii.append(s);
                    }
                    dFCount += 1;
                }
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
        if (midLength == 56) {
            midRevision = "001";
        } else if (midLength == 57) {
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
            int[] alarmStatusIdxByteRange = {22, 23};
            int[] errorCodeIdxByteRange = {25, 29};
            int[] controllerReadyStatusIdxByteRange = {31, 32};
            int[] toolReadyStatusIdxByteRange = {34, 35};
            int[] timeIdxByteRange = {37, 56};
            //
            controllerHash.put("ALARM_STATUS", controllerMsg.substring(alarmStatusIdxByteRange[0], alarmStatusIdxByteRange[1]));
            controllerHash.put("ERROR_CODE", controllerMsg.substring(errorCodeIdxByteRange[0], errorCodeIdxByteRange[1]));
            controllerHash.put("CONTROLLER_READY_STATUS", controllerMsg.substring(controllerReadyStatusIdxByteRange[0], controllerReadyStatusIdxByteRange[1]));
            controllerHash.put("TOOL_READY_STATUS", controllerMsg.substring(toolReadyStatusIdxByteRange[0], toolReadyStatusIdxByteRange[1]));
            controllerHash.put("TIME", controllerMsg.substring(timeIdxByteRange[0], timeIdxByteRange[1]));
            //
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

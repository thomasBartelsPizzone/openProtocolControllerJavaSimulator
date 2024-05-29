package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0052 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0053";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0053";
        } else {
            return null;
        }
    }
    //
    // MID 0052 Vehicle ID Number
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        // String midAckFlag = "0";
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
            opUtilityMethods opM = new opUtilityMethods();
            int dFCount = 1;
            for (Object s : dataFieldValue) {
                tempMidAscii.append(String.format("%02d", dFCount));
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    if (dFCount < 4) {
                        //tempMidAscii.append(String.format("%25d", s));
                        tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                    } else {
                        tempMidAscii.append(s);
                    }
                } else {
                    tempMidAscii.append(s);
                }
                dFCount += 1;
            }
        }
        // Check Size
        int midLength = 4 + 4 + 3 + tempMidAscii.length(); // length of message + command + revision + remainder of msg
        // combine string
        // length
        midAscii.append(String.format("%04d", midLength));
        // command
        midAscii.append(midCommandValue);
        // revision
        if (midLength == 47) {
            midAscii.append("001");
        } else {
            midAscii.append("002");
        }
        // remainder
        midAscii.append(tempMidAscii);
        //
        // return (midAscii + "NUL");
        return (midAscii.toString());
    }
    //
    public HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            //
            int[] ackFlagIdxByteRange = {11, 12};
            //
            int[] vinNumberIdxByteRange = {22, 47};
            //
            int[] identifierResult2IdxByteRange = {49, 74};
            //
            int[] identifierResult3IdxByteRange = {76, 91};
            //
            int[] identifierResult4IdxByteRange = {93, 128};
            //
            controllerHash.put("ACK_FLAG", controllerMsg.substring(ackFlagIdxByteRange[0], ackFlagIdxByteRange[1]));
            //
            controllerHash.put("VIN_NUMBER", controllerMsg.substring(vinNumberIdxByteRange[0], vinNumberIdxByteRange[1]));
            //
            if (midRevision.equals("002")) {
                controllerHash.put("IDENTIFIER_RESULT_PART_2", controllerMsg.substring(identifierResult2IdxByteRange[0], identifierResult2IdxByteRange[1]));
                controllerHash.put("IDENTIFIER_RESULT_PART_3", controllerMsg.substring(identifierResult3IdxByteRange[0], identifierResult3IdxByteRange[1]));
                controllerHash.put("IDENTIFIER_RESULT_PART_4", controllerMsg.substring(identifierResult4IdxByteRange[0], identifierResult4IdxByteRange[1]));
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

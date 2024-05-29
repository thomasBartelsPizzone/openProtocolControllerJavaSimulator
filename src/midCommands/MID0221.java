package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0221 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0005";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0005";
        } else if (answer.equalsIgnoreCase("error")) {
            return "midCommands.MID0004";
        } else {
            return "midCommands.MID0004";
        }
    }
    //
    // MID 0221 Digital input function
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0023";
        String midRevision = "001";
        // String midAckFlag = "0";
        String midAckFlag = "1";
        String midStationID = "00";
        String midSpindleID = "00";
        String midSpare = "0000";
        //
        StringBuilder midAscii = new StringBuilder();
        int midLength = Integer.parseInt(midLengthString);
        // Length
        midAscii.append(midLengthString);
        // MID
        midAscii.append(midCommandValue);
        // Revision
        midAscii.append(midRevision);
        // No Ack flag
        midAscii.append(midAckFlag);
        // StationID flag
        midAscii.append(midStationID);
        // SpindleID flag
        midAscii.append(midSpindleID);
        // Spare
        midAscii.append(midSpare);
        //
        // Data Field
        if (!dataFieldValue.isEmpty()) {
            opUtilityMethods opM = new opUtilityMethods();
            int dFCount = 1;
            for (Object s : dataFieldValue) {
                midAscii.append(String.format("%02d", dFCount));
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    // midAscii.append(String.format("%03d", s));
                    midAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                } else {
                    midAscii.append(s);
                }
            }
        }
        // padding
        if (midAscii.length() >= midLength) {
            return (midAscii + "\0");
        }
        else {
            return (new opUtilityMethods(midAscii.toString(), midLength, "right").padZeroes() + "\0");
        }
    }
    //
    public HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            //
            int[] ackFlagIdxByteRange = {11, 12};
            controllerHash.put("ACK_FLAG", controllerMsg.substring(ackFlagIdxByteRange[0], ackFlagIdxByteRange[1]));
            //
            int[] digitalInputFunctionNumberIdxByteRange = {22, 25};
            controllerHash.put("DIGITAL_INPUT_FUNCTION_NUMBER", controllerMsg.substring(digitalInputFunctionNumberIdxByteRange[0], digitalInputFunctionNumberIdxByteRange[1]));

            int[] digitalInputFunctionStatusIdxByteRange = {27, 28};
            controllerHash.put("DIGITAL_INPUT_FUNCTION_STATUS", controllerMsg.substring(digitalInputFunctionStatusIdxByteRange[0], digitalInputFunctionStatusIdxByteRange[1]));
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

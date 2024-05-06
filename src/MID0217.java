import java.util.HashMap;
import java.util.List;


public class MID0217 {
    //
    String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "MID0218";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "MID0218";
        } else {
            return null;
        }
    }
    //
    // MID 0217 Relay function
    String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0028";
        String midRevision = "001";
         String midAckFlag = "0";
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
            int dfCount = 1;
            for (Object s : dataFieldValue) {
                midAscii.append(String.format("%02d", dfCount));
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
    HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            //
            int[] relayNumberIdxByteRange = {22, 25};
            controllerHash.put("RELAY_NUMBER", controllerMsg.substring(relayNumberIdxByteRange[0], relayNumberIdxByteRange[1]));

            int[] relayFunctionStatusIdxByteRange = {27, 28};
            String relayFunctionValue = controllerMsg.substring(relayFunctionStatusIdxByteRange[0], relayFunctionStatusIdxByteRange[1]);
            if (relayFunctionValue.equals("0")) {
                controllerHash.put("RELAY_FUNCTION_STATUS", "not active");
            } else {
                controllerHash.put("RELAY_FUNCTION_STATUS", "active");
            }
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

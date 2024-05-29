package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0214 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0215";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0215";
        } else if (answer.equalsIgnoreCase("error")) {
            return "midCommands.MID0004";
        } else {
            return "midCommands.MID0004";
        }
    }
    //
    // MID 0214 IO device status request
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0022";
        // String midRevision = "000";
        // String midRevision = "001";
        String midRevision = "002";
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
            for (Object s : dataFieldValue) {
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    //midAscii.append(String.format("%02d", s));
                    midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
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
            int[] deviceNumberByteRange = {20, 22};
            String deviceNumberValue = controllerMsg.substring(deviceNumberByteRange[0], deviceNumberByteRange[1]);
            if (deviceNumberValue.equals("00")) {
                controllerHash.put("DEVICE_NUMBER", "internal device");
            } else {
                controllerHash.put("DEVICE_NUMBER", "I/O expanders");
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

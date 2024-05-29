package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0004 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
    // MID 0004 Command error
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = "0026";
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
            int[] requestMIDIdxByteRange = {20, 24};
            int[] errorCodeIdxByteRange = {24, 26};
            //
            controllerHash.put("REQUEST_MID", "MID" + controllerMsg.substring(requestMIDIdxByteRange[0], requestMIDIdxByteRange[1]));
            controllerHash.put("ERROR_CODE", controllerMsg.substring(errorCodeIdxByteRange[0], errorCodeIdxByteRange[1]));
        } catch (StringIndexOutOfBoundsException s) {
            System.out.println("StringIndex Out of Bounds\n" + s.getMessage());
            s.printStackTrace();
            midControllerHash.put(midCommand, controllerHash);
            return midControllerHash;
        }
        midControllerHash.put(midCommand, controllerHash);
        return midControllerHash;
    }
}
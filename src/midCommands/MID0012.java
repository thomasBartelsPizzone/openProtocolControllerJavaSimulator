package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0012 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0013";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0013";
        } else if (answer.equalsIgnoreCase("error")) {
            return "midCommands.MID0004";
        } else {
            return "midCommands.MID0004";
        }
    }
    //
    // MID 0012 Parameter set data upload request
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = "0023";
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
            for (Object s : dataFieldValue) {
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
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
            int midRevisionInt = Integer.parseInt(midRevision);
            //
            // Datafield
            if (midRevisionInt != 3 && midRevisionInt != 4) {
                int[] parameterSetIdIdxByteRange = {20, 23};
                String parameterSetIdString = controllerMsg.substring(parameterSetIdIdxByteRange[0], parameterSetIdIdxByteRange[1]);
                //
                int parameterSetIdInteger = Integer.parseInt(parameterSetIdString);
                controllerHash.put("PARAMETER_SET_ID", parameterSetIdInteger);
            } else {
                int[] psetFileVersionIdxByteRange = {20, 23};
                controllerHash.put("PSET_FILE_VERSION", controllerMsg.substring(psetFileVersionIdxByteRange[0], psetFileVersionIdxByteRange[1]));
            }
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
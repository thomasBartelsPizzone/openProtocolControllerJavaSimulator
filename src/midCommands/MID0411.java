package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0411 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0411";
        } else {
            return null;
        }
    }
    //
    // MID 0411 AutoDisable settings reply
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0020";
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
//                if (s instanceof Integer) {
                if (opM.isNumeric(s)) {
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
            int[] autoDisableSettingByteRange = {20, 22};
            String autoDisableSettingValue = controllerMsg.substring(autoDisableSettingByteRange[0], autoDisableSettingByteRange[1]);
            if (autoDisableSettingValue.equals("00")) {
                controllerHash.put("AUTO_DISABLE_SETTING", "not_used");
            } else {
                controllerHash.put("AUTO_DISABLE_SETTING", "in_use");
            }
            //
            int[] currentBatchByteRange = {22, 24};
            String currentBatchValue = controllerMsg.substring(currentBatchByteRange[0], currentBatchByteRange[1]);
            if (currentBatchValue.equals("00")) {
                controllerHash.put("CURRENT_BATCH", "function_not_used");
            } else {
                controllerHash.put("CURRENT_BATCH", "function_in_use");
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


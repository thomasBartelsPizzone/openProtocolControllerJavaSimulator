package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0111 {
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
    // MID 0111 Display user text on graph
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        String midLengthString = "0137";
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
                    if (dfCount == 1) {
                        //midAscii.append(String.format("%04d", s));
                        midAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                    } else if (dfCount == 2) {
                        midAscii.append(s);
                    } else if (dfCount > 2) {
                        //midAscii.append(String.format("%25d", s));
                        midAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                    } else {
                        midAscii.append(s);
                    }
                } else {
                    midAscii.append(s);
                }
                dfCount += 1;
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
        //
        int[] textDurationIdxByteRange = {22, 26};
        controllerHash.put("TEXT_DURATION", controllerMsg.substring(textDurationIdxByteRange[0], textDurationIdxByteRange[1]));
        //
        int[] removalConditionIdxByteRange = {28, 29};
        String removalConditionValue = controllerMsg.substring(removalConditionIdxByteRange[0], removalConditionIdxByteRange[1]);
        if (removalConditionValue.equals("0")) {
            controllerHash.put("REMOVAL_CONDITION", "acknowledge or wait expiration time");
        } else  {
            controllerHash.put("REMOVAL_CONDITION", "acknowledge");
        }
        //
        int[] line1IdxByteRange = {31, 56};
        int[] line2IdxByteRange = {58, 83};
        int[] line3IdxByteRange = {85, 110};
        int[] line4IdxByteRange = {112, 137};
        //
        controllerHash.put("LINE1", controllerMsg.substring(line1IdxByteRange[0], line1IdxByteRange[1]));
        controllerHash.put("LINE2", controllerMsg.substring(line2IdxByteRange[0], line2IdxByteRange[1]));
        controllerHash.put("LINE3", controllerMsg.substring(line3IdxByteRange[0], line3IdxByteRange[1]));
        controllerHash.put("LINE4", controllerMsg.substring(line4IdxByteRange[0], line4IdxByteRange[1]));
        //
        midControllerHash.put(midCommand, controllerHash);
        return midControllerHash;
    }
}

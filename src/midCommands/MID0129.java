package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0129 {
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
    // MID 0129 Job batch decrement
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
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

        // MID
        midAscii.append(midCommandValue);

        // Revision
        if (midLength < 21) {
            midAscii.append("001");
        } else {
            midAscii.append("002");
        }
        // remainder
        midAscii.append(tempMidAscii);

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
            if (midRevision.equals("002")) {
                int[] channelIDIdxByteRange = {22, 24};
                //
                int[] parameterSetIDIdxByteRange = {26, 29};
                //
                controllerHash.put("CHANNEL_ID", controllerMsg.substring(channelIDIdxByteRange[0], channelIDIdxByteRange[1]));
                controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
            }
            //
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

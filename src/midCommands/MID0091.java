package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0091 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0092";
        } else {
            return null;
        }
    }
    //
    // MID 0091 Multi-spindle status data
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = null;
        String midRevision = "001";
        // String midAckFlag = "0";
        String midAckFlag = "1";
        String midStationID = "00";
        String midSpindleID = "00";
        String midSpare = "0000";
        //
        StringBuilder tempMidAscii = new StringBuilder();
        StringBuilder midAscii = new StringBuilder();
        // MID
        tempMidAscii.append(midCommandValue);
        // Revision
        tempMidAscii.append(midRevision);
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
                    if (dFCount == 1) {
                        //tempMidAscii.append(String.format("%02d", s));
                        tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else if (dFCount == 2) {
                        tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
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
        int midLength = 4 + tempMidAscii.length(); // length of message
        // combine string
        // length
        midAscii.append(String.format("%04d", midLength));
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
            int[] numberOfSpindlesIdxByteRange = {22, 24};
            // int[] spindleStatusIdxByteRange = {37};
            String numberOfSpindlesString = controllerMsg.substring(numberOfSpindlesIdxByteRange[0], numberOfSpindlesIdxByteRange[1]);
            int numberOfSpindlesInteger = Integer.parseInt(numberOfSpindlesString);
            //        controllerHash.put("NUMBER_OF_SPINDLES", numberOfSpindlesString);
            controllerHash.put("NUMBER_OF_SPINDLES", numberOfSpindlesInteger);
            //
            int[] syncTighteningIDIdxByteRange = {27, 31};
            int[] timeIdxByteRange = {33, 52};
            int[] syncOverallStatusIdxByteRange = {34, 35};
            //
            controllerHash.put("SYNC_TIGHTENING_ID", controllerMsg.substring(syncTighteningIDIdxByteRange[0], syncTighteningIDIdxByteRange[1]));
            controllerHash.put("TIME", controllerMsg.substring(timeIdxByteRange[0], timeIdxByteRange[1]));
            controllerHash.put("SYNC_OVERALL_STATUS", controllerMsg.substring(syncOverallStatusIdxByteRange[0], syncOverallStatusIdxByteRange[1]));
            //
            int spindleStatusIdxByteRange = 37;
            //
            for (int s=0; s < numberOfSpindlesInteger; s++) {
                //
                HashMap<String, String> tempHash = new HashMap<>();
                //
                //            String spindleNumber = "spindleNumber";
                //            String spindleNumber = "SPINDLE_NUMBER";
                String spindleNumber = "NUMBER";
                String spindleNumberValue = controllerMsg.substring(spindleStatusIdxByteRange + s*2, spindleStatusIdxByteRange + s*2 + 2);
                tempHash.put(spindleNumber, spindleNumberValue);
                //
                //            String channelId = "channelID";
                String channelId = "CHANNEL_ID";
                String channelIdValue = controllerMsg.substring(spindleStatusIdxByteRange + s*2 + 2, spindleStatusIdxByteRange + s*2 + 4);
                tempHash.put(channelId, channelIdValue);
                //
                //            String spindleStatus = "spindleStatus";
                String spindleStatus = "STATUS";
                String spindleStatusValue = controllerMsg.substring(spindleStatusIdxByteRange + s*2 + 4, spindleStatusIdxByteRange + s*2 + 5);
                tempHash.put(spindleStatus, spindleStatusValue);
                //
                controllerHash.put("SPINDLE_STATUS" + (s + 1), tempHash);
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

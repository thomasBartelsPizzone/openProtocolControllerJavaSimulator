package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0211 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0212";
        } else {
            return null;
        }
    }
    //
    // MID 0211 Status externally monitored inputs
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0028";
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
            for (Object s : dataFieldValue) {
                midAscii.append(s);
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
            //
            controllerHash.put("ACK_FLAG", controllerMsg.substring(ackFlagIdxByteRange[0], ackFlagIdxByteRange[1]));
            //
            for (int sD = 20; sD < 29; sD++) {
                //
                int[] statusDelayByteRange = {sD, sD + 1};
                String statusDelayValue = controllerMsg.substring(statusDelayByteRange[0], statusDelayByteRange[1]);
                switch (statusDelayValue) {
                    case "0":
                        controllerHash.put("STATUS_RELAY" + sD, "Off (reset)");
                        break;
                    case "1":
                        controllerHash.put("STATUS_RELAY", "On (set, fast)");
                        break;
                    case "2":
                        controllerHash.put("STATUS_RELAY", "Flashing");
                        break;
                    default:
                        controllerHash.put("STATUS_RELAY", "Keep current status");
                        break;
                }
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

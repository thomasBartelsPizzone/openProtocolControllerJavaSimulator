package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0100 {
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
    // MID 0100 Multi-spindle result subscribe
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        // String midLengthString = "0020";
        String midLengthString = null;
        //String midRevision = "000";
        String midRevision = null;
        // String midAckFlag = "0";
        String midAckFlag = "1";
        String midStationID = "00";
        String midSpindleID = "00";
        String midSpare = "0000";
        //
        StringBuilder tempMidAscii = new StringBuilder();
        StringBuilder midAscii = new StringBuilder();
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
                tempMidAscii.append(s);
            }
        }
        // Check Size
        int midLength = 4 + 4 + 3 + tempMidAscii.length(); // length of message + command + revision + remainder of msg
        // combine string
        // length
        midAscii.append(String.format("%04d", midLength));
        // command
        midAscii.append(midCommandValue);
        // revision
        if (midLength < 21) {
            midRevision = "001";
        } else if (midLength < 31) {
            midRevision = "002";
        } else {
            midRevision = "003";
        }
        midAscii.append(midRevision);
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
        //
        int[] ackFlagIdxByteRange = {11, 12};
        //
        controllerHash.put("ACK_FLAG", controllerMsg.substring(ackFlagIdxByteRange[0], ackFlagIdxByteRange[1]));
        //
        midControllerHash.put(midCommand, controllerHash);
        return midControllerHash;
    }
}

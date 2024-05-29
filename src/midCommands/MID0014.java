package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0014 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0015";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0015";
        } else {
            return null;
        }
    }
//    String[] replyMID(String answer) {
//        if (answer.equalsIgnoreCase("acknowledge")) {
//            String[] reply = new String[2];
//            reply[0] = "midCommands.MID0005";
//            reply[1] = "midCommands.MID0015";
//            //return "midCommands.MID0015";
//            return reply;
//        } else if (answer.equalsIgnoreCase("accept")) {
//            String[] reply = new String[2];
//            reply[0] = "midCommands.MID0005";
//            reply[1] = "midCommands.MID0015";
//            //return "midCommands.MID0015";
//            return reply;
//        } else if (answer.equalsIgnoreCase("error")) {
//            String[] reply = new String[1];
//            reply[0] = null;
//            return reply;
//            // return "midCommands.MID0004";
//        } else {
//            String[] reply = new String[1];
//            reply[0] = null;
//            return reply;
//            // return "midCommands.MID0004";
//        }
//    }
    //
    //
    // MID 0014 Parameter set selected subscribe
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
            controllerHash.put("ACK_FLAG", controllerMsg.substring(ackFlagIdxByteRange[0], ackFlagIdxByteRange[1]));
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

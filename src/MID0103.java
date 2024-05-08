import java.util.HashMap;
import java.util.List;


public class MID0103 {
    //
    String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "MID0005";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "MID0005";
        } else if (answer.equalsIgnoreCase("error")) {
            return "MID0004";
        } else {
            return "MID0004";
        }
    }
    //
    // MID 0103 Multi-spindle result unsubscribe
    String integratorString(String midCommandValue, List<Object> dataFieldValue) {
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
    HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        //
        midControllerHash.put(midCommand, controllerHash);
        return midControllerHash;
    }
}

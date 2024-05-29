package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0064 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0065";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0065";
        } else if (answer.equalsIgnoreCase("error")) {
            return "midCommands.MID0004";
        } else {
            return "midCommands.MID0004";
        }
    }
    //
    // MID 0064 Old tightening result upload request
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0030";
        // String midRevision = "001";
        // String midRevision = "002";
        String midRevision = "003";
        // String midRevision = "004";
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
            StringBuilder tempDataFieldSB = new StringBuilder();
            String tempDataField = null;
            for (Object s : dataFieldValue) {
                tempDataFieldSB.append(s);
            }
            int padLength = midLength - midAscii.length() - tempDataFieldSB.length();
            //tempDataField = padZeroes(tempDataFieldSB.toString(), padLength, "left");
            tempDataField = new opUtilityMethods(tempDataFieldSB.toString(), padLength, "left").padZeroes();
            midAscii.append(tempDataField);
            return (midAscii + "\0");
        } else {
            // padding
            if (midAscii.length() >= midLength) {
                 return (midAscii + "\0");
            } else {
                return (new opUtilityMethods(midAscii.toString(), midLength, "right").padZeroes() + "\0");
            }
        }
    }
    //
    public HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            // Data Field
            int[] tighteningIDIdxByteRange = {20, 30};
            //
            controllerHash.put("TIGHTENING_ID", controllerMsg.substring(tighteningIDIdxByteRange[0], tighteningIDIdxByteRange[1]));
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

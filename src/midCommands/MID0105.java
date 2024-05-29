package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0105 {
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
    // MID 0105 Last PowerMACS tightening result data subscribe
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        // String midLengthString = "0020";
        String midLengthString = null;
        // String midRevision = "002";
        String midRevision = null;
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
            for (Object s : dataFieldValue) {
                tempMidAscii.append(s);
            }
        }
        // Check Size
        int midLength = 4 + 4 + 3 + tempMidAscii.length(); // length of message + command + revision + remainder of msg
        // combine string
        // length
        midAscii.append(String.format("%04d", midLength));
        //midAscii.append(String.format("%04d", midLengthString));
        // command
        midAscii.append(midCommandValue);
        // revision
        if (midLength == 30) {
            midRevision = "002";
        } else if (midLength == 31) {
            midRevision = "003";
        } else {
            midRevision = "001";
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
        try {
            //
            int midRevisionInt = Integer.parseInt(midRevision);
            if (midRevisionInt > 1) {
                int[] dataNoSystemIdxByteRange = {20, 30};
                //
                controllerHash.put("DATA_NO_SYSTEM", controllerMsg.substring(dataNoSystemIdxByteRange[0], dataNoSystemIdxByteRange[1]));
                if (midRevisionInt > 2) {
                    //
                    int[] sendOnlyNewDataIdxByteRange = {30, 31};
                    //
                    controllerHash.put("SEND_ONLY_NEW_DATA", controllerMsg.substring(sendOnlyNewDataIdxByteRange[0], sendOnlyNewDataIdxByteRange[1]));
                }
            }
            //
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

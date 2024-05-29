package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0200 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0005";
        } else if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0005";
        } else {
            return null;
        }
    }
    //
    // MID 0200 Set externally controlled relays
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0030";
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
            for (int sR = 20; sR < 31; sR++) {
                //
                int[] statusRelayByteRange = {sR, sR + 1};
                String statusRelayValue = controllerMsg.substring(statusRelayByteRange[0], statusRelayByteRange[1]);
                switch (statusRelayValue) {
                    case "0":
                        controllerHash.put("STATUS_RELAY" + sR, "Off (reset)");
                        break;
                    case "1":
                        controllerHash.put("STATUS_RELAY" + sR, "On (set, fast)");
                        break;
                    case "2":
                        controllerHash.put("STATUS_RELAY" + sR, "Flashing");
                        break;
                    default:
                        controllerHash.put("STATUS_RELAY" + sR, "Keep current status");
                        break;
                }
                sR += 1;
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

package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0255 {
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
    // MID 0255 Selector control red lights
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0034";
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
                        //midAscii.append(String.format("%02d", s));
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
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
            return (new opUtilityMethods(toString(), midLength, "right").padZeroes() + "\0");
        }
    }
    //
    public HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            //
            int[] deviceIDIdxByteRange = {22, 24};
            controllerHash.put("DEVICE_ID", controllerMsg.substring(deviceIDIdxByteRange[0], deviceIDIdxByteRange[1]));
            //
            for (int csp = 26; csp < 35; csp++) {
                String greenLightCommandPositionValue = controllerMsg.substring(csp, csp + 1);
                switch (greenLightCommandPositionValue) {
                    case "0":
                        controllerHash.put("RED_LIGHT_COMMAND_SELECTOR_POSITION" + csp, "Off");
                        break;
                    case "1":
                        controllerHash.put("RED_LIGHT_COMMAND_SELECTOR_POSITION" + csp, "Steady");
                        break;
                    default:
                        controllerHash.put("RED_LIGHT_COMMAND_SELECTOR_POSITION" + csp, "Flashing");
                        break;
                }
                csp += 1;
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


package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0251 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0252";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0252";
        } else {
            return null;
        }
    }
    //
    // MID 0251 Selector socket info
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midRevision = "001";
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
            opUtilityMethods opM = new opUtilityMethods();
            int dfCount = 1;
            for (Object s : dataFieldValue) {
                if (dfCount < 3) {
                    tempMidAscii.append(String.format("%02d", dfCount));
                }
                if (opM.isNumeric(s)) {
                    if (dfCount < 3) {
                        tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else {
                        tempMidAscii.append(s);
                    }
                } else {
                    tempMidAscii.append(s);
                }
                dfCount += 1;
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
        midAscii.append(midRevision);
        // remainder
        midAscii.append(tempMidAscii);
        //
        // return (midAscii + "NUL");
        return (midAscii.toString());
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

            int[] numberOfSocketsByteRange = {26, 28};
            String numberOfSocketsString = controllerMsg.substring(numberOfSocketsByteRange[0], numberOfSocketsByteRange[1]);
            int numberOfSocketsInteger = Integer.parseInt(numberOfSocketsString);
            controllerHash.put("NUMBER_OF_SOCKETS", numberOfSocketsInteger);
            //
            for (int s=0; s < numberOfSocketsInteger; s++) {
                String socketStatus = "socketStatus" + (s + 1);
                String socketStatusAscii = controllerMsg.substring(30 + s, 30 + s + 1);
                controllerHash.put(socketStatus, socketStatusAscii);
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
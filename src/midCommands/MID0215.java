package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0215 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
    // MID 0215 IO device status reply
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midRevision = "002";
        String midAckFlag = "0";
        String midStationID = "00";
        String midSpindleID = "00";
        String midSpare = "0000";
        //
        StringBuilder tempMidAscii = new StringBuilder();
        StringBuilder midAscii = new StringBuilder();
        // MID
        midAscii.append(midCommandValue);
        // Revision
        midAscii.append(midRevision);
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
                if(dfCount < 3) {
                    tempMidAscii.append(String.format("%02d", dfCount));
                }
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    if (dfCount == 1) {
                        tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        //tempMidAscii.append(asciiDigits(100, "6", s));
                        //tempMidAscii.append(new midCommands.opUtilityMethods(100, 6, s).asciiDigits());
                    } else if (dfCount == 2) {
                        tempMidAscii.append(s);
                    } else {
                        tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                    }
                } else {
                    tempMidAscii.append(s);
                }
                dfCount += 1;
            }
        }
        // Check Size
        int midLength = 4 + tempMidAscii.length(); // length of message + command + revision + remainder of msg
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
            int[] ioDeviceIDIdxByteRange = {22, 24};
            controllerHash.put("IO_DEVICE_ID", controllerMsg.substring(ioDeviceIDIdxByteRange[0], ioDeviceIDIdxByteRange[1]));
            //
            if (midRevision.equals("001")) {
                int relayListIdx = 26;
                // Relay List
                for (int rl = 0; rl < 7; rl++) {
                    //
                    HashMap<String, String> tempHash = new HashMap<>();
                    String relayIndex = "NUMBER" + rl+1;
                    String relayValue = controllerMsg.substring(relayListIdx + rl*4, relayListIdx + rl*4 + 3);
                    tempHash.put(relayIndex, relayValue);

                    String relayStatusIndex = "STATUS" + rl+1;
                    String relayStatusValue = controllerMsg.substring(relayListIdx + rl*4 + 3, relayListIdx + rl*4 + 4);
                    if (relayStatusValue.equals("0")) {
                        tempHash.put(relayStatusIndex, "reset");
                    } else {
                        tempHash.put(relayStatusIndex, "set");
                    }
                    //
                    controllerHash.put("RELAY" + (rl + 1), tempHash);
                }
                //
                int digitalInputListIdx = 60;
                // Relay List
                for (int dl = 0; dl < 7; dl++) {
                    //
                    HashMap<String, String> tempHash = new HashMap<>();
                    String digitalInputIndex = "NUMBER" + dl+1;
                    String digitalInputValue = controllerMsg.substring(digitalInputListIdx + dl*4, digitalInputListIdx + dl*4 + 3);
                    tempHash.put(digitalInputIndex, digitalInputValue);

                    String digitalInputStatusIndex = "STATUS" + dl+1;
                    String digitalInputStatusValue = controllerMsg.substring(digitalInputListIdx + dl*4 + 3, digitalInputListIdx + dl*4 + 4);
                    if (digitalInputStatusValue.equals("0")) {
                        tempHash.put(digitalInputStatusIndex, "Low");
                    } else {
                        tempHash.put(digitalInputStatusIndex, "High");
                    }
                    //
                    controllerHash.put("DIGITAL_INPUT" + (dl + 1), tempHash);
                }
            } else if (midRevision.equals("002")) {
                int[] numberOfRelaysIdxByteRange = {26, 28};
                String numberOfRelaysString = controllerMsg.substring(numberOfRelaysIdxByteRange[0], numberOfRelaysIdxByteRange[1]);
                int numberOfRelaysInteger = Integer.parseInt(numberOfRelaysString);
                controllerHash.put("NUMBER_OF_RELAYS", numberOfRelaysInteger);

                int relayListIdx = 30;
                // Relay List
                for (int rl = 0; rl < numberOfRelaysInteger; rl++) {
                    //
                    HashMap<String, String> tempHash = new HashMap<>();
                    String relayIndex = "NUMBER" + rl+1;
                    String relayValue = controllerMsg.substring(relayListIdx + rl*4, relayListIdx + rl*4 + 3);
                    tempHash.put(relayIndex, relayValue);

                    String relayStatusIndex = "STATUS" + rl+1;
                    String relayStatusValue = controllerMsg.substring(relayListIdx + rl*4 + 3, relayListIdx + rl*4 + 4);
                    if (relayStatusValue.equals("0")) {
                        tempHash.put(relayStatusIndex, "reset");
                    } else {
                        tempHash.put(relayStatusIndex, "set");
                    }
                    //
                    controllerHash.put("RELAY" + (rl + 1), tempHash);
                }
                //
                int digitalInputListIdx = relayListIdx + numberOfRelaysInteger * 4 + 2;
                // Relay List
                for (int dl = 0; dl < 7; dl++) {
                    //
                    HashMap<String, String> tempHash = new HashMap<>();
                    String digitalInputIndex = "NUMBER" + dl+1;
                    String digitalInputValue = controllerMsg.substring(digitalInputListIdx + dl*4, digitalInputListIdx + dl*4 + 3);
                    tempHash.put(digitalInputIndex, digitalInputValue);

                    String digitalInputStatusIndex = "STATUS" + dl+1;
                    String digitalInputStatusValue = controllerMsg.substring(digitalInputListIdx + dl*4 + 3, digitalInputListIdx + dl*4 + 4);
                    if (digitalInputStatusValue.equals("0")) {
                        tempHash.put(digitalInputStatusIndex, "Low");
                    } else {
                        tempHash.put(digitalInputStatusIndex, "High");
                    }
                    //
                    controllerHash.put("DIGITAL_INPUT" + (dl + 1), tempHash);
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

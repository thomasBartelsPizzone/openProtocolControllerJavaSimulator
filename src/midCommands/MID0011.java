package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0011 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
    // MID 0011 Parameter set ID upload reply
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
        // MID
        tempMidAscii.append(midCommandValue);
        // Revision
        tempMidAscii.append(midRevision);
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
            int jobDataCount = 1;
            for (Object s : dataFieldValue) {
                if (s instanceof Integer) {
                    if (jobDataCount == 1) {
                        tempMidAscii.append(String.format("%03d", s));
                    } else {
                        tempMidAscii.append(s);
                    }
                } else {
                    tempMidAscii.append(s);
                }
                jobDataCount += 1;
            }
        }
        // Length
        int midLength = tempMidAscii.length() + 4;
        // combine string
        midAscii.append(String.format("%04d", midLength)).append(tempMidAscii);
        //
        return (midAscii + "\0");
    }
    //
    public HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            // Datafield-1
            int[] controllerParameterSetCountIdxByteRange = {20, 23};
            String parameterSetCountString = controllerMsg.substring(controllerParameterSetCountIdxByteRange[0], controllerParameterSetCountIdxByteRange[1]);
            //controllerHash.put("REQUEST_MID", controllerMsg.substring(controllerParameterSetCountIdxByteRange[0], controllerParameterSetCountIdxByteRange[1]));
            int parameterSetCountInteger = Integer.parseInt(parameterSetCountString);
            controllerHash.put("PARAMETER_SET_COUNT", parameterSetCountInteger);
            //
            // Datafield-2
            for (int p=0; p < parameterSetCountInteger; p++) {
                String parameterID = "PARAMETER_SET_ID" + (p + 1);
//                System.out.println("parameterID: " + parameterID);
                String parameterValue = controllerMsg.substring(23 + p*3, 23 + p*3 + 3);
                controllerHash.put(parameterID, parameterValue);
            }
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
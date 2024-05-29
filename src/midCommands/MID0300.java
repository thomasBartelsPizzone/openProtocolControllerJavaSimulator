package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0300 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0301";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0301";
        } else if (answer.equalsIgnoreCase("error")) {
            return "midCommands.MID0004";
        } else {
            return "midCommands.MID0004";
        }
    }
    //
    // MID 0300 Histogram upload request
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0029";
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
                //if (s instanceof Integer) {
                if (opM.isNumeric(s)) {
                    if (dfCount == 1) {
                        //midAscii.append(String.format("%03d", s));
                        midAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                    } else {
                        //midAscii.append(String.format("%02d", s));
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
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
            int[] parameterSetIDIdxByteRange = {22, 25};
            controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
            //
            int[] histogramTypeIdxByteRange = {27, 29};
            String histogramTypeValue = controllerMsg.substring(histogramTypeIdxByteRange[0], histogramTypeIdxByteRange[1]);
            switch (histogramTypeValue) {
                case "00":
                    controllerHash.put("HISTOGRAM_TYPE", "Torque");
                    break;
                case "01":
                    controllerHash.put("HISTOGRAM_TYPE", "Angle");
                    break;
                case "02":
                    controllerHash.put("HISTOGRAM_TYPE", "Current");
                    break;
                case "03":
                    controllerHash.put("HISTOGRAM_TYPE", "Prevail_Torque");
                    break;
                case "04":
                    controllerHash.put("HISTOGRAM_TYPE", "Self_Tap");
                    break;
                default:
                    controllerHash.put("HISTOGRAM_TYPE", "Rundown_Angle");
                    break;
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
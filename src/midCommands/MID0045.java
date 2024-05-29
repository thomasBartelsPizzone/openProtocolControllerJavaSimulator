package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0045 {
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
    // MID 0045 Set calibration value request
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = "0031";
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
            int calibrationCount = 1;
            for (Object s : dataFieldValue) {
                midAscii.append(String.format("%02d", calibrationCount));
                if (calibrationCount == 1) {
                    midAscii.append(s);
                } else {
                    //String calibValue = asciiDigits(100, "6", s);
                    //midAscii.append(calibValue);
                    midAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                }
                calibrationCount += 1;
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
            int[] calibrationValueUnitIdxByteRange = {22, 23};
            String calibrationValueUnit = controllerMsg.substring(calibrationValueUnitIdxByteRange[0], calibrationValueUnitIdxByteRange[1]);
            switch (calibrationValueUnit) {
                case "1":
                    controllerHash.put("CALIBRATION_VALUE_UNIT", "Nm");
                    break;
                case "2":
                    controllerHash.put("CALIBRATION_VALUE_UNIT", "Lbf.ft");
                    break;
                case "3":
                    controllerHash.put("CALIBRATION_VALUE_UNIT", "Lbf.In");
                    break;
                case "4":
                    controllerHash.put("CALIBRATION_VALUE_UNIT", "Kpm");
                    break;
                case "5":
                    controllerHash.put("CALIBRATION_VALUE_UNIT", "Kgf.cm");
                    break;
                case "6":
                    controllerHash.put("CALIBRATION_VALUE_UNIT", "ozf.in");
                    break;
                default:
                    controllerHash.put("CALIBRATION_VALUE_UNIT", "%");
                    break;
            }
            //
            int[] calibrationValueIdxByteRange = {25, 31};
            String calibrationValueStringAscii = controllerMsg.substring(calibrationValueIdxByteRange[0], calibrationValueIdxByteRange[1]);
            //String calibrationValueString = inverseAsciiDigits(100, 6, calibrationValueStringAscii);
            String calibrationValueString = new opUtilityMethods(100, 6, calibrationValueStringAscii).inverseAsciiDigits();
            controllerHash.put("CALIBRATION_VALUE", calibrationValueString);
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

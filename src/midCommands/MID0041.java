package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0041 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
    // MID 0041 Tool data upload reply
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
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
                tempMidAscii.append(String.format("%02d", dfCount));
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    if (dfCount == 5) {
                        //tempMidAscii.append(asciiDigits(100, "6", s));
                        tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                    } else if (dfCount == 9) {
                        //tempMidAscii.append(String.format("%02d", s));
                        tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else {
                        tempMidAscii.append(s);
                    }
                } else {
                    if (dfCount == 5) {
                        //tempMidAscii.append(asciiDigits(100, "6", s));
                        tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                    } else {
                        tempMidAscii.append(s);
                    }
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
        if (midLength == 81) {
            midAscii.append("001");
        } else {
            midAscii.append("002");
        }
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
            int[] toolSerialNumberIdxByteRange = {22, 36};
            //
            int[] toolNumberOfTighteningIdxByteRange = {38, 48};
            //
            int[] lastCalibrationDateIdxByteRange = {50, 69};
            //
            int[] controllerSerialNumberIdxByteRange = {71, 81};
            //
            controllerHash.put("TOOL_SERIAL_NUMBER", controllerMsg.substring(toolSerialNumberIdxByteRange[0], toolSerialNumberIdxByteRange[1]));
            controllerHash.put("TOOL_NUMBER_OF_TIGHTENING", controllerMsg.substring(toolNumberOfTighteningIdxByteRange[0], toolNumberOfTighteningIdxByteRange[1]));
            controllerHash.put("LAST_CALIBRATION_DATE", controllerMsg.substring(lastCalibrationDateIdxByteRange[0], lastCalibrationDateIdxByteRange[1]));
            controllerHash.put("CONTROLLER_SERIAL_NUMBER", controllerMsg.substring(controllerSerialNumberIdxByteRange[0], controllerSerialNumberIdxByteRange[1]));
            //
            if (midRevision.equals("002")) {
                int[] calibrationValueIdxByteRange = {83, 89};
                String calibrationValueStringAscii = controllerMsg.substring(calibrationValueIdxByteRange[0], calibrationValueIdxByteRange[1]);
                //String calibrationValueString = inverseAsciiDigits(100, 6, calibrationValueStringAscii);
                String calibrationValueString = new opUtilityMethods(100, 6, calibrationValueStringAscii).inverseAsciiDigits();
                controllerHash.put("CALIBRATION_VALUE", calibrationValueString);
                //
                int[] lastServiceDateIdxByteRange = {91, 110};
                int[] tighteningsSinceServiceIdxByteRange = {112, 122};
                controllerHash.put("LAST_SERVICE_DATE", controllerMsg.substring(lastServiceDateIdxByteRange[0], lastServiceDateIdxByteRange[1]));
                controllerHash.put("TIGHTENING_SINCE_SERVICE", controllerMsg.substring(tighteningsSinceServiceIdxByteRange[0], tighteningsSinceServiceIdxByteRange[1]));
                //
                int[] toolTypeIdxByteRange = {124, 126};
                String toolTypeValue = controllerMsg.substring(toolTypeIdxByteRange[0], toolTypeIdxByteRange[1]);
                switch (toolTypeValue) {
                    case "01":
                        controllerHash.put("TOOL_TYPE", "S-tool");
                        break;
                    case "02":
                        controllerHash.put("TOOL_TYPE", "DS-tool");
                        break;
                    case "03":
                        controllerHash.put("TOOL_TYPE", "Ref. transducer");
                        break;
                    case "04":
                        controllerHash.put("TOOL_TYPE", "ST-tool");
                        break;
                    case "05":
                        controllerHash.put("TOOL_TYPE", "EP-tool");
                        break;
                    case "06":
                        controllerHash.put("TOOL_TYPE", "ETX-tool");
                        break;
                    case "07":
                        controllerHash.put("TOOL_TYPE", "SL-tool");
                        break;
                    case "08":
                        controllerHash.put("TOOL_TYPE", "DL-tool");
                        break;
                    case "09":
                        controllerHash.put("TOOL_TYPE", "STB(offline)");
                        break;
                    case "10":
                        controllerHash.put("TOOL_TYPE", "STB(online)");
                        break;
                    case "11":
                        controllerHash.put("TOOL_TYPE", "QST-tool");
                        break;
                }
                //
                int[] motorSizeIdxByteRange = {128, 130};
                controllerHash.put("MOTOR_SIZE", controllerMsg.substring(motorSizeIdxByteRange[0], motorSizeIdxByteRange[1]));

                int[] openEndDataUseIdxByteRange = {132, 133};
                String openEndDataUseValue = controllerMsg.substring(openEndDataUseIdxByteRange[0], openEndDataUseIdxByteRange[1]);
                if (openEndDataUseValue.equals("0")) {
                    controllerHash.put("OPEN_END_DATA_USE", "FALSE");
                } else {
                    controllerHash.put("OPEN_END_DATA_USE", "TRUE");
                }

                int[] openEndDataTighteningDirectionIdxByteRange = {133, 134};
                String[] tighteningDirectionArray = {"CW", "CCW"};
                String tighteningDirectionValue = controllerMsg.substring(openEndDataTighteningDirectionIdxByteRange[0], openEndDataTighteningDirectionIdxByteRange[1]);
                if (tighteningDirectionValue.equals("0")) {
                    controllerHash.put("OPEN_END_DATA_TIGHTENING_DIRECTION", tighteningDirectionArray[0]);
                } else {
                    controllerHash.put("OPEN_END_DATA_TIGHTENING_DIRECTION", tighteningDirectionArray[1]);
                }
                //
                int[] openEndDataMotorRotationIdxByteRange = {134, 135};
                String openEndDataMotorRotationValue = controllerMsg.substring(openEndDataMotorRotationIdxByteRange[0], openEndDataMotorRotationIdxByteRange[1]);
                if (openEndDataMotorRotationValue.equals("0")) {
                    controllerHash.put("OPEN_END_DATA_MOTOR_ROTATION", "NORMAL");
                } else {
                    controllerHash.put("OPEN_END_DATA_MOTOR_ROTATION", "INVERTED");
                }
                //
                int[] controllerSWVersionIdxByteRange = {137, 156};
                controllerHash.put("CONTROLLER_SW_VERSION", controllerMsg.substring(controllerSWVersionIdxByteRange[0], controllerSWVersionIdxByteRange[1]));
            }
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

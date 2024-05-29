package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0013 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
    // MID 0013 Parameter set data upload reply
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = "0104";
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
                        //midAscii.append(String.format("%03d", s));
                        midAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                    } else if (dfCount == 2) {
                        //midAscii.append(String.format("%25d", s));
                        midAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                    } else if (dfCount == 4) {
                        //midAscii.append(String.format("%02d", s));
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else if (dfCount > 4 && dfCount < 8) {
                        //midAscii.append(asciiDigits(100, "6", s));
                        midAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                    } else if (dfCount >= 8) {
                        //midAscii.append(String.format("%05d", s));
                        midAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                    } else {
                        midAscii.append(s);
                    }
                } else {
                    if (dfCount == 2) {
                        String parameterSetName = (String) s;
                        if (parameterSetName.length() < 25) {
                            //midAscii.append(padZeroes(parameterSetName, 25, "right"));
                            midAscii.append(new opUtilityMethods(100, 25, s).asciiDigits());
                        } else {
                            midAscii.append(parameterSetName);
                        }
                    } else if (dfCount > 4 && dfCount < 8) {
                        //midAscii.append(asciiDigits(100, "6", s));
                        midAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                    } else {
                        midAscii.append(s);
                    }
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
            int midRevisionInt = Integer.parseInt(midRevision);
            //
            if (midRevisionInt != 3 && midRevisionInt != 4) {
                int[] parameterSetIDIdxByteRange = {22, 25};
                int[] parameterSetNameIdxByteRange = {27, 52};
                controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
                controllerHash.put("PARAMETER_SET_NAME", controllerMsg.substring(parameterSetNameIdxByteRange[0], parameterSetNameIdxByteRange[1]));

                int[] rotationDirectionIdxByteRange = {54, 55};
                String[] rotationDirectionArray = {"CW", "CCW", "NA"};
                String rotationDirectionValue = controllerMsg.substring(rotationDirectionIdxByteRange[0], rotationDirectionIdxByteRange[1]);
                if (rotationDirectionValue.equals("1")) {
                    controllerHash.put("ROTATION_DIRECTION", rotationDirectionArray[0]);
                } else if (rotationDirectionValue.equals("2")) {
                    controllerHash.put("ROTATION_DIRECTION", rotationDirectionArray[1]);
                } else {
                    controllerHash.put("ROTATION_DIRECTION", rotationDirectionArray[2]);
                }

                int[] batchSizeIdxByteRange = {57, 59};
                controllerHash.put("BATCH_SIZE", controllerMsg.substring(batchSizeIdxByteRange[0], batchSizeIdxByteRange[1]));
                //
                int midLength = Integer.parseInt(midLengthString);
                //
                //
                int[] torqueMinIdxByteRange = {61, 67};
                String torqueMinStringAscii = controllerMsg.substring(torqueMinIdxByteRange[0], torqueMinIdxByteRange[1]);
                //String torqueMinString = inverseAsciiDigits(100, 6, torqueMinStringAscii);
                String torqueMinString = new opUtilityMethods(100, 6, torqueMinStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_MIN", torqueMinString);

                int[] torqueMaxIdxByteRange = {69, 75};
                String torqueMaxStringAscii = controllerMsg.substring(torqueMaxIdxByteRange[0], torqueMaxIdxByteRange[1]);
                //String torqueMaxString = inverseAsciiDigits(100, 6, torqueMaxStringAscii);
                String torqueMaxString = new opUtilityMethods(100, 6, torqueMaxStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_MAX", torqueMaxString);

                int[] torqueFinalTargetIdxByteRange = {77, 83};
                String torqueFinalTargetStringAscii = controllerMsg.substring(torqueFinalTargetIdxByteRange[0], torqueFinalTargetIdxByteRange[1]);
                //String torqueFinalTargetString = inverseAsciiDigits(100, 6, torqueFinalTargetStringAscii);
                String torqueFinalTargetString = new opUtilityMethods(100, 6, torqueFinalTargetStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_FINAL_TARGET", torqueFinalTargetString);
                //
                int[] angleMinIdxByteRange = {85, 90};
                controllerHash.put("ANGLE_MIN", controllerMsg.substring(angleMinIdxByteRange[0], angleMinIdxByteRange[1]));

                int[] angleMaxIdxByteRange = {92, 97};
                controllerHash.put("ANGLE_MAX", controllerMsg.substring(angleMaxIdxByteRange[0], angleMaxIdxByteRange[1]));

                int[] finalAngleTargetIdxByteRange = {99, 104};
                controllerHash.put("FINAL_ANGLE_TARGET", controllerMsg.substring(finalAngleTargetIdxByteRange[0], finalAngleTargetIdxByteRange[1]));
                //
                if (midRevisionInt > 1) {
                    //
                    int[] firstTargetIdxByteRange = {106, 112};
                    String firstTargetValueStringAscii = controllerMsg.substring(firstTargetIdxByteRange[0], firstTargetIdxByteRange[1]);
                    String firstTargetValueString = new opUtilityMethods(100, 6, firstTargetValueStringAscii).inverseAsciiDigits();
                    controllerHash.put("FIRST_TARGET", firstTargetValueString);

                    int[] startFinalAngleIdxByteRange = {106, 112};
                    String startFinalAngleValueStringAscii = controllerMsg.substring(startFinalAngleIdxByteRange[0], startFinalAngleIdxByteRange[1]);
                    String startFinalAngleValueString = new opUtilityMethods(100, 6, startFinalAngleValueStringAscii).inverseAsciiDigits();
                    controllerHash.put("START_FINAL_ANGLE", startFinalAngleValueString);

                    if (midRevisionInt > 2) {
                        int[] dateOfChangeParameterIdxByteRange = {122, 141};
                        controllerHash.put("DATE_OF_LAST_CHANGE_IN_PARAMETER_SET_SETTING", controllerMsg.substring(dateOfChangeParameterIdxByteRange[0], dateOfChangeParameterIdxByteRange[1]));
                    }
                }
            } else {
                controllerHash.put("PARAMETER_SET_DATA", controllerMsg.substring(28));
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
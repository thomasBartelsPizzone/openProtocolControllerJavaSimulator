package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0101 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0102";
        } else {
            return null;
        }
    }
    //
    // MID 0101 Multi-spindle status data
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = null;
        String midRevision = null;
        // String midAckFlag = "0";
        String midAckFlag = "1";
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
            opUtilityMethods opM = new opUtilityMethods();
            // Check Size
            int inputCount = dataFieldValue.size();
            // revision
            if (inputCount == 18) {
                midRevision = "003";
            } else if (inputCount == 19) {
                midRevision = "004";
            } else {
                midRevision = "006";
            }
            //
            if (midRevision.equals("003")) {
                int dFCount = 1;
                int spindleCount = 0;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 1) {
                            spindleCount = (int) s;
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 2) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 3) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 4) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 4 && dFCount < 7) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 7) {
                            tempMidAscii.append(s);
                        } else if (dFCount > 7 && dFCount < 11) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 11 && dFCount < 14) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 16) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 17) {
                            if (spindleCount == 1) {
                                for (int i = 0; i < spindleCount; i++) {
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 0, 2);
                                    // Channel Id
                                    tempMidAscii.append(s.toString(), 2, 4);
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 4, 5);
                                    // Spindle Torque Status
                                    tempMidAscii.append(s.toString(), 5, 6);
                                    // Spindle Torque Result
                                    //tempMidAscii.append(asciiDigits(100, "6", s.toString().substring(6, 12)));
                                    tempMidAscii.append(new opUtilityMethods(100, 6, s.toString().substring(6, 12)));
                                    // Spindle Angle Status
                                    tempMidAscii.append(s.toString(), 12, 13);
                                    // Spindle Turning Angle Value
                                    tempMidAscii.append(s.toString(), 13, 18);
                                }
                            }
                            tempMidAscii.append(s);
                        } else {
                            tempMidAscii.append(s);
                        }
                    } else {
                        if (dFCount > 7 && dFCount < 11) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 17) {
                            if (spindleCount == 1) {
                                for (int i = 0; i < spindleCount; i++) {
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 0, 2);
                                    // Channel Id
                                    tempMidAscii.append(s.toString(), 2, 4);
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 4, 5);
                                    // Spindle Torque Status
                                    tempMidAscii.append(s.toString(), 5, 6);
                                    // Spindle Torque Result
                                    //tempMidAscii.append(asciiDigits(100, "6", s.toString().substring(6, 12)));
                                    tempMidAscii.append(new opUtilityMethods(100, 6, s.toString().substring(6, 12)));
                                    // Spindle Angle Status
                                    tempMidAscii.append(s.toString(), 12, 13);
                                    // Spindle Turning Angle Value
                                    tempMidAscii.append(s.toString(), 13, 18);
                                }
                            }
                            tempMidAscii.append(s);
                        } else {
                            tempMidAscii.append(s);
                        }
                    }
                    dFCount += 1;
                }
            } else {
                int dFCount = 1;
                int spindleCount = 0;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 1) {
                            spindleCount = (int) s;
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 2) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 3) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 4) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 4 && dFCount < 7) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 7) {
                            tempMidAscii.append(s);
                        } else if (dFCount > 7 && dFCount < 11) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 11 && dFCount < 14) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 16) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 17) {
                            if (spindleCount == 1) {
                                for (int i = 0; i < spindleCount; i++) {
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 0, 2);
                                    // Channel Id
                                    tempMidAscii.append(s.toString(), 2, 4);
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 4, 5);
                                    // Spindle Torque Status
                                    tempMidAscii.append(s.toString(), 5, 6);
                                    // Spindle Torque Result
                                    //tempMidAscii.append(asciiDigits(100, "6", s.toString().substring(6, 12)));
                                    tempMidAscii.append(new opUtilityMethods(100, 6, s.toString().substring(6, 12)));
                                    // Spindle Angle Status
                                    tempMidAscii.append(s.toString(), 12, 13);
                                    // Spindle Turning Angle Value
                                    tempMidAscii.append(s.toString(), 13, 18);
                                }
                            }
                            tempMidAscii.append(s);
                        } else {
                            tempMidAscii.append(s);
                        }
                    } else {
                        if (dFCount > 7 && dFCount < 11) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 17) {
                            if (spindleCount == 1) {
                                for (int i = 0; i < spindleCount; i++) {
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 0, 2);
                                    // Channel Id
                                    tempMidAscii.append(s.toString(), 2, 4);
                                    // Spindle Number
                                    tempMidAscii.append(s.toString(), 4, 5);
                                    // Spindle Torque Status
                                    tempMidAscii.append(s.toString(), 5, 6);
                                    // Spindle Torque Result
                                    //tempMidAscii.append(asciiDigits(100, "6", s.toString().substring(6, 12)));
                                    tempMidAscii.append(new opUtilityMethods(100, 6, s.toString().substring(6, 12)));
                                    // Spindle Angle Status
                                    tempMidAscii.append(s.toString(), 12, 13);
                                    // Spindle Turning Angle Value
                                    tempMidAscii.append(s.toString(), 13, 18);
                                }
                            }
                            tempMidAscii.append(s);
                        } else {
                            tempMidAscii.append(s);
                        }
                    }
                    dFCount += 1;
                }
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
            int midRevisionInt = Integer.parseInt(midRevision);
            //
            int[] numberOfSpindlesIdxByteRange = {22, 24};
            String numberOfSpindlesString = controllerMsg.substring(numberOfSpindlesIdxByteRange[0], numberOfSpindlesIdxByteRange[1]);
            int numberOfSpindlesInteger = Integer.parseInt(numberOfSpindlesString);
            //        controllerHash.put("NUMBER_OF_SPINDLES", numberOfSpindlesString);
            controllerHash.put("NUMBER_OF_SPINDLES", numberOfSpindlesInteger);
            //
            int[] vinNumberIdxByteRange = {26, 51};
            int[] jobIdIdxByteRange = {53, 55};
            int[] parameterSetIdIdxByteRange = {57, 60};
            int[] BatchSizeIdxByteRange = {62, 66};
            //
            controllerHash.put("VIN_NUMBER", controllerMsg.substring(vinNumberIdxByteRange[0], vinNumberIdxByteRange[1]));
            controllerHash.put("JOB_ID", controllerMsg.substring(jobIdIdxByteRange[0], jobIdIdxByteRange[1]));
            controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIdIdxByteRange[0], parameterSetIdIdxByteRange[1]));
            controllerHash.put("BATCH_SIZE", controllerMsg.substring(BatchSizeIdxByteRange[0], BatchSizeIdxByteRange[1]));
            //
            int[] BatchCounterByteRange = {68, 72};
            String[] BatchCounterArray = {"LOW", "OK", "HIGH"};
            String BatchCounterValue = controllerMsg.substring(BatchCounterByteRange[0], BatchCounterByteRange[1]);
            if (BatchCounterValue.equals("0000")) {
                controllerHash.put("BATCH_COUNTER", BatchCounterArray[0]);
            } else if (BatchCounterValue.equals("0001")) {
                controllerHash.put("BATCH_COUNTER", BatchCounterArray[1]);
            } else {
                controllerHash.put("BATCH_COUNTER", BatchCounterArray[2]);
            }
            //
            int[] batchStatusIdxByteRange = {75, 76};
            String[] batchStatusArray = {"BATCH_NOK", "BATCH_OK", "BATCH_NOT_USED"};
            String batchStatusValue = controllerMsg.substring(batchStatusIdxByteRange[0], batchStatusIdxByteRange[1]);
            if (batchStatusValue.equals("0")) {
                controllerHash.put("BATCH_STATUS", batchStatusArray[0]);
            } else if (batchStatusValue.equals("1")) {
                controllerHash.put("BATCH_STATUS", batchStatusArray[1]);
            } else {
                controllerHash.put("BATCH_STATUS", batchStatusArray[2]);
            }
            //
            int[] torqueMinLimitIdxByteRange = {78, 84};
            String torqueMinLimitStringAscii = controllerMsg.substring(torqueMinLimitIdxByteRange[0], torqueMinLimitIdxByteRange[1]);
            //String torqueMinLimitString = inverseAsciiDigits(100, 6, torqueMinLimitStringAscii);
            String torqueMinLimitString = new opUtilityMethods(100, 6, torqueMinLimitStringAscii).inverseAsciiDigits();
            controllerHash.put("TORQUE_MIN_LIMIT", torqueMinLimitString);

            int[] torqueMaxLimitIdxByteRange = {86, 92};
            String torqueMaxLimitStringAscii = controllerMsg.substring(torqueMaxLimitIdxByteRange[0], torqueMaxLimitIdxByteRange[1]);
            //String torqueMaxLimitString = inverseAsciiDigits(100, 6, torqueMaxLimitStringAscii);
            String torqueMaxLimitString = new opUtilityMethods(100, 6, torqueMaxLimitStringAscii).inverseAsciiDigits();
            controllerHash.put("TORQUE_MAX_LIMIT", torqueMaxLimitString);

            int[] torqueFinalTargetIdxByteRange = {94, 100};
            String torqueFinalTargetStringAscii = controllerMsg.substring(torqueFinalTargetIdxByteRange[0], torqueFinalTargetIdxByteRange[1]);
            //String torqueFinalTargetString = inverseAsciiDigits(100, 6, torqueFinalTargetStringAscii);
            String torqueFinalTargetString = new opUtilityMethods(100, 6, torqueFinalTargetStringAscii).inverseAsciiDigits();
            controllerHash.put("TORQUE_FINAL_TARGET", torqueFinalTargetString);
            //
            int[] angleMinIdxByteRange = {102, 107};
            int[] angleMaxIdxByteRange = {109, 114};
            int[] finalAngleTargetIdxByteRange = {116, 121};
            //
            int[] lastChangeDateIdxByteRange = {123, 142};
            int[] timeStampIdxByteRange = {123, 142};
            //
            controllerHash.put("ANGLE_MIN", controllerMsg.substring(angleMinIdxByteRange[0], angleMinIdxByteRange[1]));
            controllerHash.put("ANGLE_MAX", controllerMsg.substring(angleMaxIdxByteRange[0], angleMaxIdxByteRange[1]));
            controllerHash.put("FINAL_ANGLE_TARGET", controllerMsg.substring(finalAngleTargetIdxByteRange[0], finalAngleTargetIdxByteRange[1]));
            //
            controllerHash.put("DATE_LAST_PARAMETER_SET_CHANGE", controllerMsg.substring(lastChangeDateIdxByteRange[0], lastChangeDateIdxByteRange[1]));
            controllerHash.put("TIMESTAMP", controllerMsg.substring(timeStampIdxByteRange[0], timeStampIdxByteRange[1]));
            //
            int[] syncTighteningIDIdxByteRange = {144, 149};
            int[] syncOverallStatusIdxByteRange = {151, 152};
            //
            controllerHash.put("SYNC_TIGHTENING_ID", controllerMsg.substring(syncTighteningIDIdxByteRange[0], syncTighteningIDIdxByteRange[1]));
            controllerHash.put("SYNC_OVERALL_STATUS", controllerMsg.substring(syncOverallStatusIdxByteRange[0], syncOverallStatusIdxByteRange[1]));
            //
            int spindleStatusIdxByteRange = 154;
            //
            for (int s=0; s < numberOfSpindlesInteger; s++) {
                //
                HashMap<String, String> tempHash = new HashMap<>();
                //
                // String spindleNumber = "spindleNumber";
                String spindleNumber = "NUMBER";
                String spindleNumberValue = controllerMsg.substring(spindleStatusIdxByteRange + s*18, spindleStatusIdxByteRange + s*18 + 2);
                tempHash.put(spindleNumber, spindleNumberValue);
                //
                // String channelId = "channelID";
                String channelId = "CHANNEL_ID";
                String channelIdValue = controllerMsg.substring(spindleStatusIdxByteRange + s*18 + 2, spindleStatusIdxByteRange + s*18 + 4);
                tempHash.put(channelId, channelIdValue);
                //
                // String spindleStatus = "spindleTighteningStatus";
                String spindleStatus = "TIGHTENING_STATUS";
                String spindleStatusValue = controllerMsg.substring(spindleStatusIdxByteRange + s*18 + 4, spindleStatusIdxByteRange + s*18 + 5);
                tempHash.put(spindleStatus, spindleStatusValue);
                //
                // String torqueStatus = "spindleTorqueStatus";
                String torqueStatus = "TORQUE_STATUS";
                String torqueStatusValue = controllerMsg.substring(spindleStatusIdxByteRange + s*18 + 5, spindleStatusIdxByteRange + s*18 + 6);
                tempHash.put(torqueStatus, torqueStatusValue);
                //
                // String torqueResult = "spindleTorqueResult";
                String torqueResult = "TORQUE_RESULT";
                String torqueResultValueStringAscii = controllerMsg.substring(spindleStatusIdxByteRange + s*18 + 6, spindleStatusIdxByteRange + s*18 + 12);
                //String torqueResultValueString = inverseAsciiDigits(100, 6, torqueResultValueStringAscii);
                String torqueResultValueString = new opUtilityMethods(100, 6, torqueResultValueStringAscii).inverseAsciiDigits();
                tempHash.put(torqueResult, torqueResultValueString);
                //
                // String angleStatus = "spindleAngleStatus";
                String angleStatus = "ANGLE_STATUS";
                String angleStatusValue = controllerMsg.substring(spindleStatusIdxByteRange + s*12, spindleStatusIdxByteRange + s*18 + 13);
                tempHash.put(angleStatus, angleStatusValue);
                //
                // String angleName = "spindleAngleValue";
                String angleName = "ANGLE_VALUE";
                String angleValue = controllerMsg.substring(spindleStatusIdxByteRange + s*18 + 13, spindleStatusIdxByteRange + s*18 + 18);
                tempHash.put(angleName, angleValue);
                //
                // controllerHash.put("SPINDLE_" + (s + 1) + "_STATUS", tempHash);
                controllerHash.put("SPINDLE_STATUS" + (s + 1), tempHash);
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

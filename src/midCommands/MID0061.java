package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0061 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0062";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0062";
        } else {
            return null;
        }
    }
    //
    //  MID 0061 Last tightening result data
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
            // revision as int
            int midRevisionInt = 0;
            // revision
            if (inputCount == 23) {
                midRevision = "001";
                midLengthString = "0231";
            } else if (inputCount == 46) {
                midRevision = "002";
                midLengthString = "0385";
            } else if (inputCount == 49) {
                midRevision = "003";
                midLengthString = "0419";
            } else if (inputCount == 52) {
                midRevision = "004";
                midLengthString = "0500";
            } else if (inputCount == 53) {
                midRevision = "005";
                midLengthString = "0506";
            } else if (inputCount == 55) {
                midRevision = "006";
                midLengthString = "0526";
//            } else if (inputCount == 57) {
//                midRevision = "007";
//                midLengthString = "0544";
//            } else if (inputCount == 61) {
//                midRevision = "008";
//                midLengthString = "0571";
//            } else if (inputCount == 64) {
//                midRevision = "009";
//                midLengthString = "0592";
//            } else if (inputCount == 74) {
//                midRevision = "010";
//                midLengthString = "0662";
//            } else if (inputCount == 76) {
//                midRevision = "011";
//                midLengthString = "0677";
            } else if (inputCount == 58) {
                midRevision = "998";
                midLengthString = "0677";
            } else if (inputCount == 14) {
                midRevision = "999";
                midLengthString = "0121";
            } else {
                midRevision = "998";
            }
            midRevisionInt = Integer.parseInt(midRevision);
            //
            if (midRevision.equals("001")) {
                int dFCount = 1;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 1) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 2) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 2 && dFCount < 5) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 5) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 6) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 6 && dFCount < 9) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount >= 9 && dFCount <= 11) {
                            tempMidAscii.append(s);//
                        } else if (dFCount > 11 && dFCount < 16) {
                            //midAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 16 && dFCount < 19) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 20 || dFCount == 21) {
                            //midAscii.append(s);
                            tempMidAscii.append(s);
                        } else {
                            tempMidAscii.append(s);
                        }
                    } else {
                        if (dFCount > 11 && dFCount < 16) {
                            //midAscii.append(asciiDigits(100, 6, s));
                            midAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else {
                            midAscii.append(s);
                        }
                    }
                    dFCount += 1;
                }
            //} else if (midRevision.equals("002")) {
            } else if (midRevisionInt > 1 && midRevisionInt < 999) {
                int dFCount = 1;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 1) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 2) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 2 && dFCount < 5) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 5) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 6) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 7) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 8) {
                            tempMidAscii.append(s);
                        } else if (dFCount >= 9 && dFCount <= 10) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 10 && dFCount < 20) {
                            tempMidAscii.append(s);
                        } else if (dFCount == 20) {
                            tempMidAscii.append(s);
                        } else if (dFCount > 20 && dFCount < 25) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 25 && dFCount < 32) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount >= 32 && dFCount < 35) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount >= 35 && dFCount < 41) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount == 41) {
                            tempMidAscii.append(String.format("%10d", Integer.parseInt(s.toString())));
                        } else if (dFCount >= 42 && dFCount <= 43) {
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 44) {
                            tempMidAscii.append(String.format("%14d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 44 && dFCount < 47) {
                            tempMidAscii.append(s);
                        }
                        //
                        if (midRevisionInt > 2) {
                            if (dFCount == 47) {
                                tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                            } else if (dFCount == 48) {
                                tempMidAscii.append(String.format("%1d", Integer.parseInt(s.toString())));
                            } else if (dFCount == 49) {
                                tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                            }
                            if (midRevisionInt > 3) {
                                if (dFCount >= 50 && dFCount < 53) {
                                    tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                                }
                                if (midRevisionInt > 4) {
                                    if (dFCount == 53) {
                                        tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                                    }
                                    if (midRevisionInt > 5) {
                                        if (dFCount == 54) {
                                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                                        } else if (dFCount == 55) {
                                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                                        }
                                        if (midRevisionInt > 6) {
                                            if (dFCount == 56) {
                                                tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                                            } else if (dFCount == 57) {
                                                tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                                            } else if (dFCount == 58) {
                                                int s1 = Integer.parseInt(s.toString().substring(0, 6));
                                                String s2 = s.toString().substring(6);
                                                tempMidAscii.append(new opUtilityMethods(100, 6, s1).asciiDigits());
                                                tempMidAscii.append(String.format("%05d", Integer.parseInt(s2)));
                                            } else {
                                                tempMidAscii.append(s);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (dFCount > 20 && dFCount < 25) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 35 && dFCount < 41) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount == 54) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount == 56) {
                            //tempMidAscii.append(asciiDigits(100, 7, s));
                            tempMidAscii.append(new opUtilityMethods(100, 7, s).asciiDigits());
                        } else if (dFCount == 57) {
                            //tempMidAscii.append(asciiDigits(100, 7, s));
                            tempMidAscii.append(new opUtilityMethods(100, 7, s).asciiDigits());
                        } else if (dFCount == 58) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 59 && dFCount < 62) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 62 && dFCount < 65) {
                            //tempMidAscii.append(asciiDigits(100, 5, s));
                            tempMidAscii.append(new opUtilityMethods(100, 5, s).asciiDigits());

                            //65 & 66 represent the numerator and denominator in scaling factor
                        } else if (dFCount == 65) {
                            //tempMidAscii.append(asciiDigits(100, 5, s));
                            tempMidAscii.append(new opUtilityMethods(100, 5, s).asciiDigits());
                        } else if (dFCount == 66) {
                            //tempMidAscii.append(asciiDigits(1, 5, s));
                            tempMidAscii.append(new opUtilityMethods(1, 5, s).asciiDigits());
                        } else if (dFCount >= 71 && dFCount < 76) {
                            //tempMidAscii.append(asciiDigits(100, 6, s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else {
                            tempMidAscii.append(s);
                        }
                    }
                    dFCount += 1;
                }
            } else {
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(s);
                }
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
        return (midAscii + "\0");
    }
    //
    public HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            // collect revision for parser settings
            int midRevisionInt = Integer.parseInt(midRevision);
            //
            if (midRevision.equals("001")) {
                //
                int[] cellIdIdxByteRange = {22, 26};
                //
                int[] channelIdIdxByteRange = {28, 30};
                //
                int[] torqueControllerIdIdxByteRange = {32, 57};
                //
                int[] vinNumberIdxByteRange = {59, 84};
                //
                controllerHash.put("CELL_ID", controllerMsg.substring(cellIdIdxByteRange[0], cellIdIdxByteRange[1]));
                controllerHash.put("CHANNEL_ID", controllerMsg.substring(channelIdIdxByteRange[0], channelIdIdxByteRange[1]));
                controllerHash.put("TORQUE_CONTROLLER_NAME", controllerMsg.substring(torqueControllerIdIdxByteRange[0], torqueControllerIdIdxByteRange[1]));
                controllerHash.put("VIN_NUMBER", controllerMsg.substring(vinNumberIdxByteRange[0], vinNumberIdxByteRange[1]));
                //
                //
                int[] jobIDIdxByteRange = {86, 88};
                controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));
                //
                int[] parameterSetIDIdxByteRange = {90, 93};
                controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
                //
                int[] batchSizeIdxByteRange = {95, 99};
                controllerHash.put("BATCH_SIZE", controllerMsg.substring(batchSizeIdxByteRange[0], batchSizeIdxByteRange[1]));
                int[] batchCounterIdxByteRange = {101, 105};
                controllerHash.put("BATCH_COUNTER", controllerMsg.substring(batchCounterIdxByteRange[0], batchCounterIdxByteRange[1]));
                //
                int[] tighteningStatusIdxByteRange = {107, 108};
                String[] tighteningStatusArray = {"TIGHTENING_NOK", "TIGHTENING_OK"};
                String tighteningStatusValue = controllerMsg.substring(tighteningStatusIdxByteRange[0], tighteningStatusIdxByteRange[1]);
    //            System.out.println("TIGHTENING_STATUS: " + tighteningStatusValue);
                if (tighteningStatusValue.equals("0")) {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[0]);
                } else {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[1]);
                }
                //
                int[] torqueStatusIdxByteRange = {110, 111};
                String[] torqueStatusArray = {"LOW", "OK", "HIGH"};
                String torqueStatusValue = controllerMsg.substring(torqueStatusIdxByteRange[0], torqueStatusIdxByteRange[1]);
    //            System.out.println("TORQUE_STATUS: " + torqueStatusValue);
                if (torqueStatusValue.equals("0")) {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[0]);
                } else if (torqueStatusValue.equals("1")) {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[1]);
                } else {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[2]);
                }
                //
                int[] angleStatusIdxByteRange = {113, 114};
                String[] angleStatusArray = {"LOW", "OK", "HIGH"};
                String angleStatusValue = controllerMsg.substring(angleStatusIdxByteRange[0], angleStatusIdxByteRange[1]);
    //            System.out.println("ANGLE_STATUS: " + angleStatusValue);
                if (angleStatusValue.equals("0")) {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[0]);
                } else if (angleStatusValue.equals("1")) {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[1]);
                } else {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[2]);
                }
                //
                int[] torqueMinLimitIdxByteRange = {116, 122};
                String torqueMinLimitStringAscii = controllerMsg.substring(torqueMinLimitIdxByteRange[0], torqueMinLimitIdxByteRange[1]);
    //            System.out.println("TORQUE_MIN_LIMIT: " + torqueMinLimitStringAscii);
                //String torqueMinLimitString = inverseAsciiDigits(100, 6, torqueMinLimitStringAscii);
                String torqueMinLimitString = new opUtilityMethods(100, 6, torqueMinLimitStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_MIN_LIMIT", torqueMinLimitString);

                int[] torqueMaxLimitIdxByteRange = {124, 130};
                String torqueMaxLimitStringAscii = controllerMsg.substring(torqueMaxLimitIdxByteRange[0], torqueMaxLimitIdxByteRange[1]);
    //            System.out.println("TORQUE_MAX_LIMIT: " + torqueMaxLimitStringAscii);
                //String torqueMaxLimitString = inverseAsciiDigits(100, 6, torqueMaxLimitStringAscii);
                String torqueMaxLimitString = new opUtilityMethods(100, 6, torqueMaxLimitStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_MAX_LIMIT", torqueMaxLimitString);

                int[] torqueFinalTargetIdxByteRange = {132, 138};
                String torqueFinalTargetStringAscii = controllerMsg.substring(torqueFinalTargetIdxByteRange[0], torqueFinalTargetIdxByteRange[1]);
    //            System.out.println("TORQUE_FINAL_TARGET: " + torqueFinalTargetStringAscii);
                //String torqueFinalTargetString = inverseAsciiDigits(100, 6, torqueFinalTargetStringAscii);
                String torqueFinalTargetString = new opUtilityMethods(100, 6, torqueFinalTargetStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_FINAL_TARGET", torqueFinalTargetString);

                int[] torqueIdxByteRange = {140, 146};
                String torqueStringAscii = controllerMsg.substring(torqueIdxByteRange[0], torqueIdxByteRange[1]);
                //String torqueString = inverseAsciiDigits(100, 6, torqueStringAscii);
                String torqueString = new opUtilityMethods(100, 6, torqueStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE", torqueString);
                //
                int[] angleMinLimitIdxByteRange = {148, 153};
                controllerHash.put("ANGLE_MIN", controllerMsg.substring(angleMinLimitIdxByteRange[0], angleMinLimitIdxByteRange[1]));
                int[] angleMaxLimitIdxByteRange = {155, 160};
                controllerHash.put("ANGLE_MAX", controllerMsg.substring(angleMaxLimitIdxByteRange[0], angleMaxLimitIdxByteRange[1]));
                int[] angleFinalTargetIdxByteRange = {162, 167};
                controllerHash.put("ANGLE_FINAL_TARGET", controllerMsg.substring(angleFinalTargetIdxByteRange[0], angleFinalTargetIdxByteRange[1]));
                int[] angleIdxByteRange = {169, 174};
                controllerHash.put("ANGLE", controllerMsg.substring(angleIdxByteRange[0], angleIdxByteRange[1]));
                //
                int[] timeStampIdxByteRange = {176, 195};
                controllerHash.put("TIMESTAMP", controllerMsg.substring(timeStampIdxByteRange[0], timeStampIdxByteRange[1]));
                int[] lastChangeDateIdxByteRange = {197, 216};
                controllerHash.put("DATE_LAST_PARAMETER_SET_CHANGE", controllerMsg.substring(lastChangeDateIdxByteRange[0], lastChangeDateIdxByteRange[1]));
                //
                int[] batchStatusIdxByteRange = {218, 219};
                controllerHash.put("BATCH_STATUS", controllerMsg.substring(batchStatusIdxByteRange[0], batchStatusIdxByteRange[1]));
                //
                int[] tighteningIDIdxByteRange = {222, 231};
                controllerHash.put("TIGHTENING_ID", controllerMsg.substring(tighteningIDIdxByteRange[0], tighteningIDIdxByteRange[1]));
                //
                //
                //} else if (midRevision.equals("002") || midRevision.equals("003") || midRevision.equals("004"))
            } else if (midRevisionInt < 999) {
                //
                int[] cellIdIdxByteRange = {22, 26};
                //
                int[] channelIdIdxByteRange = {28, 30};
                //
                int[] torqueControllerIdIdxByteRange = {32, 57};
                //
                int[] vinNumberIdxByteRange = {59, 84};
                //
                controllerHash.put("CELL_ID", controllerMsg.substring(cellIdIdxByteRange[0], cellIdIdxByteRange[1]));
                controllerHash.put("CHANNEL_ID", controllerMsg.substring(channelIdIdxByteRange[0], channelIdIdxByteRange[1]));
                controllerHash.put("TORQUE_CONTROLLER_NAME", controllerMsg.substring(torqueControllerIdIdxByteRange[0], torqueControllerIdIdxByteRange[1]));
                controllerHash.put("VIN_NUMBER", controllerMsg.substring(vinNumberIdxByteRange[0], vinNumberIdxByteRange[1]));
                //
                //
                int[] jobIDIdxByteRange = {86, 90};
                controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));
                //
                int[] parameterSetIDIdxByteRange = {92, 95};
                controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
                //
                int[] strategyIdxByteRange = {97, 99};
                String strategyValue = controllerMsg.substring(strategyIdxByteRange[0], strategyIdxByteRange[1]);
                switch (strategyValue) {
                    case "01":
                        controllerHash.put("STRATEGY", "TORQUE_CONTROL");
                        break;
                    case "02":
                        controllerHash.put("STRATEGY", "TORQUE_CONTROL_AND_ANGLE_MONITORING");
                        break;
                    case "03":
                        controllerHash.put("STRATEGY", "TORQUE_CONTROL_AND_ANGLE_CONTROL");
                        break;
                    case "04":
                        controllerHash.put("STRATEGY", "ANGLE_CONTROL_AND_TORQUE_MONITORING");
                        break;
                    case "05":
                        controllerHash.put("STRATEGY", "DS_CONTROL");
                        break;
                    case "06":
                        controllerHash.put("STRATEGY", "DS_CONTROL_TORQUE_MONITORING");
                        break;
                    case "07":
                        controllerHash.put("STRATEGY", "REVERSE_ANGLE");
                        break;
                    case "08":
                        controllerHash.put("STRATEGY", "REVERSE_TORQUE");
                        break;
                    case "09":
                        controllerHash.put("STRATEGY", "CLICK_WRENCH");
                        break;
                    case "10":
                        controllerHash.put("STRATEGY", "ROTATE_SPINDLE_FORWARD");
                        break;
                    case "11":
                        controllerHash.put("STRATEGY", "TORQUE_CONTROL_OR_ANGLE_CONTROL");
                        break;
                    case "12":
                        controllerHash.put("STRATEGY", "ROTATE_SPINDLE_REVERSE");
                        break;
                    case "13":
                        controllerHash.put("STRATEGY", "HOME_POSITION");
                        break;
                    case "14":
                        controllerHash.put("STRATEGY", "EP_MONITORING");
                        break;
                    case "15":
                        controllerHash.put("STRATEGY", "RESERVED");
                        break;
                    case "99":
                        controllerHash.put("STRATEGY", "NO_STRATEGY");
                        break;
                }
                int[] strategyOptionsIdxByteRange = {101, 106};
                controllerHash.put("STRATEGY_OPTIONS", controllerMsg.substring(strategyOptionsIdxByteRange[0], strategyOptionsIdxByteRange[1]));
                //
                int[] batchSizeIdxByteRange = {108, 112};
                controllerHash.put("BATCH_SIZE", controllerMsg.substring(batchSizeIdxByteRange[0], batchSizeIdxByteRange[1]));
                int[] batchCounterIdxByteRange = {114, 118};
                controllerHash.put("BATCH_COUNTER", controllerMsg.substring(batchCounterIdxByteRange[0], batchCounterIdxByteRange[1]));
                //
                int[] tighteningStatusIdxByteRange = {120, 121};
                String[] tighteningStatusArray = {"TIGHTENING_NOK", "TIGHTENING_OK"};
                String tighteningStatusValue = controllerMsg.substring(tighteningStatusIdxByteRange[0], tighteningStatusIdxByteRange[1]);
                if (tighteningStatusValue.equals("0")) {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[0]);
                } else {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[1]);
                }
                //
                int[] batchStatusIdxByteRange = {123, 124};
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
                int[] torqueStatusIdxByteRange = {126, 127};
                String[] torqueStatusArray = {"LOW", "OK", "HIGH"};
                String torqueStatusValue = controllerMsg.substring(torqueStatusIdxByteRange[0], torqueStatusIdxByteRange[1]);
                if (torqueStatusValue.equals("0")) {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[0]);
                } else if (torqueStatusValue.equals("1")) {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[1]);
                } else {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[2]);
                }
                //
                int[] angleStatusIdxByteRange = {129, 130};
                String[] angleStatusArray = {"LOW", "OK", "HIGH"};
                String angleStatusValue = controllerMsg.substring(angleStatusIdxByteRange[0], angleStatusIdxByteRange[1]);
                if (angleStatusValue.equals("0")) {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[0]);
                } else if (angleStatusValue.equals("1")) {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[1]);
                } else {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[2]);
                }
                //
                int[] rundownAngleStatusIdxByteRange = {132, 133};
                String[] rundownAngleStatusArray = {"LOW", "OK", "HIGH"};
                String rundownAngleStatusValue = controllerMsg.substring(rundownAngleStatusIdxByteRange[0], rundownAngleStatusIdxByteRange[1]);
                if (rundownAngleStatusValue.equals("0")) {
                    controllerHash.put("RUNDOWN_ANGLE_STATUS", rundownAngleStatusArray[0]);
                } else if (rundownAngleStatusValue.equals("1")) {
                    controllerHash.put("RUNDOWN_ANGLE_STATUS", rundownAngleStatusArray[1]);
                } else {
                    controllerHash.put("RUNDOWN_ANGLE_STATUS", rundownAngleStatusArray[2]);
                }
                //
                int[] currentMonitoringStatusIdxByteRange = {135, 136};
                String[] currentMonitoringStatusArray = {"LOW", "OK", "HIGH"};
                String currentMonitoringStatusValue = controllerMsg.substring(currentMonitoringStatusIdxByteRange[0], currentMonitoringStatusIdxByteRange[1]);
                if (currentMonitoringStatusValue.equals("0")) {
                    controllerHash.put("CURRENT_MONITORING_STATUS", currentMonitoringStatusArray[0]);
                } else if (currentMonitoringStatusValue.equals("1")) {
                    controllerHash.put("CURRENT_MONITORING_STATUS", currentMonitoringStatusArray[1]);
                } else {
                    controllerHash.put("CURRENT_MONITORING_STATUS", currentMonitoringStatusArray[2]);
                }
                //
                int[] selftapStatusIdxByteRange = {138, 139};
                String[] selftapStatusArray = {"LOW", "OK", "HIGH"};
                String selftapStatusValue = controllerMsg.substring(selftapStatusIdxByteRange[0], selftapStatusIdxByteRange[1]);
                if (selftapStatusValue.equals("0")) {
                    controllerHash.put("SELFTAP_STATUS", selftapStatusArray[0]);
                } else if (selftapStatusValue.equals("1")) {
                    controllerHash.put("SELFTAP_STATUS", selftapStatusArray[1]);
                } else {
                    controllerHash.put("SELFTAP_STATUS", selftapStatusArray[2]);
                }
                //
                int[] prevailTorqueMonitoringStatusIdxByteRange = {141, 142};
                String[] prevailTorqueMonitoringStatusArray = {"LOW", "OK", "HIGH"};
                String prevailTorqueMonitoringStatusValue = controllerMsg.substring(prevailTorqueMonitoringStatusIdxByteRange[0], prevailTorqueMonitoringStatusIdxByteRange[1]);
                if (prevailTorqueMonitoringStatusValue.equals("0")) {
                    controllerHash.put("PREVAIL_TORQUE_MONITORING_STATUS", prevailTorqueMonitoringStatusArray[0]);
                } else if (prevailTorqueMonitoringStatusValue.equals("1")) {
                    controllerHash.put("PREVAIL_TORQUE_MONITORING_STATUS", prevailTorqueMonitoringStatusArray[1]);
                } else {
                    controllerHash.put("PREVAIL_TORQUE_MONITORING_STATUS", prevailTorqueMonitoringStatusArray[2]);
                }
                //
                int[] prevailTorqueMonitoringCompensateIdxByteRange = {144, 145};
                String[] prevailTorqueMonitoringCompensateArray = {"LOW", "OK", "HIGH"};
                String prevailTorqueMonitoringCompensateValue = controllerMsg.substring(prevailTorqueMonitoringCompensateIdxByteRange[0], prevailTorqueMonitoringCompensateIdxByteRange[1]);
                if (prevailTorqueMonitoringCompensateValue.equals("0")) {
                    controllerHash.put("PREVAIL_TORQUE_MONITORING_COMPENSATE", prevailTorqueMonitoringCompensateArray[0]);
                } else if (prevailTorqueMonitoringCompensateValue.equals("1")) {
                    controllerHash.put("PREVAIL_TORQUE_MONITORING_COMPENSATE", prevailTorqueMonitoringCompensateArray[1]);
                } else {
                    controllerHash.put("PREVAIL_TORQUE_MONITORING_COMPENSATE", prevailTorqueMonitoringCompensateArray[2]);
                }
                //
                int[] tighteningErrorStatusIdxByteRange = {147, 157};
                controllerHash.put("TIGHTENING_ERROR_STATUS", controllerMsg.substring(tighteningErrorStatusIdxByteRange[0], tighteningErrorStatusIdxByteRange[1]));
                //
                int[] torqueMinLimitIdxByteRange = {159, 165};
                String torqueMinLimitStringAscii = controllerMsg.substring(torqueMinLimitIdxByteRange[0], torqueMinLimitIdxByteRange[1]);
                //String torqueMinLimitString = inverseAsciiDigits(100, 6, torqueMinLimitStringAscii);
                String torqueMinLimitString = new opUtilityMethods(100, 6, torqueMinLimitStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_MIN_LIMIT", torqueMinLimitString);

                int[] torqueMaxLimitIdxByteRange = {167, 173};
                String torqueMaxLimitStringAscii = controllerMsg.substring(torqueMaxLimitIdxByteRange[0], torqueMaxLimitIdxByteRange[1]);
                //String torqueMaxLimitString = inverseAsciiDigits(100, 6, torqueMaxLimitStringAscii);
                String torqueMaxLimitString = new opUtilityMethods(100, 6, torqueMaxLimitStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_MAX_LIMIT", torqueMaxLimitString);

                int[] torqueFinalTargetIdxByteRange = {175, 181};
                String torqueFinalTargetStringAscii = controllerMsg.substring(torqueFinalTargetIdxByteRange[0], torqueFinalTargetIdxByteRange[1]);
                //String torqueFinalTargetString = inverseAsciiDigits(100, 6, torqueFinalTargetStringAscii);
                String torqueFinalTargetString = new opUtilityMethods(100, 6, torqueFinalTargetStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE_FINAL_TARGET", torqueFinalTargetString);

                int[] torqueIdxByteRange = {183, 189};
                String torqueStringAscii = controllerMsg.substring(torqueIdxByteRange[0], torqueIdxByteRange[1]);
                //String torqueString = inverseAsciiDigits(100, 6, torqueStringAscii);
                String torqueString = new opUtilityMethods(100, 6, torqueStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE", torqueString);
                //
                int[] angleMinLimitIdxByteRange = {191, 196};
                controllerHash.put("ANGLE_MIN", controllerMsg.substring(angleMinLimitIdxByteRange[0], angleMinLimitIdxByteRange[1]));
                int[] angleMaxLimitIdxByteRange = {198, 203};
                controllerHash.put("ANGLE_MAX", controllerMsg.substring(angleMaxLimitIdxByteRange[0], angleMaxLimitIdxByteRange[1]));
                int[] angleFinalTargetIdxByteRange = {205, 210};
                controllerHash.put("ANGLE_FINAL_TARGET", controllerMsg.substring(angleFinalTargetIdxByteRange[0], angleFinalTargetIdxByteRange[1]));
                int[] angleIdxByteRange = {212, 217};
                controllerHash.put("ANGLE", controllerMsg.substring(angleIdxByteRange[0], angleIdxByteRange[1]));
                int[] rundownAngleMinIdxByteRange = {219, 224};
                controllerHash.put("RUNDOWN_ANGLE_MIN", controllerMsg.substring(rundownAngleMinIdxByteRange[0], rundownAngleMinIdxByteRange[1]));
                int[] rundownAngleMaxIdxByteRange = {226, 231};
                controllerHash.put("RUNDOWN_ANGLE_MAX", controllerMsg.substring(rundownAngleMaxIdxByteRange[0], rundownAngleMaxIdxByteRange[1]));
                int[] rundownAngleIdxByteRange = {233, 238};
                controllerHash.put("RUNDOWN_ANGLE", controllerMsg.substring(rundownAngleIdxByteRange[0], rundownAngleIdxByteRange[1]));
                //
                int[] currentMonitoringMinIdxByteRange = {240, 243};
                controllerHash.put("CURRENT_MONITORING_MIN", controllerMsg.substring(currentMonitoringMinIdxByteRange[0], currentMonitoringMinIdxByteRange[1]));
                int[] currentMonitoringMaxIdxByteRange = {245, 248};
                controllerHash.put("CURRENT_MONITORING_MAX", controllerMsg.substring(currentMonitoringMaxIdxByteRange[0], currentMonitoringMaxIdxByteRange[1]));
                int[] currentMonitoringValueIdxByteRange = {250, 253};
                controllerHash.put("CURRENT_MONITORING_VALUE", controllerMsg.substring(currentMonitoringValueIdxByteRange[0], currentMonitoringValueIdxByteRange[1]));
                //
                int[] selftapMinIdxByteRange = {255, 261};
                String selftapMinValueStringAscii = controllerMsg.substring(selftapMinIdxByteRange[0], selftapMinIdxByteRange[1]);
                //String selftapMinValueString = inverseAsciiDigits(100, 6, selftapMinValueStringAscii);
                String selftapMinValueString = new opUtilityMethods(100, 6, selftapMinValueStringAscii).inverseAsciiDigits();
                controllerHash.put("SELFTAP_MIN", selftapMinValueString);

                int[] selftapMaxIdxByteRange = {263, 269};
                String selftapMaxValueStringAscii = controllerMsg.substring(selftapMaxIdxByteRange[0], selftapMaxIdxByteRange[1]);
                //String selftapMaxValueString = inverseAsciiDigits(100, 6, selftapMaxValueStringAscii);
                String selftapMaxValueString = new opUtilityMethods(100, 6, selftapMaxValueStringAscii).inverseAsciiDigits();
                controllerHash.put("SELFTAP_MAX", selftapMaxValueString);

                int[] selftapTorqueIdxByteRange = {271, 277};
                String selftapTorqueValueStringAscii = controllerMsg.substring(selftapTorqueIdxByteRange[0], selftapTorqueIdxByteRange[1]);
                //String selftapTorqueValueString = inverseAsciiDigits(100, 6, selftapTorqueValueStringAscii);
                String selftapTorqueValueString = new opUtilityMethods(100, 6, selftapTorqueValueStringAscii).inverseAsciiDigits();
                controllerHash.put("SELFTAP_TORQUE", selftapTorqueValueString);
                //
                int[] prevailTorqueMonitoringMinIdxByteRange = {279, 285};
                String prevailTorqueMonitoringMinValueStringAscii = controllerMsg.substring(prevailTorqueMonitoringMinIdxByteRange[0], prevailTorqueMonitoringMinIdxByteRange[1]);
                //String prevailTorqueMonitoringMinValueString = inverseAsciiDigits(100, 6, prevailTorqueMonitoringMinValueStringAscii);
                String prevailTorqueMonitoringMinValueString = new opUtilityMethods(100, 6, prevailTorqueMonitoringMinValueStringAscii).inverseAsciiDigits();
                controllerHash.put("PREVAIL_TORQUE_MONITORING_MIN", prevailTorqueMonitoringMinValueString);

                int[] prevailTorqueMonitoringMaxIdxByteRange = {287, 293};
                String prevailTorqueMonitoringMaxValueStringAscii = controllerMsg.substring(prevailTorqueMonitoringMaxIdxByteRange[0], prevailTorqueMonitoringMaxIdxByteRange[1]);
                //String prevailTorqueMonitoringMaxValueString = inverseAsciiDigits(100, 6, prevailTorqueMonitoringMaxValueStringAscii);
                String prevailTorqueMonitoringMaxValueString = new opUtilityMethods(100, 6, prevailTorqueMonitoringMaxValueStringAscii).inverseAsciiDigits();
                controllerHash.put("PREVAIL_TORQUE_MONITORING_MAX", prevailTorqueMonitoringMaxValueString);

                int[] prevailTorqueIdxByteRange = {295, 301};
                String prevailTorqueValueStringAscii = controllerMsg.substring(prevailTorqueIdxByteRange[0], prevailTorqueIdxByteRange[1]);
                //String prevailTorqueValueString = inverseAsciiDigits(100, 6, prevailTorqueValueStringAscii);
                String prevailTorqueValueString = new opUtilityMethods(100, 6, prevailTorqueValueStringAscii).inverseAsciiDigits();
                controllerHash.put("PREVAIL_TORQUE", prevailTorqueValueString);
                ////
                //
                int[] tighteningIDIdxByteRange = {303, 313};
                controllerHash.put("TIGHTENING_ID", controllerMsg.substring(tighteningIDIdxByteRange[0], tighteningIDIdxByteRange[1]));
                //
                int[] jobSequenceNumberIdxByteRange = {315, 320};
                controllerHash.put("JOB_SEQUENCE_NUMBER", controllerMsg.substring(jobSequenceNumberIdxByteRange[0], jobSequenceNumberIdxByteRange[1]));
                //
                int[] syncTighteningIDIdxByteRange = {322, 327};
                controllerHash.put("SYNC_TIGHTENING_ID", controllerMsg.substring(syncTighteningIDIdxByteRange[0], syncTighteningIDIdxByteRange[1]));
                //
                int[] toolSerialNumberIdxByteRange = {329, 343};
                controllerHash.put("TOOL_SERIAL_NUMBER", controllerMsg.substring(toolSerialNumberIdxByteRange[0], toolSerialNumberIdxByteRange[1]));
                //
                int[] timeStampIdxByteRange = {345, 364};
                controllerHash.put("TIMESTAMP", controllerMsg.substring(timeStampIdxByteRange[0], timeStampIdxByteRange[1]));
                int[] lastChangeDateIdxByteRange = {366, 385};
                controllerHash.put("DATE_LAST_PARAMETER_SET_CHANGE", controllerMsg.substring(lastChangeDateIdxByteRange[0], lastChangeDateIdxByteRange[1]));
                //
                if (midRevisionInt > 2) {
                    // if (!(midRevision.equals("002"))) {
                    //
                    int[] parameterSetNameIdxByteRange = {387, 412};
                    controllerHash.put("PARAMETER_SET_NAME", controllerMsg.substring(parameterSetNameIdxByteRange[0], parameterSetNameIdxByteRange[1]));
                    //
                    int[] torqueValuesUnitIdxByteRange = {414, 415};
                    String torqueValuesUnit = controllerMsg.substring(torqueValuesUnitIdxByteRange[0], torqueValuesUnitIdxByteRange[1]);
                    switch (torqueValuesUnit) {
                        case "1":
                            controllerHash.put("TORQUE_VALUES_UNIT", "Nm");
                            break;
                        case "2":
                            controllerHash.put("TORQUE_VALUES_UNIT", "Lbf.ft");
                            break;
                        case "3":
                            controllerHash.put("TORQUE_VALUES_UNIT", "Lbf.In");
                            break;
                        case "4":
                            controllerHash.put("TORQUE_VALUES_UNIT", "Kpm");
                            break;
                        case "5":
                            controllerHash.put("TORQUE_VALUES_UNIT", "Kgf.cm");
                            break;
                        case "6":
                            controllerHash.put("TORQUE_VALUES_UNIT", "ozf.in");
                            break;
                        default:
                            controllerHash.put("TORQUE_VALUES_UNIT", "%");
                            break;
                    }
                    //
                    int[] resultTypeIdxByteRange = {417, 419};
                    String resultType = controllerMsg.substring(resultTypeIdxByteRange[0], resultTypeIdxByteRange[1]);
                    switch (resultType) {
                        case "01":
                            controllerHash.put("RESULT_TYPE", "Tightening");
                            break;
                        case "02":
                            controllerHash.put("RESULT_TYPE", "Loosening");
                            break;
                        case "03":
                            controllerHash.put("RESULT_TYPE", "Batch Increment ");
                            break;
                        case "04":
                            controllerHash.put("RESULT_TYPE", "Batch decrement");
                            break;
                        case "05":
                            controllerHash.put("RESULT_TYPE", "Bypass parameter set result");
                            break;
                        case "06":
                            controllerHash.put("RESULT_TYPE", "Abort Job result");
                            break;
                        case "07":
                            controllerHash.put("RESULT_TYPE", "Sync tightening");
                            break;
                        default:
                            controllerHash.put("RESULT_TYPE", "Reference setup");
                            break;
                    }
                }
                //
                if (midRevisionInt > 3) {
                    //
                    int[] identifierResult2IdxByteRange = {421, 446};
                    int[] identifierResult3IdxByteRange = {448, 473};
                    int[] identifierResult4IdxByteRange = {475, 500};
                    controllerHash.put("IDENTIFIER_RESULT_PART_2", controllerMsg.substring(identifierResult2IdxByteRange[0], identifierResult2IdxByteRange[1]));
                    controllerHash.put("IDENTIFIER_RESULT_PART_3", controllerMsg.substring(identifierResult3IdxByteRange[0], identifierResult3IdxByteRange[1]));
                    controllerHash.put("IDENTIFIER_RESULT_PART_4", controllerMsg.substring(identifierResult4IdxByteRange[0], identifierResult4IdxByteRange[1]));
                    //
                }
                //
                if (midRevisionInt > 4) {
                    int[] customerTighteningErrorCodeIdxByteRange = {502, 506};
                    controllerHash.put("CUSTOMER_TIGHTENING_ERROR_CODE", controllerMsg.substring(customerTighteningErrorCodeIdxByteRange[0], customerTighteningErrorCodeIdxByteRange[1]));
                }
                //
                if (midRevisionInt > 5) {
                    int[] prevailTorqueCompensateValueIdxByteRange = {508, 514};
                    String prevailTorqueCompensateValueStringAscii = controllerMsg.substring(prevailTorqueCompensateValueIdxByteRange[0], prevailTorqueCompensateValueIdxByteRange[1]);
                    //String prevailTorqueCompensateValueString = inverseAsciiDigits(100, 6, prevailTorqueCompensateValueStringAscii);
                    String prevailTorqueCompensateValueString = new opUtilityMethods(100, 6, prevailTorqueCompensateValueStringAscii).inverseAsciiDigits();
                    controllerHash.put("PREVAIL_TORQUE_COMPENSATE_VALUE", prevailTorqueCompensateValueString);
                    //
                    int[] tighteningErrorStatus2IdxByteRange = {516, 526};
                    controllerHash.put("TIGHTENING_ERROR_STATUS2", controllerMsg.substring(tighteningErrorStatus2IdxByteRange[0], tighteningErrorStatus2IdxByteRange[1]));
                }
                //
                if (midRevision.equals("998")) {
                    int[] numberOfStagesInMultistageIdxByteRange = {528, 530};
                    controllerHash.put("NUMBER_OF_STAGES_IN_MULTISTAGE", controllerMsg.substring(numberOfStagesInMultistageIdxByteRange[0], numberOfStagesInMultistageIdxByteRange[1]));
                    //
                    int[] numberOfStageResultsIdxByteRange = {532, 534};
                    //controllerHash.put("NUMBER_OF_STAGE_RESULTS", controllerMsg.substring(numberOfStageResultsIdxByteRange[0], numberOfStageResultsIdxByteRange[1]));
                    String numberOfStageResultsString = controllerMsg.substring(numberOfStageResultsIdxByteRange[0], numberOfStageResultsIdxByteRange[1]);
                    int numberOfStageResultsInteger = Integer.parseInt(numberOfStageResultsString);
    //                controllerHash.put("NUMBER_OF_STAGE_RESULTS", numberOfStageResultsString);
                    controllerHash.put("NUMBER_OF_STAGE_RESULTS", numberOfStageResultsInteger);
                    //
                    for (int s=0; s < numberOfStageResultsInteger; s++) {
    //                    String stageResultTorque = "stageResultTorqueValue" + (s + 1);
                        String stageResultTorque = "STAGE_RESULT_TORQUE_VALUE" + (s + 1);
                        String stageResultTorqueAscii = controllerMsg.substring(536 + s*11, 536 + s*11 + 6);
                        //String stageResultTorqueValue = inverseAsciiDigits(100, 6, stageResultTorqueAscii);
                        String stageResultTorqueValue = new opUtilityMethods(100, 6, stageResultTorqueAscii).inverseAsciiDigits();
                        controllerHash.put(stageResultTorque, stageResultTorqueValue);
                        //
    //                    String stageResultTurningAngle = "stageResultTurningAngle" + (s + 1);
                        String stageResultTurningAngle = "STAGE_RESULT_TURNING_ANGLE" + (s + 1);
                        String stageResultTurningAngleValue = controllerMsg.substring(536 + s*11 + 6, 536 + s*11 + 11);
                        controllerHash.put(stageResultTurningAngle, stageResultTurningAngleValue);
                        //
                    }
                } else {
                    if (midRevisionInt > 6) {
                        int[] compensatedAngleIdxByteRange = {528, 535};
                        String compensatedAngleStringAscii = controllerMsg.substring(compensatedAngleIdxByteRange[0], compensatedAngleIdxByteRange[1]);
                        //String compensatedAngleString = inverseAsciiDigits(100, 7, compensatedAngleStringAscii);
                        String compensatedAngleString = new opUtilityMethods(100, 7, compensatedAngleStringAscii).inverseAsciiDigits();
                        controllerHash.put("COMPENSATED_ANGLE", compensatedAngleString);
                        //
                        int[] finalAngleDecimalIdxByteRange = {537, 544};
                        String finalAngleDecimalStringAscii = controllerMsg.substring(finalAngleDecimalIdxByteRange[0], finalAngleDecimalIdxByteRange[1]);
                        //String finalAngleDecimalString = inverseAsciiDigits(100, 7, finalAngleDecimalStringAscii);
                        String finalAngleDecimalString = new opUtilityMethods(100, 7, finalAngleDecimalStringAscii).inverseAsciiDigits();
                        controllerHash.put("FINAL_ANGLE_DECIMAL", finalAngleDecimalString);
                    }
                    if (midRevisionInt > 7) {
                        int[] startFinalAngleIdxByteRange = {546, 552};
                        controllerHash.put("START_FINAL_ANGLE", controllerMsg.substring(startFinalAngleIdxByteRange[0], startFinalAngleIdxByteRange[1]));
                        //
                        int[] postViewTorqueActivatedIdxByteRange = {554, 555};
                        controllerHash.put("POST_VIEW_TORQUE_ACTIVATED", controllerMsg.substring(postViewTorqueActivatedIdxByteRange[0], postViewTorqueActivatedIdxByteRange[1]));
                        //
                        int[] postViewTorqueHighIdxByteRange = {557, 563};
                        String postViewTorqueHighStringAscii = controllerMsg.substring(postViewTorqueHighIdxByteRange[0], postViewTorqueHighIdxByteRange[1]);
                        //String postViewTorqueHighString = inverseAsciiDigits(100, 6, postViewTorqueHighStringAscii);
                        String postViewTorqueHighString = new opUtilityMethods(100, 6, postViewTorqueHighStringAscii).inverseAsciiDigits();
                        controllerHash.put("POST_VIEW_TORQUE_HIGH", postViewTorqueHighString);
                        //
                        int[] postViewTorqueLowIdxByteRange = {565, 571};
                        String postViewTorqueLowStringAscii = controllerMsg.substring(postViewTorqueLowIdxByteRange[0], postViewTorqueLowIdxByteRange[1]);
                        //String postViewTorqueLowString = inverseAsciiDigits(100, 6, postViewTorqueLowStringAscii);
                        String postViewTorqueLowString = new opUtilityMethods(100, 6, postViewTorqueLowStringAscii).inverseAsciiDigits();
                        controllerHash.put("POST_VIEW_TORQUE_LOW", postViewTorqueLowString);
                    }
                    if (midRevisionInt > 8) {
                        int[] currentMonitoringAmpIdxByteRange = {573, 578};
                        controllerHash.put("CURRENT_MONITORING_AMP", controllerMsg.substring(currentMonitoringAmpIdxByteRange[0], currentMonitoringAmpIdxByteRange[1]));
                        //
                        int[] currentMonitoringAmpMinIdxByteRange = {580, 585};
                        controllerHash.put("CURRENT_MONITORING_AMP_MIN", controllerMsg.substring(currentMonitoringAmpMinIdxByteRange[0], currentMonitoringAmpMinIdxByteRange[1]));
                        //
                        int[] currentMonitoringAmpMaxIdxByteRange = {587, 592};
                        controllerHash.put("CURRENT_MONITORING_AMP_MAX", controllerMsg.substring(currentMonitoringAmpMaxIdxByteRange[0], currentMonitoringAmpMaxIdxByteRange[1]));
                    }
                    if (midRevisionInt > 9) {
                        int[] angleNumeratorScaleFactorIdxByteRange = {594, 599};
                        String angleNumeratorScaleFactorStringAscii = controllerMsg.substring(angleNumeratorScaleFactorIdxByteRange[0], angleNumeratorScaleFactorIdxByteRange[1]);
                        //String angleNumeratorScaleFactorString = inverseAsciiDigits(100, 5, angleNumeratorScaleFactorStringAscii);
                        String angleNumeratorScaleFactorString = new opUtilityMethods(100, 5, angleNumeratorScaleFactorStringAscii).inverseAsciiDigits();
                        controllerHash.put("ANGLE_NUMERATOR_SCALE_FACTOR", angleNumeratorScaleFactorString);
                        //
                        int[] angleDenominatorScaleFactorIdxByteRange = {601, 606};
                        String angleDenominatorScaleFactorStringAscii = controllerMsg.substring(angleDenominatorScaleFactorIdxByteRange[0], angleDenominatorScaleFactorIdxByteRange[1]);
                        //String angleDenominatorScaleFactorString = inverseAsciiDigits(1, 5, angleDenominatorScaleFactorStringAscii);
                        String angleDenominatorScaleFactorString = new opUtilityMethods(1, 5, angleDenominatorScaleFactorStringAscii).inverseAsciiDigits();
                        controllerHash.put("ANGLE_DENOMINATOR_SCALE_FACTOR", angleDenominatorScaleFactorString);
                        //
                        int[] overallAngleStatusIdxByteRange = {608, 609};
                        String overallAngleStatus = controllerMsg.substring(overallAngleStatusIdxByteRange[0], overallAngleStatusIdxByteRange[1]);
                        switch (overallAngleStatus) {
                            case "0":
                                controllerHash.put("OVERALL_ANGLE_STATUS", "Low");
                                break;
                            case "1":
                                controllerHash.put("OVERALL_ANGLE_STATUS", "OK");
                                break;
                            case "2":
                                controllerHash.put("OVERALL_ANGLE_STATUS", "High");
                                break;
                        }
                        int[] overallAngleMinIdxByteRange = {611, 616};
                        controllerHash.put("OVERALL_ANGLE_MIN", controllerMsg.substring(overallAngleMinIdxByteRange[0], overallAngleMinIdxByteRange[1]));
                        int[] overallAngleMaxIdxByteRange = {618, 623};
                        controllerHash.put("OVERALL_ANGLE_MAX", controllerMsg.substring(overallAngleMaxIdxByteRange[0], overallAngleMaxIdxByteRange[1]));
                        int[] overallAngleIdxByteRange = {625, 630};
                        controllerHash.put("OVERALL_ANGLE", controllerMsg.substring(overallAngleIdxByteRange[0], overallAngleIdxByteRange[1]));
                        //
                        int[] peakTorqueIdxByteRange = {632, 638};
                        String peakTorqueStringAscii = controllerMsg.substring(peakTorqueIdxByteRange[0], peakTorqueIdxByteRange[1]);
                        //String peakTorqueString = inverseAsciiDigits(100, 6, peakTorqueStringAscii);
                        String peakTorqueString = new opUtilityMethods(100, 6, peakTorqueStringAscii).inverseAsciiDigits();
                        controllerHash.put("PEAK_TORQUE", peakTorqueString);
                        //
                        int[] residualBreakawayTorqueIdxByteRange = {640, 646};
                        String residualBreakawayTorqueStringAscii = controllerMsg.substring(residualBreakawayTorqueIdxByteRange[0], residualBreakawayTorqueIdxByteRange[1]);
                        //String residualBreakawayTorqueString = inverseAsciiDigits(100, 6, residualBreakawayTorqueStringAscii);
                        String residualBreakawayTorqueString = new opUtilityMethods(100, 6, residualBreakawayTorqueStringAscii).inverseAsciiDigits();
                        controllerHash.put("RESIDUAL_BREAKAWAY_TORQUE", residualBreakawayTorqueString);
                        //
                        int[] startRundownAngleIdxByteRange = {648, 654};
                        String startRundownAngleStringAscii = controllerMsg.substring(startRundownAngleIdxByteRange[0], startRundownAngleIdxByteRange[1]);
                        //String startRundownAngleString = inverseAsciiDigits(100, 6, startRundownAngleStringAscii);
                        String startRundownAngleString = new opUtilityMethods(100, 6, startRundownAngleStringAscii).inverseAsciiDigits();
                        controllerHash.put("START_RUNDOWN_ANGLE", startRundownAngleString);
                        //
                        int[] rundownAngleCompleteIdxByteRange = {656, 662};
                        String rundownAngleCompleteStringAscii = controllerMsg.substring(rundownAngleCompleteIdxByteRange[0], rundownAngleCompleteIdxByteRange[1]);
                        //String rundownAngleCompleteString = inverseAsciiDigits(100, 6, rundownAngleCompleteStringAscii);
                        String rundownAngleCompleteString = new opUtilityMethods(100, 6, rundownAngleCompleteStringAscii).inverseAsciiDigits();
                        controllerHash.put("RUNDOWN_ANGLE_COMPLETE", rundownAngleCompleteString);
                    }
                    //
                    if (midRevisionInt > 10) {
                        int[] clickTorqueIdxByteRange = {664, 670};
                        String clickTorqueStringAscii = controllerMsg.substring(clickTorqueIdxByteRange[0], clickTorqueIdxByteRange[1]);
                        //String clickTorqueString = inverseAsciiDigits(100, 6, clickTorqueStringAscii);
                        String clickTorqueString = new opUtilityMethods(100, 6, clickTorqueStringAscii).inverseAsciiDigits();
                        controllerHash.put("CLICK_TORQUE", clickTorqueString);
                        //
                        int[] angleDenominatorScaleFactorIdxByteRange = {672, 677};
                        controllerHash.put("CLICK_ANGLE", controllerMsg.substring(angleDenominatorScaleFactorIdxByteRange[0], angleDenominatorScaleFactorIdxByteRange[1]));
                    }
                }
            } else if (midRevision.equals("999")) {
                int[] vinNumberIdxByteRange = new int[]{20, 45};
                controllerHash.put("VIN_NUMBER", controllerMsg.substring(vinNumberIdxByteRange[0], vinNumberIdxByteRange[1]));
                //
                int[] jobIDIdxByteRange = {45, 47};
                controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));
                //
                int[] parameterSetIDIdxByteRange = {47, 50};
                controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
                //
                int[] batchSizeIdxByteRange = {50, 54};
                controllerHash.put("BATCH_SIZE", controllerMsg.substring(batchSizeIdxByteRange[0], batchSizeIdxByteRange[1]));
                //
                int[] batchCounterIdxByteRange = {54, 58};
                controllerHash.put("BATCH_COUNTER", controllerMsg.substring(batchCounterIdxByteRange[0], batchCounterIdxByteRange[1]));
                //
                int[] batchStatusIdxByteRange = {58, 59};
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
                int[] tighteningStatusIdxByteRange = {59, 60};
                String[] tighteningStatusArray = {"TIGHTENING_NOK", "TIGHTENING_OK"};
                String tighteningStatusValue = controllerMsg.substring(tighteningStatusIdxByteRange[0], tighteningStatusIdxByteRange[1]);
                if (tighteningStatusValue.equals("0")) {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[0]);
                } else {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[1]);
                }
                //
                int[] torqueStatusIdxByteRange = {60, 61};
                String[] torqueStatusArray = {"LOW", "OK", "HIGH"};
                String torqueStatusValue = controllerMsg.substring(torqueStatusIdxByteRange[0], torqueStatusIdxByteRange[1]);
                if (torqueStatusValue.equals("0")) {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[0]);
                } else if (torqueStatusValue.equals("1")) {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[1]);
                } else {
                    controllerHash.put("TORQUE_STATUS", torqueStatusArray[2]);
                }
                //
                int[] angleStatusIdxByteRange = {61, 62};
                String[] angleStatusArray = {"LOW", "OK", "HIGH"};
                String angleStatusValue = controllerMsg.substring(angleStatusIdxByteRange[0], angleStatusIdxByteRange[1]);
                if (angleStatusValue.equals("0")) {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[0]);
                } else if (angleStatusValue.equals("1")) {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[1]);
                } else {
                    controllerHash.put("ANGLE_STATUS", angleStatusArray[2]);
                }
                //
                int[] torqueIdxByteRange = {62, 68};
                String torqueStringAscii = controllerMsg.substring(torqueIdxByteRange[0], torqueIdxByteRange[1]);
                //String torqueString = inverseAsciiDigits(100, 6, torqueStringAscii);
                String torqueString = new opUtilityMethods(100, 6, torqueStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE", torqueString);
                //
                int[] angleIdxByteRange = {68, 73};
                controllerHash.put("ANGLE", controllerMsg.substring(angleIdxByteRange[0], angleIdxByteRange[1]));
                //
                int[] timeStampIdxByteRange = {73, 92};
                controllerHash.put("TIMESTAMP", controllerMsg.substring(timeStampIdxByteRange[0], timeStampIdxByteRange[1]));
                int[] lastChangeDateIdxByteRange = {92, 111};
                controllerHash.put("DATE_LAST_PARAMETER_SET_CHANGE", controllerMsg.substring(lastChangeDateIdxByteRange[0], lastChangeDateIdxByteRange[1]));
                //
                int[] tighteningIDIdxByteRange = {111, 121};
                controllerHash.put("TIGHTENING_ID", controllerMsg.substring(tighteningIDIdxByteRange[0], tighteningIDIdxByteRange[1]));
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
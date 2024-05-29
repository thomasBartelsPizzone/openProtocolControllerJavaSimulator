package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0065 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0065";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0065";
        } else if (answer.equalsIgnoreCase("error")) {
            return "midCommands.MID0004";
        } else {
            return "midCommands.MID0004";
        }
    }
    //
    // MID 0065 Old tightening result upload reply
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        //String midLengthString = "0030";
        String midLengthString = null;
        String midRevision = null;
        // String midRevision = "001";
        // String midRevision = "002";
        // String midRevision = "003";
        // String midAckFlag = "0";
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
            // Check Size
            int inputCount = dataFieldValue.size();
            // revision
            if (inputCount == 11) {
                midRevision = "001";
                midLengthString = "0118";
            } else if (inputCount == 28) {
                midRevision = "002";
                midLengthString = "0226";
            } else if (inputCount == 30) {
                midRevision = "003";
                midLengthString = "0233";
            } else if (inputCount == 33) {
                midRevision = "004";
                midLengthString = "0314";
            } else if (inputCount == 34) {
                midRevision = "005";
                midLengthString = "0320";
            } else if (inputCount == 36) {
                midRevision = "006";
                midLengthString = "0340";
            } else if (inputCount == 38) {
                midRevision = "007";
                midLengthString = "0379";
            } else if (inputCount == 42) {
                midRevision = "008";
                midLengthString = "0406";
            } else if (inputCount == 45) {
                midRevision = "009";
                midLengthString = "0427";
            } else if (inputCount == 55) {
                midRevision = "010";
                midLengthString = "0497";
            } else if (inputCount == 57) {
                midRevision = "011";
                midLengthString = "0512";
            } else {
                midRevision = "002";
                midLengthString = "0226";
            }
            //
            if (midRevision.equals("001")) {
                int dFCount = 1;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 1) {
                            //tempMidAscii.append(String.format("%10d", s));
                            tempMidAscii.append(String.format("%10d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 2) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 3) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 4) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 4 && dFCount < 8) {
                            tempMidAscii.append(s);
                        } else if (dFCount == 8) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else {
                            tempMidAscii.append(s);
                        }
                    } else {
                        if (dFCount == 8) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else {
                            tempMidAscii.append(s);
                        }
                    }
                    dFCount += 1;
                }
            } else {
                //} else if (midRevision.equals("002")) {
                int dFCount = 1;
                for (Object s : dataFieldValue) {
                    tempMidAscii.append(String.format("%02d", dFCount));
                    if (opM.isNumeric(s)) {
                        //if (s instanceof Integer) {
                        if (dFCount == 1) {
                            //tempMidAscii.append(String.format("%10d", s));
                            tempMidAscii.append(String.format("%10d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 2) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 3) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 4) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 5) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 6) {
                            tempMidAscii.append(s);
                        } else if (dFCount > 6 && dFCount < 9) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount >= 9 && dFCount < 18) {
                            tempMidAscii.append(s);
                        } else if (dFCount == 18) {
                            //tempMidAscii.append(String.format("%10d", s));
                            tempMidAscii.append(String.format("%10d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 19) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 19 && dFCount < 22) {
                            tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 22) {
                            //tempMidAscii.append(String.format("%03d", s));
                            tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 22 && dFCount < 25) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 25 && dFCount < 27) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 27) {
                            //tempMidAscii.append(String.format("%14d", s));
                            tempMidAscii.append(String.format("%14d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 28) {
                            tempMidAscii.append(s);
                        } else if (dFCount == 29) {
                            tempMidAscii.append(s);
                        } else if (dFCount == 30) {
                            //tempMidAscii.append(String.format("%02d", s));
                            tempMidAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                        } else if (dFCount > 30 && dFCount < 34) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 34) {
                            //tempMidAscii.append(String.format("%04d", s));
                            tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 35) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 35 && dFCount < 38) {
                            //tempMidAscii.append(String.format("%10d", s));
                            tempMidAscii.append(String.format("%10d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 38) {
                            //tempMidAscii.append(String.format("%25d", s));
                            tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                        } else if (dFCount == 39) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount == 40) {
                            tempMidAscii.append(s);
                        } else if (dFCount > 40 && dFCount < 43) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 43 && dFCount < 46) {
                            //tempMidAscii.append(asciiDigits(100, "5", s));
                            tempMidAscii.append(new opUtilityMethods(100, 5, s).asciiDigits());

                            //46 & 47 represent the numerator and denominator in scaling factor
                        } else if (dFCount == 46) {
                            //tempMidAscii.append(asciiDigits(100, "5", s));
                            tempMidAscii.append(new opUtilityMethods(100, 5, s).asciiDigits());
                        } else if (dFCount == 47) {
                            //tempMidAscii.append(asciiDigits(1, "5", s));
                            tempMidAscii.append(new opUtilityMethods(100, 5, s).asciiDigits());
                        } else if (dFCount == 48) {
                            tempMidAscii.append(s);
                        } else if (dFCount > 48 && dFCount < 52) {
                            //tempMidAscii.append(String.format("%05d", s));
                            tempMidAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                        } else if (dFCount >= 52 && dFCount < 57) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else {
                            tempMidAscii.append(s);
                        }
                    } else {
                        if (dFCount == 19) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 22 && dFCount < 25) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount == 35) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount == 39) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount > 40 && dFCount < 43) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                        } else if (dFCount >= 43 && dFCount < 46) {
                            //tempMidAscii.append(asciiDigits(100, "5", s));
                            tempMidAscii.append(new opUtilityMethods(100, 5, s).asciiDigits());
                        } else if (dFCount >= 52 && dFCount < 57) {
                            //tempMidAscii.append(asciiDigits(100, "6", s));
                            tempMidAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
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
            // collect revision for parser settings
            int midRevisionInt = Integer.parseInt(midRevision);
            //
            int[] tighteningIDIdxByteRange = {22, 32};
            //
            int[] vinNumberIdxByteRange = {34, 59};
            //
            controllerHash.put("TIGHTENING_ID", controllerMsg.substring(tighteningIDIdxByteRange[0], tighteningIDIdxByteRange[1]));
            controllerHash.put("VIN_NUMBER", controllerMsg.substring(vinNumberIdxByteRange[0], vinNumberIdxByteRange[1]));
            //
            if (midRevision.equals("001")) {
                int[] parameterSetIDIdxByteRange = {61, 64};
                controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
                //
                int[] batchCounterIdxByteRange = {66, 70};
                controllerHash.put("BATCH_COUNTER", controllerMsg.substring(batchCounterIdxByteRange[0], batchCounterIdxByteRange[1]));
                //
                int[] tighteningStatusIdxByteRange = {72, 73};
                String[] tighteningStatusArray = {"TIGHTENING_NOK", "TIGHTENING_OK"};
                String tighteningStatusValue = controllerMsg.substring(tighteningStatusIdxByteRange[0], tighteningStatusIdxByteRange[1]);
                if (tighteningStatusValue.equals("0")) {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[0]);
                } else {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[1]);
                }
                //
                int[] torqueStatusIdxByteRange = {75, 76};
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
                int[] angleStatusIdxByteRange = {78, 79};
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
                int[] torqueIdxByteRange = {81, 87};
                String torqueStringAscii = controllerMsg.substring(torqueIdxByteRange[0], torqueIdxByteRange[1]);
                //String torqueString = inverseAsciiDigits(100, 6, torqueStringAscii);
                String torqueString = new opUtilityMethods(100, 6, torqueStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE", torqueString);
                //
                int[] angleIdxByteRange = {89, 94};
                controllerHash.put("ANGLE", controllerMsg.substring(angleIdxByteRange[0], angleIdxByteRange[1]));
                //
                int[] timeStampIdxByteRange = {96, 115};
                controllerHash.put("TIMESTAMP", controllerMsg.substring(timeStampIdxByteRange[0], timeStampIdxByteRange[1]));
                //
                int[] batchStatusIdxByteRange = {117, 118};
                controllerHash.put("BATCH_STATUS", controllerMsg.substring(batchStatusIdxByteRange[0], batchStatusIdxByteRange[1]));
                //
            } else {
                //
                int[] jobIDIdxByteRange = {61, 65};
                controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));
                //
                int[] parameterSetIDIdxByteRange = {67, 70};
                controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));
                //
                int[] strategyIdxByteRange = {72, 74};
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
                int[] strategyOptionsIdxByteRange = {76, 81};
                controllerHash.put("STRATEGY_OPTIONS", controllerMsg.substring(strategyOptionsIdxByteRange[0], strategyOptionsIdxByteRange[1]));
                //
                int[] batchSizeIdxByteRange = {83, 87};
                controllerHash.put("BATCH_SIZE", controllerMsg.substring(batchSizeIdxByteRange[0], batchSizeIdxByteRange[1]));
                int[] batchCounterIdxByteRange = {89, 93};
                controllerHash.put("BATCH_COUNTER", controllerMsg.substring(batchCounterIdxByteRange[0], batchCounterIdxByteRange[1]));
                //
                int[] tighteningStatusIdxByteRange = {95, 96};
                String[] tighteningStatusArray = {"TIGHTENING_NOK", "TIGHTENING_OK"};
                String tighteningStatusValue = controllerMsg.substring(tighteningStatusIdxByteRange[0], tighteningStatusIdxByteRange[1]);
                if (tighteningStatusValue.equals("0")) {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[0]);
                } else {
                    controllerHash.put("TIGHTENING_STATUS", tighteningStatusArray[1]);
                }
                //
                int[] batchStatusIdxByteRange = {98, 99};
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
                int[] torqueStatusIdxByteRange = {101, 102};
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
                int[] angleStatusIdxByteRange = {104, 105};
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
                int[] rundownAngleStatusIdxByteRange = {107, 108};
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
                int[] currentMonitoringStatusIdxByteRange = {110, 111};
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
                int[] selftapStatusIdxByteRange = {113, 114};
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
                int[] prevailTorqueMonitoringStatusIdxByteRange = {116, 117};
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
                int[] prevailTorqueMonitoringCompensateIdxByteRange = {119, 120};
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
                int[] tighteningErrorStatusIdxByteRange = {122, 132};
                controllerHash.put("TIGHTENING_ERROR_STATUS", controllerMsg.substring(tighteningErrorStatusIdxByteRange[0], tighteningErrorStatusIdxByteRange[1]));
                //
                int[] torqueIdxByteRange = {134, 140};
                String torqueStringAscii = controllerMsg.substring(torqueIdxByteRange[0], torqueIdxByteRange[1]);
                //String torqueString = inverseAsciiDigits(100, 6, torqueStringAscii);
                String torqueString = new opUtilityMethods(100, 6, torqueStringAscii).inverseAsciiDigits();
                controllerHash.put("TORQUE", torqueString);
                //
                int[] angleIdxByteRange = {142, 147};
                controllerHash.put("ANGLE", controllerMsg.substring(angleIdxByteRange[0], angleIdxByteRange[1]));
                //
                int[] rundownAngleIdxByteRange = {149, 154};
                controllerHash.put("RUNDOWN_ANGLE", controllerMsg.substring(rundownAngleIdxByteRange[0], rundownAngleIdxByteRange[1]));
                //
                int[] currentMonitoringValueIdxByteRange = {156, 159};
                controllerHash.put("CURRENT_MONITORING_VALUE", controllerMsg.substring(currentMonitoringValueIdxByteRange[0], currentMonitoringValueIdxByteRange[1]));
                //
                int[] selftapTorqueIdxByteRange = {161, 167};
                String selftapTorqueValueStringAscii = controllerMsg.substring(selftapTorqueIdxByteRange[0], selftapTorqueIdxByteRange[1]);
                //String selftapTorqueValueString = inverseAsciiDigits(100, 6, selftapTorqueValueStringAscii);
                String selftapTorqueValueString = new opUtilityMethods(100, 6, selftapTorqueValueStringAscii).inverseAsciiDigits();
                controllerHash.put("SELFTAP_TORQUE", selftapTorqueValueString);
                //
                int[] prevailTorqueIdxByteRange = {169, 175};
                String prevailTorqueValueStringAscii = controllerMsg.substring(prevailTorqueIdxByteRange[0], prevailTorqueIdxByteRange[1]);
                //String prevailTorqueValueString = inverseAsciiDigits(100, 6, prevailTorqueValueStringAscii);
                String prevailTorqueValueString = new opUtilityMethods(100, 6, prevailTorqueValueStringAscii).inverseAsciiDigits();
                controllerHash.put("PREVAIL_TORQUE", prevailTorqueValueString);
                //
                int[] jobSequenceNumberIdxByteRange = {177, 182};
                controllerHash.put("JOB_SEQUENCE_NUMBER", controllerMsg.substring(jobSequenceNumberIdxByteRange[0], jobSequenceNumberIdxByteRange[1]));
                //
                int[] syncTighteningIDIdxByteRange = {184, 189};
                controllerHash.put("SYNC_TIGHTENING_ID", controllerMsg.substring(syncTighteningIDIdxByteRange[0], syncTighteningIDIdxByteRange[1]));
                //
                int[] toolSerialNumberIdxByteRange = {191, 205};
                controllerHash.put("TOOL_SERIAL_NUMBER", controllerMsg.substring(toolSerialNumberIdxByteRange[0], toolSerialNumberIdxByteRange[1]));
                //
                int[] timeStampIdxByteRange = {207, 226};
                controllerHash.put("TIMESTAMP", controllerMsg.substring(timeStampIdxByteRange[0], timeStampIdxByteRange[1]));
                //
                if (midRevisionInt > 2) {
                    int[] torqueValuesUnitIdxByteRange = {228, 229};
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
                    int[] resultTypeIdxByteRange = {231, 232};
                    String resultType = controllerMsg.substring(resultTypeIdxByteRange[0], resultTypeIdxByteRange[1]);
                    switch (resultType) {
                        case "1":
                            controllerHash.put("RESULT_TYPE", "Tightening");
                            break;
                        case "2":
                            controllerHash.put("RESULT_TYPE", "Loosening");
                            break;
                        case "3":
                            controllerHash.put("RESULT_TYPE", "Batch Increment ");
                            break;
                        case "4":
                            controllerHash.put("RESULT_TYPE", "Batch decrement");
                            break;
                        case "5":
                            controllerHash.put("RESULT_TYPE", "Bypass parameter set result");
                            break;
                        case "6":
                            controllerHash.put("RESULT_TYPE", "Abort Job result");
                            break;
                        case "7":
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
                    int[] identifierResult2IdxByteRange = {235, 260};
                    int[] identifierResult3IdxByteRange = {262, 287};
                    int[] identifierResult4IdxByteRange = {289, 314};
                    controllerHash.put("IDENTIFIER_RESULT_PART_2", controllerMsg.substring(identifierResult2IdxByteRange[0], identifierResult2IdxByteRange[1]));
                    controllerHash.put("IDENTIFIER_RESULT_PART_3", controllerMsg.substring(identifierResult3IdxByteRange[0], identifierResult3IdxByteRange[1]));
                    controllerHash.put("IDENTIFIER_RESULT_PART_4", controllerMsg.substring(identifierResult4IdxByteRange[0], identifierResult4IdxByteRange[1]));
                }
                //
                if (midRevisionInt > 4) {
                    int[] customerTighteningErrorCodeIdxByteRange = {316, 320};
                    controllerHash.put("CUSTOMER_TIGHTENING_ERROR_CODE", controllerMsg.substring(customerTighteningErrorCodeIdxByteRange[0], customerTighteningErrorCodeIdxByteRange[1]));
                }
                //
                if (midRevisionInt > 5) {
                    //
                    int[] prevailTorqueCompensateValueIdxByteRange = {322, 328};
                    String prevailTorqueCompensateValueStringAscii = controllerMsg.substring(prevailTorqueCompensateValueIdxByteRange[0], prevailTorqueCompensateValueIdxByteRange[1]);
                    //String prevailTorqueCompensateValueString = inverseAsciiDigits(100, 6, prevailTorqueCompensateValueStringAscii);
                    String prevailTorqueCompensateValueString = new opUtilityMethods(100, 6, prevailTorqueCompensateValueStringAscii).inverseAsciiDigits();
                    controllerHash.put("PREVAIL_TORQUE_COMPENSATE_VALUE", prevailTorqueCompensateValueString);
                    //
                    int[] tighteningErrorStatus2IdxByteRange = {330, 340};
                    controllerHash.put("TIGHTENING_ERROR_STATUS2", controllerMsg.substring(tighteningErrorStatus2IdxByteRange[0], tighteningErrorStatus2IdxByteRange[1]));
                }
                //
                if (midRevisionInt > 6) {
                    //
                    int[] stationIDIdxByteRange = {342, 352};
                    int[] stationNameIdxByteRange = {354, 379};
                    controllerHash.put("STATION_ID", controllerMsg.substring(stationIDIdxByteRange[0], stationIDIdxByteRange[1]));
                    controllerHash.put("STATION_NAME", controllerMsg.substring(stationNameIdxByteRange[0], stationNameIdxByteRange[1]));
                }
                //
                if (midRevisionInt > 7) {
                    int[] startFinalAngleIdxByteRange = {381, 387};
                    controllerHash.put("START_FINAL_ANGLE", controllerMsg.substring(startFinalAngleIdxByteRange[0], startFinalAngleIdxByteRange[1]));
                    //
                    int[] postViewTorqueActivatedIdxByteRange = {389, 390};
                    String postViewTorqueActivated = controllerMsg.substring(postViewTorqueActivatedIdxByteRange[0], postViewTorqueActivatedIdxByteRange[1]);
                    switch (postViewTorqueActivated) {
                        case "0":
                            controllerHash.put("POST_VIEW_TORQUE_ACTIVATED", "Off");
                            break;
                        case "1":
                            controllerHash.put("POST_VIEW_TORQUE_ACTIVATED", "On");
                            break;
                        case "2":
                            controllerHash.put("POST_VIEW_TORQUE_ACTIVATED", "Only PVTH on");
                            break;
                        case "3":
                            controllerHash.put("POST_VIEW_TORQUE_ACTIVATED", "Only PVTL on");
                            break;
                    }
                    int[] postViewTorqueHighIdxByteRange = {392, 398};
                    String postViewTorqueHighStringAscii = controllerMsg.substring(postViewTorqueHighIdxByteRange[0], postViewTorqueHighIdxByteRange[1]);
                    //String postViewTorqueHighString = inverseAsciiDigits(100, 6, postViewTorqueHighStringAscii);
                    String postViewTorqueHighString = new opUtilityMethods(100, 6, postViewTorqueHighStringAscii).inverseAsciiDigits();
                    controllerHash.put("POST_VIEW_TORQUE_HIGH", postViewTorqueHighString);
                    //
                    int[] postViewTorqueLowIdxByteRange = {392, 398};
                    String postViewTorqueLowStringAscii = controllerMsg.substring(postViewTorqueLowIdxByteRange[0], postViewTorqueLowIdxByteRange[1]);
                    //String postViewTorqueLowString = inverseAsciiDigits(100, 6, postViewTorqueLowStringAscii);
                    String postViewTorqueLowString = new opUtilityMethods(100, 6, postViewTorqueLowStringAscii).inverseAsciiDigits();
                    controllerHash.put("POST_VIEW_TORQUE_LOW", postViewTorqueLowString);
                }
                //
                if (midRevisionInt > 8) {
                    int[] currentMonitoringAmpIdxByteRange = {408, 413};
                    String currentMonitoringAmpStringAscii = controllerMsg.substring(currentMonitoringAmpIdxByteRange[0], currentMonitoringAmpIdxByteRange[1]);
                    //String currentMonitoringAmpString = inverseAsciiDigits(100, 5, currentMonitoringAmpStringAscii);
                    String currentMonitoringAmpString = new opUtilityMethods(100, 5, currentMonitoringAmpStringAscii).inverseAsciiDigits();
                    controllerHash.put("CURRENT_MONITORING_AMP", currentMonitoringAmpString);
                    //
                    int[] currentMonitoringAmpMinIdxByteRange = {415, 420};
                    String currentMonitoringAmpMinStringAscii = controllerMsg.substring(currentMonitoringAmpMinIdxByteRange[0], currentMonitoringAmpMinIdxByteRange[1]);
                    //String currentMonitoringAmpMinString = inverseAsciiDigits(100, 5, currentMonitoringAmpMinStringAscii);
                    String currentMonitoringAmpMinString = new opUtilityMethods(100, 5, currentMonitoringAmpMinStringAscii).inverseAsciiDigits();
                    controllerHash.put("CURRENT_MONITORING_AMP_MIN", currentMonitoringAmpMinString);
                    //
                    int[] currentMonitoringAmpMaxIdxByteRange = {422, 427};
                    String currentMonitoringAmpMaxStringAscii = controllerMsg.substring(currentMonitoringAmpMaxIdxByteRange[0], currentMonitoringAmpMaxIdxByteRange[1]);
                    //String currentMonitoringAmpMaxString = inverseAsciiDigits(100, 5, currentMonitoringAmpMaxStringAscii);
                    String currentMonitoringAmpMaxString = new opUtilityMethods(100, 5, currentMonitoringAmpMaxStringAscii).inverseAsciiDigits();
                    controllerHash.put("CURRENT_MONITORING_AMP_MAX", currentMonitoringAmpMaxString);
                }
                //
                if (midRevisionInt > 9) {
                    int[] angleNumeratorScaleFactorIdxByteRange = {429, 434};
                    String angleNumeratorScaleFactorStringAscii = controllerMsg.substring(angleNumeratorScaleFactorIdxByteRange[0], angleNumeratorScaleFactorIdxByteRange[1]);
                    //String angleNumeratorScaleFactorString = inverseAsciiDigits(100, 5, angleNumeratorScaleFactorStringAscii);
                    String angleNumeratorScaleFactorString = new opUtilityMethods(100, 5, angleNumeratorScaleFactorStringAscii).inverseAsciiDigits();
                    controllerHash.put("ANGLE_NUMERATOR_SCALE_FACTOR", angleNumeratorScaleFactorString);
                    //
                    int[] angleDenominatorScaleFactorIdxByteRange = {436, 441};
                    String angleDenominatorScaleFactorStringAscii = controllerMsg.substring(angleDenominatorScaleFactorIdxByteRange[0], angleDenominatorScaleFactorIdxByteRange[1]);
                    //String angleDenominatorScaleFactorString = inverseAsciiDigits(1, 5, angleDenominatorScaleFactorStringAscii);
                    String angleDenominatorScaleFactorString = new opUtilityMethods(1, 5, angleDenominatorScaleFactorStringAscii).inverseAsciiDigits();
                    controllerHash.put("ANGLE_DENOMINATOR_SCALE_FACTOR", angleDenominatorScaleFactorString);
                    //
                    int[] overallAngleStatusIdxByteRange = {443, 444};
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
                    int[] overallAngleMinIdxByteRange = {446, 451};
                    controllerHash.put("OVERALL_ANGLE_MIN", controllerMsg.substring(overallAngleMinIdxByteRange[0], overallAngleMinIdxByteRange[1]));
                    int[] overallAngleMaxIdxByteRange = {453, 458};
                    controllerHash.put("OVERALL_ANGLE_MAX", controllerMsg.substring(overallAngleMaxIdxByteRange[0], overallAngleMaxIdxByteRange[1]));
                    int[] overallAngleIdxByteRange = {460, 465};
                    controllerHash.put("OVERALL_ANGLE", controllerMsg.substring(overallAngleIdxByteRange[0], overallAngleIdxByteRange[1]));
                    //
                    int[] peakTorqueIdxByteRange = {467, 473};
                    String peakTorqueStringAscii = controllerMsg.substring(peakTorqueIdxByteRange[0], peakTorqueIdxByteRange[1]);
                    //String peakTorqueString = inverseAsciiDigits(100, 6, peakTorqueStringAscii);
                    String peakTorqueString = new opUtilityMethods(100, 6, peakTorqueStringAscii).inverseAsciiDigits();
                    controllerHash.put("PEAK_TORQUE", peakTorqueString);
                    //
                    int[] residualBreakawayTorqueIdxByteRange = {475, 481};
                    String residualBreakawayTorqueStringAscii = controllerMsg.substring(residualBreakawayTorqueIdxByteRange[0], residualBreakawayTorqueIdxByteRange[1]);
                    //String residualBreakawayTorqueString = inverseAsciiDigits(100, 6, residualBreakawayTorqueStringAscii);
                    String residualBreakawayTorqueString = new opUtilityMethods(100, 6, residualBreakawayTorqueStringAscii).inverseAsciiDigits();
                    controllerHash.put("RESIDUAL_BREAKAWAY_TORQUE", residualBreakawayTorqueString);
                    //
                    int[] startRundownAngleIdxByteRange = {483, 489};
                    String startRundownAngleStringAscii = controllerMsg.substring(startRundownAngleIdxByteRange[0], startRundownAngleIdxByteRange[1]);
                    //String startRundownAngleString = inverseAsciiDigits(100, 6, startRundownAngleStringAscii);
                    String startRundownAngleString = new opUtilityMethods(100, 6, startRundownAngleStringAscii).inverseAsciiDigits();
                    controllerHash.put("START_RUNDOWN_ANGLE", startRundownAngleString);
                    //
                    int[] rundownAngleCompleteIdxByteRange = {491, 497};
                    String rundownAngleCompleteStringAscii = controllerMsg.substring(rundownAngleCompleteIdxByteRange[0], rundownAngleCompleteIdxByteRange[1]);
                    //String rundownAngleCompleteString = inverseAsciiDigits(100, 6, rundownAngleCompleteStringAscii);
                    String rundownAngleCompleteString = new opUtilityMethods(100, 6, rundownAngleCompleteStringAscii).inverseAsciiDigits();
                    controllerHash.put("RUNDOWN_ANGLE_COMPLETE", rundownAngleCompleteString);
                }
                //
                if (midRevisionInt > 10) {
                    int[] clickTorqueIdxByteRange = {499, 505};
                    String clickTorqueStringAscii = controllerMsg.substring(clickTorqueIdxByteRange[0], clickTorqueIdxByteRange[1]);
                    //String clickTorqueString = inverseAsciiDigits(100, 6, clickTorqueStringAscii);
                    String clickTorqueString = new opUtilityMethods(100, 6, clickTorqueStringAscii).inverseAsciiDigits();
                    controllerHash.put("CLICK_TORQUE", clickTorqueString);
                    //
                    int[] angleDenominatorScaleFactorIdxByteRange = {436, 441};
                    controllerHash.put("CLICK_ANGLE", controllerMsg.substring(angleDenominatorScaleFactorIdxByteRange[0], angleDenominatorScaleFactorIdxByteRange[1]));
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

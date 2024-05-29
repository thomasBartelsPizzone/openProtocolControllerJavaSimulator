package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0140 {
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
    // MID 0140 Execute dynamic Job request
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        // String midRevision = "000";
         String midRevision = "001";
//        String midRevision = "002";
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
            opUtilityMethods opM = new opUtilityMethods();
            int jobDataCount = 1;
            for (Object s : dataFieldValue) {
                midAscii.append(String.format("%02d", jobDataCount));
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    String format = String.format("%04d", Integer.parseInt(s.toString()));
                    if (jobDataCount == 1) {
                        //midAscii.append(String.format("%04d", s));
                        midAscii.append(format);
                    } else if (jobDataCount == 2) {
                        midAscii.append(s);
                    } else if (jobDataCount == 3) {
                        //midAscii.append(String.format("%02d", s));
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else if (jobDataCount >= 4 && jobDataCount < 12) {
                        midAscii.append(s);
                    } else if (jobDataCount == 12) {
                        //midAscii.append(String.format("%04d", s));
                        midAscii.append(format);
                    } else if (jobDataCount == 13) {
                        //midAscii.append(String.format("%05d", s));
                        midAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                    } else if (jobDataCount == 14) {
                        //midAscii.append(String.format("%04d", s));
                        midAscii.append(format);
                    } else if (jobDataCount > 14) {
                        midAscii.append(s);
                    } else {
                        midAscii.append(s);
                    }
                } else {
                    midAscii.append(s);
                }
                jobDataCount += 1;
            }
        }
        // Length
        int midLength = tempMidAscii.length() + 4;
        // combine string
        midAscii.append(String.format("%04d", midLength));
        // append remaining message
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
            int[] jobIDIdxByteRange = {22, 24};
            controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));

            int[] jobNameIdxByteRange = {26, 51};
            controllerHash.put("JOB_NAME", controllerMsg.substring(jobNameIdxByteRange[0], jobNameIdxByteRange[1]));
            //
            int[] numberOfParameterSetsIdxByteRange = {53, 55};
            String numberOfParameterSetsString = controllerMsg.substring(numberOfParameterSetsIdxByteRange[0], numberOfParameterSetsIdxByteRange[1]);
            int numberOfParameterSetsInteger = Integer.parseInt(numberOfParameterSetsString);

            controllerHash.put("NUMBER_OF_PARAMETER_SETS", numberOfParameterSetsInteger);
            int jobListIdxByteRange = 56;

            for (int j=0; j < numberOfParameterSetsInteger; j++) {
                //
                HashMap<String, String> tempHash = new HashMap<>();
                //
                String channelId = "CHANNEL_ID";
                String channelIdValue = controllerMsg.substring(jobListIdxByteRange + j*15, jobListIdxByteRange + j*12 + 2);
                tempHash.put(channelId, channelIdValue);
                //
                String typeId = "PROGRAM_ID";
                String typeIdValue = controllerMsg.substring(jobListIdxByteRange + j*15 + 3, jobListIdxByteRange + j*12 + 6);
                tempHash.put(typeId, typeIdValue);
                //
                String autoValue = controllerMsg.substring(jobListIdxByteRange + j*15 + 7, jobListIdxByteRange + j*12 + 8);
                tempHash.put("AUTO_SELECT", autoValue);
                //
                String batchSize = controllerMsg.substring(jobListIdxByteRange + j*15 + 9, jobListIdxByteRange + j*12 + 11);
                tempHash.put("BATCH_SIZE", batchSize);
                //
                String batchCoherentNOK = controllerMsg.substring(jobListIdxByteRange + j*15 + 12, jobListIdxByteRange + j*15 + 14);
                tempHash.put("MAX_COHERENT_NOK", batchCoherentNOK);
                //
                controllerHash.put("PARAMETER_SET" + (j + 1), tempHash);
            }
            //
            int forcedOrderIdxByteStart = jobListIdxByteRange * numberOfParameterSetsInteger * 15 + 2;
            int[] forcedOrderIdxByteRange = {forcedOrderIdxByteStart, forcedOrderIdxByteStart + 1};
            String forcedOrderValue = controllerMsg.substring(forcedOrderIdxByteRange[0], forcedOrderIdxByteRange[1]);
            if (forcedOrderValue.equals("0")) {
                controllerHash.put("FORCED_ORDER", "free_order");
            } else if (forcedOrderValue.equals("1")) {
                controllerHash.put("FORCED_ORDER", "forced_order");
            } else {
                controllerHash.put("FORCED_ORDER", "free_and_forced");
            }
            //
            int lockAtJobDoneIdxByteStart = forcedOrderIdxByteStart + 3;
            int[] lockAtJobDoneIdxByteRange = {lockAtJobDoneIdxByteStart, lockAtJobDoneIdxByteStart + 1};
            String lockAtJobDoneValue = controllerMsg.substring(lockAtJobDoneIdxByteRange[0], lockAtJobDoneIdxByteRange[1]);
            if (lockAtJobDoneValue.equals("0")) {
                controllerHash.put("LOCK_AT_JOB_DONE", "No");
            } else {
                controllerHash.put("LOCK_AT_JOB_DONE", "Yes");
            }
            //
            int toolLooseningIdxByteStart = lockAtJobDoneIdxByteStart + 3;
            int[] toolLooseningIdxByteRange = {toolLooseningIdxByteStart, toolLooseningIdxByteStart + 1};
            String toolLooseningValue = controllerMsg.substring(toolLooseningIdxByteRange[0], toolLooseningIdxByteRange[1]);
            if (toolLooseningValue.equals("0")) {
                controllerHash.put("TOOL_LOOSENING", "Enable");
            } else if (toolLooseningValue.equals("1")) {
                controllerHash.put("TOOL_LOOSENING", "Disable");
            } else {
                controllerHash.put("TOOL_LOOSENING", "Enable_NOK_Tightening");
            }
            //
            int repeatJobIdxByteStart = toolLooseningIdxByteStart + 3;
            int[] repeatJobIdxByteRange = {repeatJobIdxByteStart, repeatJobIdxByteStart + 1};
            String repeatJobValue = controllerMsg.substring(repeatJobIdxByteRange[0], repeatJobIdxByteRange[1]);
            if (repeatJobValue.equals("0")) {
                controllerHash.put("REPEAT_JOB", "No");
            } else {
                controllerHash.put("YES", "Yes");
            }
            //
            int jobBatchCountTypeIdxByteStart = repeatJobIdxByteStart + 3;
            int[] jobBatchCountTypeIdxByteRange = {jobBatchCountTypeIdxByteStart, jobBatchCountTypeIdxByteStart + 1};
            String jobBatchCountTypeValue = controllerMsg.substring(jobBatchCountTypeIdxByteRange[0], jobBatchCountTypeIdxByteRange[1]);
            if (jobBatchCountTypeValue.equals("0")) {
                controllerHash.put("JOB_BATCH_COUNT_TYPE", "OK_TIGHTENING");
            } else {
                controllerHash.put("JOB_BATCH_COUNT_TYPE", "OK_NOK_TIGHTENING");
            }
            //
            int batchStatusIncrementBypassIdxByteStart = jobBatchCountTypeIdxByteStart + 3;
            int[] batchStatusIncrementBypassIdxByteRange = {batchStatusIncrementBypassIdxByteStart, batchStatusIncrementBypassIdxByteStart + 1};
            String batchStatusIncrementBypassValue = controllerMsg.substring(batchStatusIncrementBypassIdxByteRange[0], batchStatusIncrementBypassIdxByteRange[1]);
            if (batchStatusIncrementBypassValue.equals("0")) {
                controllerHash.put("BATCH_STATUS_AT_INCREMENT_BYPASS", "OK");
            } else {
                controllerHash.put("BATCH_STATUS_AT_INCREMENT_BYPASS", "NOK");
            }
            //
            int decrementBatchIdxByteStart = batchStatusIncrementBypassIdxByteStart + 3;
            int[] decrementBatchIdxByteRange = {decrementBatchIdxByteStart, decrementBatchIdxByteStart + 1};
            String decrementBatchValue = controllerMsg.substring(decrementBatchIdxByteRange[0], decrementBatchIdxByteRange[1]);
            if (decrementBatchValue.equals("0")) {
                controllerHash.put("DECREMENT_BATCH_AT_OK_LOOSENING", "NO");
            } else {
                controllerHash.put("DECREMENT_BATCH_AT_OK_LOOSENING", "YES");
            }
            //
            int maxFirstTighteningTimeIdxByteStart = decrementBatchIdxByteStart + 3;
            int[] maxFirstTighteningTimeIdxByteRange = {maxFirstTighteningTimeIdxByteStart, maxFirstTighteningTimeIdxByteStart + 4};
            controllerHash.put("MAX_TIME_FOR_FIRST_TIGHTENING", controllerMsg.substring(maxFirstTighteningTimeIdxByteRange[0], maxFirstTighteningTimeIdxByteRange[1]));
            //
            int maxTimeToCompleteJobIdxByteStart = maxFirstTighteningTimeIdxByteStart + 6;
            int[] maxTimeToCompleteJobIdxByteRange = {maxTimeToCompleteJobIdxByteStart, maxTimeToCompleteJobIdxByteStart + 5};
            controllerHash.put("MAX_TIME_TO_COMPLETE_JOB", controllerMsg.substring(maxTimeToCompleteJobIdxByteRange[0], maxTimeToCompleteJobIdxByteRange[1]));
            //
            int displayResultAutoSelectJobIdxByteStart = maxTimeToCompleteJobIdxByteStart + 7;
            int[] displayResultAutoSelectJobIdxByteRange = {displayResultAutoSelectJobIdxByteStart, displayResultAutoSelectJobIdxByteStart + 4};
            controllerHash.put("DISPLAY_RESULT_AT_AUTO_SELECT", controllerMsg.substring(displayResultAutoSelectJobIdxByteRange[0], displayResultAutoSelectJobIdxByteRange[1]));
            //
            int useLineControlIdxByteStart = displayResultAutoSelectJobIdxByteStart + 6;
            int[] useLineControlIdxByteRange = {useLineControlIdxByteStart, useLineControlIdxByteStart + 1};
            String useLineControlValue = controllerMsg.substring(useLineControlIdxByteRange[0], useLineControlIdxByteRange[1]);
            if (useLineControlValue.equals("0")) {
                controllerHash.put("USE_LINE_CONTROL", "NO");
            } else {
                controllerHash.put("USE_LINE_CONTROL", "YES");
            }
            int identifierResultPart1IdxByteStart = useLineControlIdxByteStart + 3;
            int[] identifierResultPart1IdxByteRange = {identifierResultPart1IdxByteStart, identifierResultPart1IdxByteStart + 1};
            String identifierResultPart1Value = controllerMsg.substring(identifierResultPart1IdxByteRange[0], identifierResultPart1IdxByteRange[1]);
            if (identifierResultPart1Value.equals("0")) {
                controllerHash.put("ID_RESULT_PART1_RESULT_OF_NON_TIGHTENING", "Job VIN number, save the identifier that triggered in identifier \n" +
                        "result part 1");
            } else {
                controllerHash.put("ID_RESULT_PART1_RESULT_OF_NON_TIGHTENING", "other");
            }
            //
            int identifierResultPart2IdxByteStart = identifierResultPart1IdxByteStart + 3;
            int[] identifierResultPart2IdxByteRange = {identifierResultPart2IdxByteStart, identifierResultPart2IdxByteStart + 1};
            String identifierResultPart2Value = controllerMsg.substring(identifierResultPart2IdxByteRange[0], identifierResultPart2IdxByteRange[1]);
            if (identifierResultPart2Value.equals("0")) {
                controllerHash.put("ID_RESULT_PART2_RESULT_OF_NON_TIGHTENING", "No");
            } else {
                controllerHash.put("ID_RESULT_PART2_RESULT_OF_NON_TIGHTENING", "Yes");
            }
            //
            int resetAllIdentifiersAtJobDoneIdxByteStart = identifierResultPart2IdxByteStart + 3;
            int[] resetAllIdentifiersAtJobDoneIdxByteRange = {resetAllIdentifiersAtJobDoneIdxByteStart, resetAllIdentifiersAtJobDoneIdxByteStart + 1};
            String resetAllIdentifiersAtJobDoneValue = controllerMsg.substring(resetAllIdentifiersAtJobDoneIdxByteRange[0], resetAllIdentifiersAtJobDoneIdxByteRange[1]);
            if (resetAllIdentifiersAtJobDoneValue.equals("0")) {
                controllerHash.put("RESET_ALL_IDENTIFIERS_AT_JOB_DONE", "No");
            } else {
                controllerHash.put("RESET_ALL_IDENTIFIERS_AT_JOB_DONE", "Yes");
            }
            //
            int reservedIdxByteStart = identifierResultPart2IdxByteStart + 3;
            int[] reservedIdxByteRange = {reservedIdxByteStart, reservedIdxByteStart + 1};
            String reservedValue = controllerMsg.substring(reservedIdxByteRange[0], reservedIdxByteRange[1]);
            if (reservedValue.equals("0")) {
                controllerHash.put("RESERVED", "E");
            } else {
                controllerHash.put("RESERVED", "G");
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

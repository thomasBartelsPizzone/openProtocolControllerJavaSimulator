package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0033 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
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
            opUtilityMethods opM = new opUtilityMethods();
            int jobDataCount = 1;
            int parameterCount = 0;
            for (Object s : dataFieldValue) {
                midAscii.append(String.format("%02d", jobDataCount));
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    if (jobDataCount == 1) {
                        //midAscii.append(String.format("%02d", s));
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else if (jobDataCount > 1 && jobDataCount < 4) {
                        midAscii.append(s);
                    } else if (jobDataCount == 4) {
                        //midAscii.append(String.format("%04d", s));
                        midAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                    } else if (jobDataCount == 5) {
                        //midAscii.append(String.format("%05d", s));
                        midAscii.append(String.format("%05d", Integer.parseInt(s.toString())));
                    } else if (jobDataCount >= 6 && jobDataCount < 12) {
                        midAscii.append(s);
                    } else if (jobDataCount == 12) {
                        parameterCount = (int) s;
                        //midAscii.append(String.format("%02d", s));
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else {
                    //} else if (jobDataCount >= 13) {
                        midAscii.append(s);
                        //for (int j=0; j < parameterCount; j++) {
                        //
                        //}
                    }
                } else {
                    midAscii.append(s);
                }
                jobDataCount += 1;
            }
        }
        // Length
        int midLength = tempMidAscii.length() + 4;
        // append length
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
            if (midRevision.equals("001")) {
                int[] jobIDIdxByteRange = {22, 24};
                int[] jobNameIdxByteRange = {26, 51};
                //
                controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));
                controllerHash.put("JOB_NAME", controllerMsg.substring(jobNameIdxByteRange[0], jobNameIdxByteRange[1]));
                //
                int[] forcedOrderIdxByteRange = {53, 54};
                String[] forcedOrderArray = {"FREE_ORDER", "FORCED_ORDER", "FREE_AND_FORCED"};
                //
                String forcedOrderValue = controllerMsg.substring(forcedOrderIdxByteRange[0], forcedOrderIdxByteRange[1]);
                if (forcedOrderValue.equals("0")) {
                    controllerHash.put("FORCED_ORDER", forcedOrderArray[0]);
                } else if (forcedOrderValue.equals("1")) {
                    controllerHash.put("FORCED_ORDER", forcedOrderArray[1]);
                } else {
                    controllerHash.put("FORCED_ORDER", forcedOrderArray[2]);
                }
                //
                int[] maxTimeFirstTighteningIdxByteRange = {56, 60};
                int[] maxTimeJobCompleteIdxByteRange = {62, 67};
                //
                controllerHash.put("MAX_FIRST_TIGHTENING_TIME", controllerMsg.substring(maxTimeFirstTighteningIdxByteRange[0], maxTimeFirstTighteningIdxByteRange[1]));
                controllerHash.put("MAX_TIGHTENING_COMPLETION_TIME", controllerMsg.substring(maxTimeJobCompleteIdxByteRange[0], maxTimeJobCompleteIdxByteRange[1]));
                //
                int[] jobBatchModeIdxByteRange = {69, 70};
                String[] jobBatchModeArray = {"OK", "OK_NOK"};
                //
                String jobBatchModeValue = controllerMsg.substring(jobBatchModeIdxByteRange[0], jobBatchModeIdxByteRange[1]);
                if (jobBatchModeValue.equals("0")) {
                    controllerHash.put("JOB_BATCH_MODE", jobBatchModeArray[0]);
                } else {
                    controllerHash.put("JOB_BATCH_MODE", jobBatchModeArray[1]);
                }
                //
                int[] lockJobDoneIdxByteRange = {72, 73};
                String[] lockJobDoneArray = {"NO", "YES"};
                //
                String lockJobDoneValue = controllerMsg.substring(lockJobDoneIdxByteRange[0], lockJobDoneIdxByteRange[1]);
                if (lockJobDoneValue.equals("0")) {
                    controllerHash.put("LOCK_JOB_DONE", lockJobDoneArray[0]);
                } else {
                    controllerHash.put("LOCK_JOB_DONE", lockJobDoneArray[1]);
                }
                //
                int[] useLineControlIdxByteRange = {75, 76};
                String[] useLineControlArray = {"NO", "YES"};
                String useLineControlValue = controllerMsg.substring(useLineControlIdxByteRange[0], useLineControlIdxByteRange[1]);
                if (useLineControlValue.equals("0")) {
                    controllerHash.put("USE_LINE_CONTROL", useLineControlArray[0]);
                } else {
                    controllerHash.put("USE_LINE_CONTROL", useLineControlArray[1]);
                }
                //
                int[] repeatJobIdxByteRange = {78, 79};
                String[] repeatJobArray = {"NO", "YES"};
                String repeatJobValue = controllerMsg.substring(repeatJobIdxByteRange[0], repeatJobIdxByteRange[1]);
                if (repeatJobValue.equals("0")) {
                    controllerHash.put("REPEAT_JOB", repeatJobArray[0]);
                } else {
                    controllerHash.put("REPEAT_JOB", repeatJobArray[1]);
                }
                //
                int[] toolLooseningIdxByteRange = {81, 82};
                String[] toolLooseningArray = {"ENABLE", "DISABLE", "ENABLE_ON_NOK"};
                String toolLooseningValue = controllerMsg.substring(toolLooseningIdxByteRange[0], toolLooseningIdxByteRange[1]);
                if (toolLooseningValue.equals("0")) {
                    controllerHash.put("TOOL_LOOSENING", toolLooseningArray[0]);
                } else if (toolLooseningValue.equals("1")) {
                    controllerHash.put("TOOL_LOOSENING", toolLooseningArray[1]);
                } else {
                    controllerHash.put("TOOL_LOOSENING", toolLooseningArray[2]);
                }
                //
                // int[] reservedIdxByteRange = {84, 85};
                // String[] reservedArray = {"E", "G"};
                // String reservedValue = controllerMsg.substring(reservedIdxByteRange[0], reservedIdxByteRange[1]);
                // if (reservedValue.equals("0")) {
                //     controllerHash.put("RESERVED", reservedArray[0]);
                // } else {
                //     controllerHash.put("RESERVED", reservedArray[1]);
                // }
                //
                // int[] numberOfParameterSetsIdxByteRange = {87, 89};
                int[] numberOfParameterSetsIdxByteRange = {84, 86};
                String numberOfParameterSetsString = controllerMsg.substring(numberOfParameterSetsIdxByteRange[0], numberOfParameterSetsIdxByteRange[1]);
                int numberOfParameterSetsInteger = Integer.parseInt(numberOfParameterSetsString);

                controllerHash.put("NUMBER_OF_PARAMETER_SETS", numberOfParameterSetsInteger);
                //
                // int jobListIdxByteRange = 91;
                int jobListIdxByteRange = 88;
                //
                for (int j=0; j < numberOfParameterSetsInteger; j++) {
                    //
                    HashMap<String, String> tempHash = new HashMap<>();
                    //
                    String channelId = "CHANNEL_ID";
                    String channelIdValue = controllerMsg.substring(jobListIdxByteRange + j*12, jobListIdxByteRange + j*12 + 2);
                    tempHash.put(channelId, channelIdValue);
                    //
                    String typeId = "TYPE_ID";
                    String typeIdValue = controllerMsg.substring(jobListIdxByteRange + j*12 + 3, jobListIdxByteRange + j*12 + 6);
                    tempHash.put(typeId, typeIdValue);
                    //
                    String autoValue = controllerMsg.substring(jobListIdxByteRange + j*12 + 7, jobListIdxByteRange + j*12 + 8);
                    tempHash.put("AUTO_VALUE", autoValue);
                    //
                    String batchSize = controllerMsg.substring(jobListIdxByteRange + j*12 + 9, jobListIdxByteRange + j*12 + 11);
                    tempHash.put("BATCH_SIZE", batchSize);
                    //
                    controllerHash.put("PARAMETER_SET" + (j + 1), tempHash);
                }
            } else {
                //
                int[] jobIDIdxByteRange = {22, 26};
                int[] jobNameIdxByteRange = {28, 53};
                //
                controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));
                controllerHash.put("JOB_NAME", controllerMsg.substring(jobNameIdxByteRange[0], jobNameIdxByteRange[1]));
                //
                int[] forcedOrderIdxByteRange = {55, 56};
                String[] forcedOrderArray = {"FREE_ORDER", "FORCED_ORDER", "FREE_AND_FORCED"};
                //
                String forcedOrderValue = controllerMsg.substring(forcedOrderIdxByteRange[0], forcedOrderIdxByteRange[1]);
                if (forcedOrderValue.equals("0")) {
                    controllerHash.put("FORCED_ORDER", forcedOrderArray[0]);
                } else if (forcedOrderValue.equals("1")) {
                    controllerHash.put("FORCED_ORDER", forcedOrderArray[1]);
                } else {
                    controllerHash.put("FORCED_ORDER", forcedOrderArray[2]);
                }
                //
                int[] maxTimeFirstTighteningIdxByteRange = {58, 62};
                int[] maxTimeJobCompleteIdxByteRange = {64, 69};
                //
                controllerHash.put("MAX_FIRST_TIGHTENING_TIME", controllerMsg.substring(maxTimeFirstTighteningIdxByteRange[0], maxTimeFirstTighteningIdxByteRange[1]));
                controllerHash.put("MAX_TIGHTENING_COMPLETION_TIME", controllerMsg.substring(maxTimeJobCompleteIdxByteRange[0], maxTimeJobCompleteIdxByteRange[1]));
                //
                int[] jobBatchModeIdxByteRange = {71, 72};
                String[] jobBatchModeArray = {"OK", "OK_NOK"};
                //
                String jobBatchModeValue = controllerMsg.substring(jobBatchModeIdxByteRange[0], jobBatchModeIdxByteRange[1]);
                if (jobBatchModeValue.equals("0")) {
                    controllerHash.put("JOB_BATCH_MODE", jobBatchModeArray[0]);
                } else {
                    controllerHash.put("JOB_BATCH_MODE", jobBatchModeArray[1]);
                }
                //
                int[] lockJobDoneIdxByteRange = {73, 74};
                String[] lockJobDoneArray = {"NO", "YES"};
                //
                String lockJobDoneValue = controllerMsg.substring(lockJobDoneIdxByteRange[0], lockJobDoneIdxByteRange[1]);
                if (lockJobDoneValue.equals("0")) {
                    controllerHash.put("LOCK_JOB_DONE", lockJobDoneArray[0]);
                } else {
                    controllerHash.put("LOCK_JOB_DONE", lockJobDoneArray[1]);
                }
                //
                int[] useLineControlIdxByteRange = {77, 78};
                String[] useLineControlArray = {"NO", "YES"};
                String useLineControlValue = controllerMsg.substring(useLineControlIdxByteRange[0], useLineControlIdxByteRange[1]);
                if (useLineControlValue.equals("0")) {
                    controllerHash.put("USE_LINE_CONTROL", useLineControlArray[0]);
                } else {
                    controllerHash.put("USE_LINE_CONTROL", useLineControlArray[1]);
                }
                //
                int[] repeatJobIdxByteRange = {80, 81};
                String[] repeatJobArray = {"NO", "YES"};
                String repeatJobValue = controllerMsg.substring(repeatJobIdxByteRange[0], repeatJobIdxByteRange[1]);
                if (repeatJobValue.equals("0")) {
                    controllerHash.put("REPEAT_JOB", repeatJobArray[0]);
                } else {
                    controllerHash.put("REPEAT_JOB", repeatJobArray[1]);
                }
                //
                int[] toolLooseningIdxByteRange = {83, 84};
                String[] toolLooseningArray = {"ENABLE", "DISABLE", "ENABLE_ON_NOK"};
                String toolLooseningValue = controllerMsg.substring(toolLooseningIdxByteRange[0], toolLooseningIdxByteRange[1]);
                if (toolLooseningValue.equals("0")) {
                    controllerHash.put("TOOL_LOOSENING", toolLooseningArray[0]);
                } else if (toolLooseningValue.equals("1")) {
                    controllerHash.put("TOOL_LOOSENING", toolLooseningArray[1]);
                } else {
                    controllerHash.put("TOOL_LOOSENING", toolLooseningArray[2]);
                }
                //
                int[] reservedIdxByteRange = {86, 87};
                String[] reservedArray = {"E", "G"};
                String reservedValue = controllerMsg.substring(reservedIdxByteRange[0], reservedIdxByteRange[1]);
                if (reservedValue.equals("0")) {
                    controllerHash.put("RESERVED", reservedArray[0]);
                } else {
                    controllerHash.put("RESERVED", reservedArray[1]);
                }
                //
                int[] numberOfParameterSetsIdxByteRange = {89, 91};
                String numberOfParameterSetsString = controllerMsg.substring(numberOfParameterSetsIdxByteRange[0], numberOfParameterSetsIdxByteRange[1]);
                int numberOfParameterSetsInteger = Integer.parseInt(numberOfParameterSetsString);
                //
                switch (midRevision) {
                    case "002": {
                        controllerHash.put("NUMBER_OF_PARAMETER_SETS", numberOfParameterSetsInteger);
                        //
                        int jobListIdxByteRange = 93;
                        //
                        for (int j = 0; j < numberOfParameterSetsInteger; j++) {
                            //
                            HashMap<String, String> tempHash = new HashMap<>();
                            //
                            String channelId = "CHANNEL_ID";
                            String channelIdValue = controllerMsg.substring(jobListIdxByteRange + j * 12, jobListIdxByteRange + j * 12 + 2);
                            tempHash.put(channelId, channelIdValue);
                            //
                            String typeId = "TYPE_ID";
                            String typeIdValue = controllerMsg.substring(jobListIdxByteRange + j * 12 + 3, jobListIdxByteRange + j * 12 + 6);
                            tempHash.put(typeId, typeIdValue);
                            //
                            String autoValue = controllerMsg.substring(jobListIdxByteRange + j * 12 + 7, jobListIdxByteRange + j * 12 + 8);
                            tempHash.put("AUTO_VALUE", autoValue);
                            //
                            String batchSize = controllerMsg.substring(jobListIdxByteRange + j * 12 + 9, jobListIdxByteRange + j * 12 + 11);
                            tempHash.put("BATCH_SIZE", batchSize);
                            //
                            controllerHash.put("PARAMETER_SET" + (j + 1), tempHash);
                        }
                        break;
                    }
                    case "003": {
                        controllerHash.put("NUMBER_OF_PARAMETER_SETS", numberOfParameterSetsInteger);
                        //
                        int jobListIdxByteRange = 93;
                        //
                        for (int j = 0; j < numberOfParameterSetsInteger; j++) {
                            //
                            HashMap<String, String> tempHash = new HashMap<>();
                            //
                            String channelId = "CHANNEL_ID";
                            String channelIdValue = controllerMsg.substring(jobListIdxByteRange + j * 44, jobListIdxByteRange + j * 44 + 2);
                            tempHash.put(channelId, channelIdValue);
                            //
                            String typeId = "TYPE_ID";
                            String typeIdValue = controllerMsg.substring(jobListIdxByteRange + j * 44 + 3, jobListIdxByteRange + j * 44 + 6);
                            tempHash.put(typeId, typeIdValue);
                            //
                            String autoValue = controllerMsg.substring(jobListIdxByteRange + j * 44 + 7, jobListIdxByteRange + j * 44 + 8);
                            tempHash.put("AUTO_VALUE", autoValue);
                            //
                            String batchSize = controllerMsg.substring(jobListIdxByteRange + j * 44 + 9, jobListIdxByteRange + j * 44 + 11);
                            tempHash.put("BATCH_SIZE", batchSize);
                            //
                            String socket = controllerMsg.substring(jobListIdxByteRange + j * 44 + 12, jobListIdxByteRange + j * 44 + 14);
                            tempHash.put("SOCKET", socket);
                            //
                            String jobStepName = controllerMsg.substring(jobListIdxByteRange + j * 44 + 15, jobListIdxByteRange + j * 44 + 40);
                            tempHash.put("JOB_STEP_NAME", jobStepName);
                            //
                            String jobStepType = controllerMsg.substring(jobListIdxByteRange + j * 44 + 41, jobListIdxByteRange + j * 44 + 43);
                            tempHash.put("JOB_STEP_TYPE", jobStepName);
                            //
                            controllerHash.put("PARAMETER_SET" + (j + 1), tempHash);
                        }
                        break;
                    }
                    case "004": {
                        controllerHash.put("NUMBER_OF_PARAMETER_SETS", numberOfParameterSetsInteger);
                        //
                        int jobListIdxByteRange = 93;
                        //
                        for (int j = 0; j < numberOfParameterSetsInteger; j++) {
                            //
                            HashMap<String, String> tempHash = new HashMap<>();
                            //
                            String channelId = "CHANNEL_ID";
                            String channelIdValue = controllerMsg.substring(jobListIdxByteRange + j * 49, jobListIdxByteRange + j * 49 + 2);
                            tempHash.put(channelId, channelIdValue);
                            //
                            String typeId = "TYPE_ID";
                            String typeIdValue = controllerMsg.substring(jobListIdxByteRange + j * 49 + 3, jobListIdxByteRange + j * 49 + 6);
                            tempHash.put(typeId, typeIdValue);
                            //
                            String autoValue = controllerMsg.substring(jobListIdxByteRange + j * 49 + 7, jobListIdxByteRange + j * 49 + 8);
                            tempHash.put("AUTO_VALUE", autoValue);
                            //
                            String batchSize = controllerMsg.substring(jobListIdxByteRange + j * 49 + 9, jobListIdxByteRange + j * 49 + 11);
                            tempHash.put("BATCH_SIZE", batchSize);
                            //
                            String idNumber = controllerMsg.substring(jobListIdxByteRange + j * 49 + 12, jobListIdxByteRange + j * 49 + 16);
                            tempHash.put("ID_NUMBER", idNumber);
                            //
                            String jobStepName = controllerMsg.substring(jobListIdxByteRange + j * 49 + 17, jobListIdxByteRange + j * 49 + 42);
                            tempHash.put("JOB_STEP_NAME", jobStepName);
                            //
                            String jobStepType = controllerMsg.substring(jobListIdxByteRange + j * 49 + 43, jobListIdxByteRange + j * 49 + 45);
                            tempHash.put("JOB_STEP_TYPE", jobStepType);
                            //
                            String maxCoherentNOK = controllerMsg.substring(jobListIdxByteRange + j * 49 + 46, jobListIdxByteRange + j * 49 + 48);
                            tempHash.put("MAX_COHERENT_NOK", maxCoherentNOK);
                            //
                            controllerHash.put("PARAMETER_SET" + (j + 1), tempHash);
                        }
                        break;
                    }
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
package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0107 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0108";
        } else {
            return null;
        }
    }
    //
    // MID 0107 Last PowerMACS tightening result Bolt data
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        // String midLengthString = "0020";
        String midLengthString = null;
        String midRevision = "003";
        // String midRevision = null;
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
            int dFCount = 1;
            int messageCount = 0;
            for (Object s : dataFieldValue) {
                tempMidAscii.append(String.format("%02d", dFCount));
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    String format02d = String.format("%02d", Integer.parseInt(s.toString()));
                    String format04d = String.format("%04d", Integer.parseInt(s.toString()));
                    if (dFCount == 1) {
                        messageCount = (int) s;
                        //tempMidAscii.append(String.format("%02d", s));
                        tempMidAscii.append(format02d);
                    } else if (dFCount == 2) {
                        //tempMidAscii.append(String.format("%02d", s));
                        tempMidAscii.append(format02d);
                    } else if (dFCount == 3) {
                        //tempMidAscii.append(String.format("%10d", s));
                        tempMidAscii.append(String.format("%10d", Integer.parseInt(s.toString())));
                    } else if (dFCount == 4) {
                        //tempMidAscii.append(String.format("%02d", s));
                        tempMidAscii.append(format02d);
                    } else if (dFCount == 5) {
                        tempMidAscii.append(s);
                    } else if (dFCount == 6) {
                        //tempMidAscii.append(String.format("%04d", s));
                        //tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        tempMidAscii.append(format04d);
                    } else if (dFCount > 6 && dFCount < 9) {
                        //tempMidAscii.append(String.format("%20d", s));
                        tempMidAscii.append(String.format("%20d", Integer.parseInt(s.toString())));
                    } else if (dFCount > 8 && dFCount < 11) {
                        tempMidAscii.append(s);
                    } else if (dFCount == 11) {
                        //tempMidAscii.append(String.format("%04d", s));
                        //tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                        tempMidAscii.append(format04d);
                    } else if (dFCount > 11 && dFCount < (11 + messageCount)) {
                        //tempMidAscii.append(String.format("%02d", messageCount));
                        tempMidAscii.append(format02d);
                        int messageCountloop = messageCount;
                        while (messageCountloop > 0) {
                            for (int i = 0; i < messageCount; i++) {
                                // Variable Name
                                tempMidAscii.append(s.toString(), 0, 20);
                                // Variable Type
                                tempMidAscii.append(s.toString(), 20, 22);
                                // Value
                                tempMidAscii.append(s.toString(), 22, 29);
                            }
                            messageCountloop -= 1;
                        }
                        tempMidAscii.append(s);
                    } else {
                        tempMidAscii.append(s);
                    }
                } else {
                    if (dFCount > 11 && dFCount < (11 + messageCount)) {
                        tempMidAscii.append(String.format("%02d", messageCount));
                        int messageCountloop = messageCount;
                        while (messageCountloop > 0) {
                            for (int i = 0; i < messageCount; i++) {
                                // Variable Name
                                tempMidAscii.append(s.toString(), 0, 20);
                                // Variable Type
                                tempMidAscii.append(s.toString(), 20, 22);
                                // Value
                                tempMidAscii.append(s.toString(), 22, 29);
                            }
                            messageCountloop -= 1;
                        }
                        tempMidAscii.append(s);
                    } else {
                        tempMidAscii.append(s);
                    }
                }
                dFCount += 1;
            }
        }
        // Check Size
        int midLength = 4 + tempMidAscii.length(); // length of message
        // combine string
        // length
        midAscii.append(String.format("%04d", midLength));
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
            int[] numberOfMessagesIdxByteRange = {22, 24};
            int[] messageNumberIdxByteRange = {26, 28};
            int[] dataNumberSystemIdxByteRange = {30, 40};
            int[] stationNumberIdxByteRange = {42, 44};
            int[] timeIdxByteRange = {46, 55};
            //
            controllerHash.put("TOTAL_NO_OF_MESSAGES", controllerMsg.substring(numberOfMessagesIdxByteRange[0], numberOfMessagesIdxByteRange[1]));
            controllerHash.put("MESSAGE_NUMBER", controllerMsg.substring(messageNumberIdxByteRange[0], messageNumberIdxByteRange[1]));
            controllerHash.put("DATA_NO_SYSTEM", controllerMsg.substring(dataNumberSystemIdxByteRange[0], dataNumberSystemIdxByteRange[1]));
            controllerHash.put("STATION_NUMBER", controllerMsg.substring(stationNumberIdxByteRange[0], stationNumberIdxByteRange[1]));
            controllerHash.put("TIME", controllerMsg.substring(timeIdxByteRange[0], timeIdxByteRange[1]));
            //
            int[] boltNumberIdxByteRange = {57, 61};
            int[] boltNameIdxByteRange = {63, 83};
            int[] programNameIdxByteRange = {85, 105};
            //
            controllerHash.put("BOLT_NUMBER", controllerMsg.substring(boltNumberIdxByteRange[0], boltNumberIdxByteRange[1]));
            controllerHash.put("BOLT_NAME", controllerMsg.substring(boltNameIdxByteRange[0], boltNameIdxByteRange[1]));
            controllerHash.put("PROGRAM_NAME", controllerMsg.substring(programNameIdxByteRange[0], programNameIdxByteRange[1]));
            //
            int[] pmStatusIdIdxByteRange = {107, 108};
            String[] pmStatusArray = {"OK", "OKR", "NOK", "TERMNOK"};
            String pmStatusValue = controllerMsg.substring(pmStatusIdIdxByteRange[0], pmStatusIdIdxByteRange[1]);
            switch (pmStatusValue) {
                case "0":
                    controllerHash.put("PM_STATUS", pmStatusArray[0]);
                    break;
                case "1":
                    controllerHash.put("PM_STATUS", pmStatusArray[1]);
                    break;
                case "2":
                    controllerHash.put("PM_STATUS", pmStatusArray[2]);
                    break;
                default:
                    controllerHash.put("PM_STATUS", pmStatusArray[3]);
                    break;
            }
            //
            int[] errorCodeIdxByteRange = {110, 160};
            int[] customerErrorCodeIdxByteRange = {162, 166};
            controllerHash.put("ERROR_CODE", controllerMsg.substring(errorCodeIdxByteRange[0], errorCodeIdxByteRange[1]));
            controllerHash.put("CUSTOMER_ERROR_CODE", controllerMsg.substring(customerErrorCodeIdxByteRange[0], customerErrorCodeIdxByteRange[1]));
            //
            int[] numberOfBoltsIdxByteRange = {168, 170};
            String numberOfBoltsString = controllerMsg.substring(numberOfBoltsIdxByteRange[0], numberOfBoltsIdxByteRange[1]);
            int numberOfBoltsInteger = Integer.parseInt(numberOfBoltsString);
            controllerHash.put("NUMBER_OF_BOLTS", numberOfBoltsInteger);
            //
            int boltResultIdxByteRange = 170;
            //
            for (int b=0; b < numberOfBoltsInteger; b++) {
                //
                HashMap<String, String> tempHash = new HashMap<>();
                //
                String boltVariableName = controllerMsg.substring(boltResultIdxByteRange + b*29, boltResultIdxByteRange + b*29 + 20);
    //            tempHash.put("variableName", boltVariableName);
                tempHash.put("VARIABLE_NAME", boltVariableName);
                //
                String boltVariableType = controllerMsg.substring(boltResultIdxByteRange + b*29 + 20, boltResultIdxByteRange + b*29 + 22);
    //            tempHash.put("variableType", boltVariableType);
                tempHash.put("VARIABLE_TYPE", boltVariableType);
                //
                String boltVariableValue = controllerMsg.substring(boltResultIdxByteRange + b*29 + 22, boltResultIdxByteRange + b*29 + 29);
    //            tempHash.put("variableValue", boltVariableValue);
                tempHash.put("VARIABLE_VALUE", boltVariableValue);
                //
    //            controllerHash.put("BOLT_" + (b + 1) + "_RESULT", tempHash);
                controllerHash.put("BOLT_RESULT" + (b + 1), tempHash);
            }
            //
            int numberOfStepResultsIdxByteStart = boltResultIdxByteRange * numberOfBoltsInteger * 29 + 2;
            int[] numberOfStepResultsIdxByteRange = {numberOfStepResultsIdxByteStart, numberOfStepResultsIdxByteStart + 3};
            String numberOfStepResultsString = controllerMsg.substring(numberOfStepResultsIdxByteRange[0], numberOfStepResultsIdxByteRange[1]);
            int numberOfStepResultsInteger = Integer.parseInt(numberOfStepResultsString);
    //        controllerHash.put("NUMBER_OF_STEP_RESULTS", numberOfStepResultsString);
            controllerHash.put("NUMBER_OF_STEP_RESULTS", numberOfStepResultsInteger);
            //
            for (int s=0; s < numberOfStepResultsInteger; s++) {
                //
                HashMap<String, String> tempHash = new HashMap<>();
                //
                int[] allStepDataSent = {numberOfStepResultsIdxByteStart + s*37 + 5, numberOfStepResultsIdxByteStart + s*37 + 6};
                String allStepDataSentValue = controllerMsg.substring(allStepDataSent[0], allStepDataSent[1]);
                if (allStepDataSentValue.equals("0")) {
    //                tempHash.put("allStepData", "FALSE");
                    tempHash.put("ALL_STEP_DATA", "FALSE");
                } else {
    //                tempHash.put("allStepData", "TRUE");
                    tempHash.put("ALL_STEP_DATA", "TRUE");
                }
                String variableName = controllerMsg.substring(numberOfStepResultsIdxByteStart + s*37 + 6, numberOfStepResultsIdxByteStart + s*37 + 26);
    //            tempHash.put("variableName", variableName);
                tempHash.put("VARIABLE_NAME", variableName);
                String variableType = controllerMsg.substring(numberOfStepResultsIdxByteStart + s*37 + 26, numberOfStepResultsIdxByteStart + s*37 + 28);
    //            tempHash.put("variableType", variableType);
                tempHash.put("VARIABLE_TYPE", variableType);
                String variableValue = controllerMsg.substring(numberOfStepResultsIdxByteStart + s*37 + 28, numberOfStepResultsIdxByteStart + s*37 + 35);
    //            tempHash.put("variableValue", variableValue);
                tempHash.put("VARIABLE_VALUE", variableValue);
                String variableStepNumber = controllerMsg.substring(numberOfStepResultsIdxByteStart + s*37 + 35, numberOfStepResultsIdxByteStart + s*37 + 37);
    //            tempHash.put("variableStepNumber", variableStepNumber);
                tempHash.put("VARIABLE_STEP_NUMBER", variableStepNumber);
                //
                controllerHash.put("STEP_RESULT" + (s + 1), tempHash);
            }
            ////
            //
            int numberOfSpecialValuesIdxByteStart = numberOfStepResultsIdxByteStart + numberOfStepResultsInteger * 37 + 2;
            int[] numberOfSpecialValuesIdxByteRange = {numberOfSpecialValuesIdxByteStart, numberOfSpecialValuesIdxByteStart + 2};
            String numberOfSpecialValuesString = controllerMsg.substring(numberOfSpecialValuesIdxByteRange[0], numberOfSpecialValuesIdxByteRange[1]);
            int numberOfSpecialValuesInteger = Integer.parseInt(numberOfSpecialValuesString);
    //        controllerHash.put("NUMBER_OF_SPECIAL_VALUES", numberOfSpecialValuesString);
            controllerHash.put("NUMBER_OF_SPECIAL_VALUES", numberOfSpecialValuesInteger);
            //
            int sVC = 0;
            for (int s=0; s < numberOfSpecialValuesInteger; s++) {
                //
                HashMap<String, String> tempHash = new HashMap<>();
                //
                if (s == 0) {
                    String specialValueName = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + 2, numberOfSpecialValuesIdxByteStart + 22);
    //                tempHash.put("variableName", specialValueName);
                    tempHash.put("VALUE_NAME", specialValueName);
                    String specialValueType = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + 22, numberOfSpecialValuesIdxByteStart + 24);
    //                tempHash.put("specialValueType", specialValueType);
                    tempHash.put("VALUE_TYPE", specialValueType);
                    String specialValueLength = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + 24, numberOfSpecialValuesIdxByteStart + 26);
    //                tempHash.put("specialValueLength", specialValueLength);
                    tempHash.put("VALUE_LENGTH", specialValueLength);
                    int specialValueLengthInteger = Integer.parseInt(specialValueLength);
                    String specialValue = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + 26, numberOfSpecialValuesIdxByteStart + 26 + specialValueLengthInteger);
    //                tempHash.put("specialValue", specialValue);
                    tempHash.put("VALUE", specialValue);
                    String variableStepNumber = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + 26 + specialValueLengthInteger, numberOfSpecialValuesIdxByteStart + 26 + specialValueLengthInteger + 2);
    //                tempHash.put("variableStepNumber", variableStepNumber);
                    tempHash.put("STEP_NUMBER", variableStepNumber);
                    sVC += specialValueLengthInteger + 28;
                } else {
                    String specialValueName = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + sVC + 2, numberOfSpecialValuesIdxByteStart + sVC + 22);
    //                tempHash.put("variableName", specialValueName);
                    tempHash.put("VALUE_NAME", specialValueName);
                    String specialValueType = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + sVC + 22, numberOfSpecialValuesIdxByteStart + sVC + 24);
    //                tempHash.put("specialValueType", specialValueType);
                    tempHash.put("VALUE_TYPE", specialValueType);
                    String specialValueLength = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + sVC + 24, numberOfSpecialValuesIdxByteStart + sVC + 26);
    //                tempHash.put("specialValueLength", specialValueLength);
                    tempHash.put("VALUE_LENGTH", specialValueLength);
                    int specialValueLengthInteger = Integer.parseInt(specialValueLength);
                    String specialValue = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + sVC + 26, numberOfSpecialValuesIdxByteStart + sVC + 26 + specialValueLengthInteger);
    //                tempHash.put("specialValue", specialValue);
                    tempHash.put("VALUE", specialValue);
                    String variableStepNumber = controllerMsg.substring(numberOfSpecialValuesIdxByteStart + sVC + 26 + specialValueLengthInteger, numberOfSpecialValuesIdxByteStart + sVC + 26 + specialValueLengthInteger + 2);
    //                tempHash.put("variableStepNumber", variableStepNumber);
                    tempHash.put("STEP_NUMBER", variableStepNumber);
                    sVC += specialValueLengthInteger + 28;
                }
                //
                controllerHash.put("SPECIAL_VALUE_" + (s + 1), tempHash);
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

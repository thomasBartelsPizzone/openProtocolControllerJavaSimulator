package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0106 {
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
    // MID 0106 Last PowerMACS tightening result Station data
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        // String midLengthString = "0020";
        String midLengthString = null;
        String midRevision = "001";
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
            int dFCount = 1;
            for (Object s : dataFieldValue) {
                tempMidAscii.append(String.format("%02d", dFCount));
                tempMidAscii.append(s);
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
            int[] stationNameIdxByteRange = {46, 66};
            int[] timeIdxByteRange = {68, 87};
            int[] modeNumberIdIdxByteRange = {89, 91};
            int[] modeNameIdxByteRange = {93, 113};
            int[] simpleStatusIdxByteRange = {115, 116};
            //
            controllerHash.put("TOTAL_NO_OF_MESSAGES", controllerMsg.substring(numberOfMessagesIdxByteRange[0], numberOfMessagesIdxByteRange[1]));
            controllerHash.put("MESSAGE_NUMBER", controllerMsg.substring(messageNumberIdxByteRange[0], messageNumberIdxByteRange[1]));
            controllerHash.put("DATA_NO_SYSTEM", controllerMsg.substring(dataNumberSystemIdxByteRange[0], dataNumberSystemIdxByteRange[1]));
            controllerHash.put("STATION_NUMBER", controllerMsg.substring(stationNumberIdxByteRange[0], stationNumberIdxByteRange[1]));
            controllerHash.put("STATION_NAME", controllerMsg.substring(stationNameIdxByteRange[0], stationNameIdxByteRange[1]));
            controllerHash.put("TIME", controllerMsg.substring(timeIdxByteRange[0], timeIdxByteRange[1]));
            controllerHash.put("MODE_NUMBER", controllerMsg.substring(modeNumberIdIdxByteRange[0], modeNumberIdIdxByteRange[1]));
            controllerHash.put("MODE_NAME", controllerMsg.substring(modeNameIdxByteRange[0], modeNameIdxByteRange[1]));
            controllerHash.put("SIMPLE_STATUS", controllerMsg.substring(simpleStatusIdxByteRange[0], simpleStatusIdxByteRange[1]));
            //
            int[] pmStatusIdIdxByteRange = {118, 119};
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
            int[] wpIdIdxByteRange = {121, 161};
            controllerHash.put("Wp. Id", controllerMsg.substring(wpIdIdxByteRange[0], wpIdIdxByteRange[1]));
            //
            int[] numberOfBoltsIdxByteRange = {163, 165};
            String numberOfBoltsString = controllerMsg.substring(numberOfBoltsIdxByteRange[0], numberOfBoltsIdxByteRange[1]);
            int numberOfBoltsInteger = Integer.parseInt(numberOfBoltsString);
            controllerHash.put("NUMBER_OF_BOLTS", numberOfBoltsInteger);
            //
            int boltPartIdxByteRange = 165;
            //
            for (int b=0; b < numberOfBoltsInteger; b++) {
                //
                HashMap<String, String> tempHash = new HashMap<>();
                //
    //            String ordinalBoltNumber = "ordinalBoltNumber";
    //            String ordinalBoltNumber = "ORDINAL_BOLT_NUMBER";
                String ordinalBoltNumber = "ORDINAL_NUMBER";
                String boltNumberValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 2, boltPartIdxByteRange + b*68 + 4);
                tempHash.put(ordinalBoltNumber, boltNumberValue);
                //
    //            String simpleBoltStatus = "simpleBoltStatus";
    //            String simpleBoltStatus = "SIMPLE_BOLT_STATUS";
                String simpleBoltStatus = "SIMPLE_STATUS";
                String[] simpleBoltStatusArray = {"TIGHTENING_NOK", "TIGHTENING_OK"};
                String simpleBoltStatusValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 6, boltPartIdxByteRange + b*68 + 7);
                if (simpleBoltStatusValue.equals("0")) {
                    tempHash.put(simpleBoltStatus, simpleBoltStatusArray[0]);
                } else {
                    tempHash.put(simpleBoltStatus, simpleBoltStatusArray[1]);
                }
                //
    //            String boltTorqueStatus = "boltTorqueStatus";
                String boltTorqueStatus = "TORQUE_STATUS";
                String[] boltTorqueStatusArray = {"BOLT_T_LOW", "BOLT_T_OK", "BOLT_T_HIGH"};
                String boltTorqueStatusValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 9, boltPartIdxByteRange + b*68 + 10);
                if (simpleBoltStatusValue.equals("0")) {
                    tempHash.put(boltTorqueStatus, boltTorqueStatusArray[0]);
                } else if (boltTorqueStatusValue.equals("1")) {
                    tempHash.put(boltTorqueStatus, boltTorqueStatusArray[1]);
                } else {
                    tempHash.put(boltTorqueStatus, boltTorqueStatusArray[1]);
                }
                //
    //            String boltAngleStatus = "boltAngleStatus";
                String boltAngleStatus = "ANGLE_STATUS";
                String[] boltAngleStatusArray = {"BOLT_A_LOW", "BOLT_A_OK", "BOLT_A_HIGH"};
                String boltAngleStatusValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 12, boltPartIdxByteRange + b*68 + 13);
                if (boltAngleStatusValue.equals("0")) {
                    tempHash.put(boltAngleStatus, boltAngleStatusArray[0]);
                } else if (boltTorqueStatusValue.equals("1")) {
                    tempHash.put(boltAngleStatus, boltAngleStatusArray[1]);
                } else {
                    tempHash.put(boltAngleStatus, boltAngleStatusArray[1]);
                }
                ////
    //            String boltT = "boltT";
                String boltT = "BOLT_T";
                String boltTValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 15, boltPartIdxByteRange + b*68 + 22);
                tempHash.put(boltT, boltTValue);
    //            String boltA = "boltA";
                String boltA = "BOLT_A";
                String boltAValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 24, boltPartIdxByteRange + b*68 + 31);
                tempHash.put(boltA, boltAValue);
                ////
                String boltTHLValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 33, boltPartIdxByteRange + b*68 + 40);
    //            tempHash.put("boltTHighLimit", boltTHLValue);
                tempHash.put("BOLT_T_HIGH_LIMIT", boltTHLValue);
                String boltTLLValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 42, boltPartIdxByteRange + b*68 + 49);
    //            tempHash.put("boltTHowLimit", boltTLLValue);
                tempHash.put("BOLT_T_LOW_LIMIT", boltTLLValue);
                //
                String boltAHLValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 52, boltPartIdxByteRange + b*68 + 59);
    //            tempHash.put("boltTHighLimit", boltAHLValue);
                tempHash.put("BOLT_A_HIGH_LIMIT", boltAHLValue);
                String boltALLValue = controllerMsg.substring(boltPartIdxByteRange + b*68 + 61, boltPartIdxByteRange + b*68 + 68);
    //            tempHash.put("boltALowLimit", boltALLValue);
                tempHash.put("BOLT_A_LOW_LIMIT", boltALLValue);
                //
                //
                controllerHash.put("BOLT_" + (b + 1) + "_STATUS", tempHash);
            }
            //
            int numberOfSpecialValuesIdxByteStart = boltPartIdxByteRange * numberOfBoltsInteger * 68 + 2;
            int[] numberOfSpecialValuesIdxByteRange = {numberOfSpecialValuesIdxByteStart, numberOfSpecialValuesIdxByteStart + 2};
            String numberOfSpecialValuesString = controllerMsg.substring(numberOfSpecialValuesIdxByteRange[0], numberOfSpecialValuesIdxByteRange[1]);
            int numberOfSpecialValuesInteger = Integer.parseInt(numberOfSpecialValuesString);
            controllerHash.put("NUMBER_OF_SPECIAL_VALUES", numberOfSpecialValuesString);
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
                    sVC += specialValueLengthInteger + 26;
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
                    sVC += specialValueLengthInteger + 26;
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
        //
        midControllerHash.put(midCommand, controllerHash);
        return midControllerHash;
    }
}

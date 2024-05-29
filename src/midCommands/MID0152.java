package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0152 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0153";
        } else {
            return null;
        }
    }
    //
    // MID 0153 Multiple identifiers work order acknowledge
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = "0148";
        String midRevision = "001";
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
            int dfCount = 1;
            for (Object s : dataFieldValue) {
                tempMidAscii.append(String.format("%02d", dfCount));
                tempMidAscii.append(s);
                dfCount += 1;
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
            int[] ackFlagIdxByteRange = {11, 12};
            controllerHash.put("ACK_FLAG", controllerMsg.substring(ackFlagIdxByteRange[0], ackFlagIdxByteRange[1]));
            ////
            int[] workOrderFirstIdentifierTypeNumberByteRange = {22, 23};
            controllerHash.put("WORK_ORDER_FIRST_ID_TYPE_NUMBER", controllerMsg.substring(workOrderFirstIdentifierTypeNumberByteRange[0], workOrderFirstIdentifierTypeNumberByteRange[1]));

            int[] workOrderFirstIdentifierIncludedByteRange = {23, 25};
            String workOrderFirstIdentifierIncludedValue = controllerMsg.substring(workOrderFirstIdentifierIncludedByteRange[0], workOrderFirstIdentifierIncludedByteRange[1]);
            if (workOrderFirstIdentifierIncludedValue.equals("00")) {
                controllerHash.put("WORK_ORDER_FIRST_ID_INCLUDED", "No");
            } else {
                controllerHash.put("WORK_ORDER_FIRST_ID_INCLUDED", "Yes");
            }

            int[] workOrderFirstIdentifierStatusByteRange = {25, 27};
            String workOrderFirstIdentifierStatusValue = controllerMsg.substring(workOrderFirstIdentifierStatusByteRange[0], workOrderFirstIdentifierStatusByteRange[1]);
            switch (workOrderFirstIdentifierStatusValue) {
                case "00":
                    controllerHash.put("WORK_ORDER_FIRST_ID_INCLUDED", "Not_Accepted");
                    break;
                case "01":
                    controllerHash.put("WORK_ORDER_FIRST_ID_INCLUDED", "Accepted");
                    break;
                case "02":
                    controllerHash.put("WORK_ORDER_FIRST_ID_INCLUDED", "Bypassed");
                    break;
                default:
                    controllerHash.put("WORK_ORDER_FIRST_ID_INCLUDED", "Reset");
                    break;
            }

            int[] workOrderFirstIdentifierByteRange = {27, 52};
            controllerHash.put("WORK_ORDER_FIRST_ID", controllerMsg.substring(workOrderFirstIdentifierByteRange[0], workOrderFirstIdentifierByteRange[1]));
            ////
            ////
            int[] workOrderSecondIdentifierTypeNumberByteRange = {54, 55};
            controllerHash.put("WORK_ORDER_SECOND_ID_TYPE_NUMBER", controllerMsg.substring(workOrderSecondIdentifierTypeNumberByteRange[0], workOrderSecondIdentifierTypeNumberByteRange[1]));

            int[] workOrderSecondIdentifierIncludedByteRange = {55, 57};
            String workOrderSecondIdentifierIncludedValue = controllerMsg.substring(workOrderSecondIdentifierIncludedByteRange[0], workOrderSecondIdentifierIncludedByteRange[1]);
            if (workOrderSecondIdentifierIncludedValue.equals("00")) {
                controllerHash.put("WORK_ORDER_SECOND_ID_INCLUDED", "No");
            } else {
                controllerHash.put("WORK_ORDER_SECOND_ID_INCLUDED", "Yes");
            }

            int[] workOrderSecondIdentifierStatusByteRange = {57, 59};
            String workOrderSecondIdentifierStatusValue = controllerMsg.substring(workOrderSecondIdentifierStatusByteRange[0], workOrderSecondIdentifierStatusByteRange[1]);
            switch (workOrderSecondIdentifierStatusValue) {
                case "00":
                    controllerHash.put("WORK_ORDER_SECOND_ID_INCLUDED", "Not_Accepted");
                    break;
                case "01":
                    controllerHash.put("WORK_ORDER_SECOND_ID_INCLUDED", "Accepted");
                    break;
                case "02":
                    controllerHash.put("WORK_ORDER_SECOND_ID_INCLUDED", "Bypassed");
                    break;
                default:
                    controllerHash.put("WORK_ORDER_SECOND_ID_INCLUDED", "Reset");
                    break;
            }

            int[] workOrderSecondIdentifierByteRange = {59, 84};
            controllerHash.put("WORK_ORDER_SECOND_ID", controllerMsg.substring(workOrderSecondIdentifierByteRange[0], workOrderSecondIdentifierByteRange[1]));
            ////
            ////
            int[] workOrderThirdIdentifierTypeNumberByteRange = {86, 87};
            controllerHash.put("WORK_ORDER_THIRD_ID_TYPE_NUMBER", controllerMsg.substring(workOrderThirdIdentifierTypeNumberByteRange[0], workOrderThirdIdentifierTypeNumberByteRange[1]));

            int[] workOrderThirdIdentifierIncludedByteRange = {87, 89};
            String workOrderThirdIdentifierIncludedValue = controllerMsg.substring(workOrderThirdIdentifierIncludedByteRange[0], workOrderThirdIdentifierIncludedByteRange[1]);
            if (workOrderThirdIdentifierIncludedValue.equals("00")) {
                controllerHash.put("WORK_ORDER_THIRD_ID_INCLUDED", "No");
            } else {
                controllerHash.put("WORK_ORDER_THIRD_ID_INCLUDED", "Yes");
            }

            int[] workOrderThirdIdentifierStatusByteRange = {89, 91};
            String workOrderThirdIdentifierStatusValue = controllerMsg.substring(workOrderThirdIdentifierStatusByteRange[0], workOrderThirdIdentifierStatusByteRange[1]);
            switch (workOrderThirdIdentifierStatusValue) {
                case "00":
                    controllerHash.put("WORK_ORDER_THIRD_ID_INCLUDED", "Not_Accepted");
                    break;
                case "01":
                    controllerHash.put("WORK_ORDER_THIRD_ID_INCLUDED", "Accepted");
                    break;
                case "02":
                    controllerHash.put("WORK_ORDER_THIRD_ID_INCLUDED", "Bypassed");
                    break;
                default:
                    controllerHash.put("WORK_ORDER_THIRD_ID_INCLUDED", "Reset");
                    break;
            }

            int[] workOrderThirdIdentifierByteRange = {91, 116};
            controllerHash.put("WORK_ORDER_THIRD_ID", controllerMsg.substring(workOrderThirdIdentifierByteRange[0], workOrderThirdIdentifierByteRange[1]));
            ////
            ////
            int[] workOrderFourthIdentifierTypeNumberByteRange = {118, 119};
            controllerHash.put("WORK_ORDER_FOURTH_ID_TYPE_NUMBER", controllerMsg.substring(workOrderFourthIdentifierTypeNumberByteRange[0], workOrderFourthIdentifierTypeNumberByteRange[1]));

            int[] workOrderFourthIdentifierIncludedByteRange = {119, 121};
            String workOrderFourthIdentifierIncludedValue = controllerMsg.substring(workOrderFourthIdentifierIncludedByteRange[0], workOrderFourthIdentifierIncludedByteRange[1]);
            if (workOrderFourthIdentifierIncludedValue.equals("00")) {
                controllerHash.put("WORK_ORDER_FOURTH_ID_INCLUDED", "No");
            } else {
                controllerHash.put("WORK_ORDER_FOURTH_ID_INCLUDED", "Yes");
            }

            int[] workOrderFourthIdentifierStatusByteRange = {121, 123};
            String workOrderFourthIdentifierStatusValue = controllerMsg.substring(workOrderFourthIdentifierStatusByteRange[0], workOrderFourthIdentifierStatusByteRange[1]);
            switch (workOrderFourthIdentifierStatusValue) {
                case "00":
                    controllerHash.put("WORK_ORDER_FOURTH_ID_INCLUDED", "Not_Accepted");
                    break;
                case "01":
                    controllerHash.put("WORK_ORDER_FOURTH_ID_INCLUDED", "Accepted");
                    break;
                case "02":
                    controllerHash.put("WORK_ORDER_FOURTH_ID_INCLUDED", "Bypassed");
                    break;
                default:
                    controllerHash.put("WORK_ORDER_FOURTH_ID_INCLUDED", "Reset");
                    break;
            }

            int[] workOrderFourthIdentifierByteRange = {123, 148};
            controllerHash.put("WORK_ORDER_FOURTH_ID", controllerMsg.substring(workOrderFourthIdentifierByteRange[0], workOrderFourthIdentifierByteRange[1]));
            ////
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

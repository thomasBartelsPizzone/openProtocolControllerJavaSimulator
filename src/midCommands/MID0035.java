package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0035 {
    //
    public String replyMID(String answer) {
        if (answer.equalsIgnoreCase("acknowledge")) {
            return "midCommands.MID0036";
        } else if (answer.equalsIgnoreCase("accept")) {
            return "midCommands.MID0036";
        } else {
            return null;
        }
    }
    //
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midLengthString = "0063";
        String midRevision = "001";
        // String midAckFlag = "0";
        String midAckFlag = "1";
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
            int dFCount = 1;
            for (Object s : dataFieldValue) {
                midAscii.append(String.format("%02d", dFCount));
                //if (s instanceof Integer) {
                if (opM.isNumeric(s)) {
                    if (dFCount == 1) {
                        //midAscii.append(String.format("%02d", s));
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else if (dFCount > 1 && dFCount < 4) {
                        midAscii.append(s);
                    } else if (dFCount == 4 || dFCount == 5) {
                        //midAscii.append(String.format("%04d", s));
                        midAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                    } else {
                        midAscii.append(s);
                    }
                } else {
                    midAscii.append(s);
                }
                dFCount += 1;
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
            //
            int[] jobIDIdxByteRange = {22, 24};
            //
            int[] jobStatusIdxByteRange = {26, 27};
            //
            int[] jobBatchModeIdxByteRange = {29, 30};
            //
            int[] jobBatchSizeIdxByteRange = {32, 36};
            //
            int[] jobBatchCounterIdxByteRange = {38, 42};
            //
            int[] timeStampIdxByteRange = {44, 63};
            //
            controllerHash.put("JOB_ID", controllerMsg.substring(jobIDIdxByteRange[0], jobIDIdxByteRange[1]));
            String[] jobStatusArray = {"JOB_INCOMPLETE", "JOB_OK", "JOB_NOK"};
            String jobStatusValue = controllerMsg.substring(jobStatusIdxByteRange[0], jobStatusIdxByteRange[1]);
            if (jobStatusValue.equals("0")) {
                controllerHash.put("JOB_STATUS", jobStatusArray[0]);
            } else if (jobStatusValue.equals("1")) {
                controllerHash.put("JOB_STATUS", jobStatusArray[1]);
            } else {
                controllerHash.put("JOB_STATUS", jobStatusArray[2]);
            }
            String[] jobBatchModeArray = {"TIGHTENING_OK_COUNTED", "TIGHTENING_OK_NOK_COUNTED"};
            String jobBatchModeValue = controllerMsg.substring(jobBatchModeIdxByteRange[0], jobBatchModeIdxByteRange[1]);
            if (jobBatchModeValue.equals("0")) {
                controllerHash.put("JOB_BATCH_MODE", jobBatchModeArray[0]);
            } else {
                controllerHash.put("JOB_BATCH_MODE", jobBatchModeArray[1]);
            }
            //
            String jobBatchSizeString = controllerMsg.substring(jobBatchSizeIdxByteRange[0], jobBatchSizeIdxByteRange[1]);
            int jobBatchSizeInteger = Integer.parseInt(jobBatchSizeString);
            controllerHash.put("JOB_BATCH_SIZE", jobBatchSizeInteger);

            String jobBatchCounterString = controllerMsg.substring(jobBatchCounterIdxByteRange[0], jobBatchCounterIdxByteRange[1]);
            int jobBatchCounterInteger = Integer.parseInt(jobBatchCounterString);
            controllerHash.put("JOB_BATCH_COUNTER", jobBatchCounterInteger);
            //
            controllerHash.put("TIMESTAMP", controllerMsg.substring(timeStampIdxByteRange[0], timeStampIdxByteRange[1]));
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
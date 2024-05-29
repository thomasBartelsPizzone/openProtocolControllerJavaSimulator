package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0031 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
    //
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
        String midRevision = "002";
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
            for (Object s : dataFieldValue) {
                if (opM.isNumeric(s)) {
                    //if (s instanceof Integer) {
                    //tempMidAscii.append(String.format("%04d", s));
                    tempMidAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                } else {
                    tempMidAscii.append(s);
                }
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
                // Datafield
                int[] numberOfJobsIdxByteRange = {20, 22};
                String numberOfJobsString = controllerMsg.substring(numberOfJobsIdxByteRange[0], numberOfJobsIdxByteRange[1]);
                //
                int numberOfJobsInteger = Integer.parseInt(numberOfJobsString);
    //            controllerHash.put("NUMBER_OF_JOBS", numberOfJobsString);
                controllerHash.put("NUMBER_OF_JOBS", numberOfJobsInteger);
                //
                for (int p=0; p < numberOfJobsInteger; p++) {
                    String jobID = "JOB_ID" + (p + 1);
                    String jobValue = controllerMsg.substring(22 + p*2, 22 + p*2 + 2);
                    controllerHash.put(jobID, jobValue);

                }
            } else {
                // Datafield
                int[] numberOfJobsIdxByteRange = {20, 24};
                String numberOfJobsString = controllerMsg.substring(numberOfJobsIdxByteRange[0], numberOfJobsIdxByteRange[1]);
                //
                int numberOfJobsInteger = Integer.parseInt(numberOfJobsString);
    //            controllerHash.put("NUMBER_OF_JOBS", numberOfJobsString);
                controllerHash.put("NUMBER_OF_JOBS", numberOfJobsInteger);
                //
                for (int p=0; p < numberOfJobsInteger; p++) {
                    String jobID = "JOB_ID" + (p + 1);
                    String jobValue = controllerMsg.substring(24 + p*4, 24 + p*4 + 4);
                    controllerHash.put(jobID, jobValue);
                }
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
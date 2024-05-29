package midCommands;

import java.util.HashMap;
import java.util.List;


public class MID0002 {
    //
    public String replyMID(String answer) {
        return null;
    }
    //
    // MID 0002 Communication start acknowledge
    public String integratorString(String midCommandValue, List<Object> dataFieldValue) {
        //
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
                    } else if (dFCount == 3) {
                        tempMidAscii.append(String.format("%25d", Integer.parseInt(s.toString())));
                    } else if (dFCount == 4) {
                        tempMidAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                    } else if (dFCount > 4) {
                        tempMidAscii.append(String.format("%19d", Integer.parseInt(s.toString())));
                    } else {
                        tempMidAscii.append(s);
                    }
                } else {
                    tempMidAscii.append(s);
                }
                dFCount += 1;
            }
        }
        // Length
        int midLength = 4 + tempMidAscii.length() + 4 + 3; // add command length, message length and revision value length
        // add command
        midAscii.append(midCommandValue);
        // combine string
        // append length
        midAscii.append(String.format("%04d", midLength));
        // append revision
        if (midLength == 57) {
            midAscii.append(String.format("%03d", 57));
        } else if (midLength == 62) {
            midAscii.append(String.format("%03d", 62));
            midAscii.append(tempMidAscii);
        } else if (midLength == 125) {
            midAscii.append(String.format("%03d", 125));
            midAscii.append(tempMidAscii);
        }
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
            int midRevisionInt = Integer.parseInt(midRevision);
            int[] cellIdIdxByteRange = {22, 26};
            int[] channelIdIdxByteRange = {28, 30};
            int[] controllerIdIdxByteRange = {32, 57};
            //
            controllerHash.put("CELL_ID", controllerMsg.substring(cellIdIdxByteRange[0], cellIdIdxByteRange[1]));
            controllerHash.put("CHANNEL_ID", controllerMsg.substring(channelIdIdxByteRange[0], channelIdIdxByteRange[1]));
            controllerHash.put("CONTROLLER_ID", controllerMsg.substring(controllerIdIdxByteRange[0], controllerIdIdxByteRange[1]));
            //
            if (midRevisionInt > 1) {
                //
                int[] supplierCodeIdxByteRange = {59, 62};
                controllerHash.put("SUPPLIER_CODE", controllerMsg.substring(supplierCodeIdxByteRange[0], supplierCodeIdxByteRange[1]));
                //
                if (midRevisionInt > 2) {

                    int[] oPVersionIdxByteRange = {64, 83};
                    int[] controllerSWIdxByteRange = {85, 104};
                    int[] toolSWVersionIdxByteRange = {106, 125};
                    //
                    controllerHash.put("OPEN_PROTOCOL_VERSION", controllerMsg.substring(oPVersionIdxByteRange[0], oPVersionIdxByteRange[1]));
                    controllerHash.put("CONTROLLER_SW_VERSION", controllerMsg.substring(controllerSWIdxByteRange[0], controllerSWIdxByteRange[1]));
                    controllerHash.put("TOOL_SW_VERSION", controllerMsg.substring(toolSWVersionIdxByteRange[0], toolSWVersionIdxByteRange[1]));
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
import java.util.HashMap;
import java.util.List;


public class MID0301 {
    //
    String replyMID(String answer) {
        return null;
    }
    //
    // MID 0301 Histogram upload reply
    String integratorString(String midCommandValue, List<Object> dataFieldValue) {
		//
        String midLengthString = "0107";
        String midRevision = "001";
        String midAckFlag = "0";
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
            int dfCount = 1;
            for (Object s : dataFieldValue) {
                midAscii.append(String.format("%02d", dfCount));
                if (opM.isNumeric(s)) {
                    if (dfCount == 1) {
                        midAscii.append(String.format("%03d", Integer.parseInt(s.toString())));
                        //midAscii.append(String.format("%03d", (int) s));
                    } else if (dfCount == 2) {
                        midAscii.append(String.format("%02d", Integer.parseInt(s.toString())));
                    } else if (dfCount > 2 && dfCount < 6) {
                        midAscii.append(new opUtilityMethods(100, 6, s).asciiDigits());
                    } else if (dfCount >= 6) {
                        midAscii.append(String.format("%04d", Integer.parseInt(s.toString())));
                    } else {
                        midAscii.append(s);
                    }
                } else {
                    midAscii.append(s);
                }
                dfCount += 1;
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
    HashMap<String, HashMap<String, Object>> controllerString(String controllerMsg, String midCommand, String midLengthString, String midRevision) {
        //
        HashMap<String, HashMap<String, Object>> midControllerHash = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> controllerHash = new HashMap<String, Object>();
        try {
            //
            int[] parameterSetIDIdxByteRange = {22, 25};
            controllerHash.put("PARAMETER_SET_ID", controllerMsg.substring(parameterSetIDIdxByteRange[0], parameterSetIDIdxByteRange[1]));

            int[] histogramTypeByteRange = {27, 29};
            controllerHash.put("HISTOGRAM_TYPE", controllerMsg.substring(histogramTypeByteRange[0], histogramTypeByteRange[1]));
            //
            int[] sigmaHistogramByteRange = {31, 37};
            String sigmaHistogramStringAscii = controllerMsg.substring(sigmaHistogramByteRange[0], sigmaHistogramByteRange[1]);
            String sigmaHistogramValueString = new opUtilityMethods(100, 6, sigmaHistogramStringAscii).inverseAsciiDigits();
            controllerHash.put("SIGMA_HISTOGRAM", sigmaHistogramValueString);
            //
            int[] meanValueHistogramByteRange = {39, 45};
            String meanValueHistogramStringAscii = controllerMsg.substring(meanValueHistogramByteRange[0], meanValueHistogramByteRange[1]);
            String meanValueHistogramValueString = new opUtilityMethods(100, 6, meanValueHistogramStringAscii).inverseAsciiDigits();
            controllerHash.put("X-BAR", meanValueHistogramValueString);
            //
            int[] classRangeByteRange = {39, 45};
            String classRangeStringAscii = controllerMsg.substring(classRangeByteRange[0], classRangeByteRange[1]);
            String classRangeValueString = new opUtilityMethods(100, 6, classRangeStringAscii).inverseAsciiDigits();
            controllerHash.put("CLASS_RANGE", classRangeValueString);
            //
            int[] bar1ByteRange = {55, 59};
            String bar1StringAscii = controllerMsg.substring(bar1ByteRange[0], bar1ByteRange[1]);
            String bar1ValueString = new opUtilityMethods(1, 4, bar1StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_1", bar1ValueString);

            int[] bar2ByteRange = {61, 65};
            String bar2StringAscii = controllerMsg.substring(bar2ByteRange[0], bar2ByteRange[1]);
            String bar2ValueString = new opUtilityMethods(1, 4, bar2StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_2", bar2ValueString);

            int[] bar3ByteRange = {67, 71};
            String bar3StringAscii = controllerMsg.substring(bar3ByteRange[0], bar3ByteRange[1]);
            String bar3ValueString = new opUtilityMethods(1, 4, bar3StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_3", bar3ValueString);

            int[] bar4ByteRange = {73, 77};
            String bar4StringAscii = controllerMsg.substring(bar4ByteRange[0], bar4ByteRange[1]);
            String bar4ValueString = new opUtilityMethods(1, 4, bar4StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_4", bar4ValueString);

            int[] bar5ByteRange = {79, 83};
            String bar5StringAscii = controllerMsg.substring(bar5ByteRange[0], bar5ByteRange[1]);
            String bar5ValueString = new opUtilityMethods(1, 4, bar5StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_5", bar5ValueString);

            int[] bar6ByteRange = {85, 89};
            String bar6StringAscii = controllerMsg.substring(bar6ByteRange[0], bar6ByteRange[1]);
            String bar6ValueString = new opUtilityMethods(1, 4, bar6StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_6", bar6ValueString);

            int[] bar7ByteRange = {91, 95};
            String bar7StringAscii = controllerMsg.substring(bar7ByteRange[0], bar7ByteRange[1]);
            String bar7ValueString = new opUtilityMethods(1, 4, bar7StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_7", bar7ValueString);

            int[] bar8ByteRange = {97, 101};
            String bar8StringAscii = controllerMsg.substring(bar8ByteRange[0], bar8ByteRange[1]);
            String bar8ValueString = new opUtilityMethods(1, 4, bar8StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_8", bar8ValueString);

            int[] bar9ByteRange = {103, 107};
            String bar9StringAscii = controllerMsg.substring(bar9ByteRange[0], bar9ByteRange[1]);
            String bar9ValueString = new opUtilityMethods(1, 4, bar9StringAscii).inverseAsciiDigits();
            controllerHash.put("BAR_9", bar9ValueString);
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
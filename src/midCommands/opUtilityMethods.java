package midCommands;

import org.apache.commons.lang3.StringUtils;

public class opUtilityMethods {
    String midAscii;
    int msgLength;
    String direction;
    int factor;
    int byteLength;
    Object byteValueString;
    String asciiString;

    public opUtilityMethods (String midAscii, int msgLength, String direction) {
        this.midAscii = midAscii;
        this.msgLength = msgLength;
        this.direction = direction;
    }

    public opUtilityMethods (int factor, int byteLength, Object byteValueString) {
        this.factor = factor;
        this.byteLength = byteLength;
        this.byteValueString = byteValueString;
    }

    public opUtilityMethods (int factor, int byteLength, String asciiString) {
        this.factor = factor;
        this.byteLength = byteLength;
        this.asciiString = asciiString;
    }

    public opUtilityMethods () {
    }

    public String padZeroes() {
        if (direction.equals("left")) {
            return StringUtils.leftPad(midAscii, msgLength, "0");
        } else {
            return StringUtils.rightPad(midAscii, msgLength, "0");
        }
    }

    public String asciiDigits() {
        double convert2Double;
        if (byteValueString instanceof String) {
            convert2Double = Double.parseDouble((String) byteValueString);
        } else {
            convert2Double = (Double) byteValueString;
        }
        int convert2Integer = (int) (convert2Double * factor);
        String convert2StringDigits = String.format("%0" + byteLength + "d", convert2Integer);
        // Convert each digit to its ASCII character
        StringBuilder convert2DigitsSb = new StringBuilder();
        for (char convert2DigitChar : convert2StringDigits.toCharArray()) {
            int convert2DigitValue = Character.getNumericValue(convert2DigitChar);
            convert2DigitsSb.append((char) (convert2DigitValue + 48));
        }
        return convert2DigitsSb.toString();
    }
    //
    public String inverseAsciiDigits() {
        float convertAscii2FloatString = 0;
        for (int i = 0; i < byteLength; i++) {
            char convertAscii2Char = asciiString.charAt(i);
            int convertChar2Int = Character.getNumericValue(convertAscii2Char);
            //
            try {
                convertAscii2FloatString += convertChar2Int * Math.pow(10, 5 - i);
            } catch (Throwable e) {
                System.out.println("ascii to double fail\n" + e.getMessage());
                e.printStackTrace();
            }
        }
        convertAscii2FloatString /= factor;
        return String.valueOf(convertAscii2FloatString);
    }
    //
    public boolean isNumeric(Object obj) {
        try {
            Integer.parseInt(obj.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

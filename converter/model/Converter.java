package converter.model;

public class Converter {

    // Method to convert a decimal number to binary
    public static String decimalToBinary(int decimal) {
        StringBuilder sb = new StringBuilder();

        while (decimal != 0) {
            int reminder = decimal % 2;
            sb.append(reminder);
            decimal /= 2;
        }

        return sb.reverse().toString();
    }

    // Method to convert a decimal number to Octal
    public static String decimalToOctal(int decimal) {
        StringBuilder sb = new StringBuilder();

        while (decimal != 0) {
            int reminder = decimal % 8;
            sb.append(reminder);
            decimal /= 8;
        }
        return sb.reverse().toString();
    }

    // Method to convert a decimal number to hexadecimal
    public static String decimalToHexadecimal(int decimal) {
        String binary = decimalToBinary(decimal); // binary representation of the binary number

        if (binary.length() % 4 != 0) {
            binary = padBinaryForHexConversion(binary);
        }
        return binaryToHexadecimal(binary);
    }

    // Method to pad a binary string-number for Hexadecimal conversion
    private static String padBinaryForHexConversion(String binary) {
        int length = binary.length();
        int paddingNeeded = 4 - length % 4;
        return "0".repeat(paddingNeeded) + binary;
    }

    // Method to convert a binary number to hexadecimal
    private static String binaryToHexadecimal(String binary) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 4) {
            String subBinary = binary.substring(i, i + 4);
            int decimal = binaryToDecimal(subBinary);
            String hex = decimalCharToHexadecimal(decimal);
            sb.append(hex);
        }
        return sb.toString();
    }

    private static String decimalCharToHexadecimal(int decimal) {
        if (decimal < 0 || decimal > 15) {
            return "";
        }
        return Integer.toHexString(decimal).toUpperCase();
    }

    private static int binaryToDecimal(String binary) {

        int length = binary.length(); // total bits on the binary number
        int decimal = 0;  // decimal number

        for (int i = 0; i < length; i++) {
            int exponent = length - 1 - i;

            if (binary.charAt(i) == '1') {
                decimal += (int) Math.pow(2, exponent);
            }
        }
        return decimal;
        // return Integer.parseInt(binary, 2); // using Integer wrapper method
    }

}
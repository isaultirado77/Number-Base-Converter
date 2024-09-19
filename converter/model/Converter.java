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
        final int blocLength = 4; // Length of the binary bloc for conversion
        String binary = decimalToBinary(decimal); // binary representation of the binary number

        if (binary.length() % blocLength != 0) {
            binary = padBinaryForConversion(binary, blocLength);
        }
        return binaryToHexadecimal(binary);
    }

    // Method to pad a binary string-number for Hexadecimal conversion
    private static String padBinaryForConversion(String binary, int blocLength) {
        int length = binary.length();
        int paddingNeeded = blocLength - length % blocLength;
        return "0".repeat(paddingNeeded) + binary;
    }

    public static int binaryToDecimal(String binary) {

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

    // Method to convert a binary number to octal
    public static String binaryToOctal(String binary) {
        final int blocLength = 3; // Length of the binary bloc for conversion

        if (binary.length() % blocLength != 0) {
            binary = padBinaryForConversion(binary, blocLength);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 3) {
            String subBinary = binary.substring(i, i + 3); // get a sub-string of length 3
            int decimal = binaryToDecimal(subBinary); // get the decimal representation of the sub-string
            sb.append(decimal);
        }
        return sb.toString();
    }

    // Method to convert a binary number to hexadecimal
    public static String binaryToHexadecimal(String binary) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 4) {
            String subBinary = binary.substring(i, i + 4);
            int decimal = binaryToDecimal(subBinary);
            String hex = decimalCharToHexadecimal(decimal);
            sb.append(hex);
        }
        return sb.toString();
    }

    // Method to get the Hexadecimal representation of a given decimal number
    private static String decimalCharToHexadecimal(int decimal) {
        if (decimal < 0 || decimal > 15) {
            return "";
        }
        return Integer.toHexString(decimal).toUpperCase();
    }

    public static String octalToDecimal(String octal) {
        int length = octal.length();
        int number = 0;
        for (int i = 0; i < length; i++) {
            int exponent = length - 1 - i;
            int coefficient = octal.charAt(i);
            number += (int) (coefficient * Math.pow(8, exponent));
        }

        return String.valueOf(number);
    }

    public static String hexaToDecimal(String hexa) {
        int length = hexa.length();
        int number = 0;
        for (int i = 0; i < length; i++) {
            int exponent = length - 1 - i;
            int coefficient = (int) hexa.charAt(i);
            number += (int) (coefficient * Math.pow(16, exponent));
        }
        return String.valueOf(number);
    }
}
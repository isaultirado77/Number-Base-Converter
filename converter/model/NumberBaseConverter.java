package converter.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class NumberBaseConverter {

    private static final int PRECISION = 5;

    public static String nonFractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        BigInteger decimal = parseToDecimal(sourceNumber, sourceBase, targetBase);
        if (sourceNumber.equals(BigInteger.ZERO.toString())) return "0";
        else if (targetBase == 10) return decimal.toString();
        else return parseDecimalToTargetBase(decimal, targetBase);
    }

    public static String fractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        String[] splitNumber = sourceNumber.split("\\.");

        if (splitNumber.length > 2) throw new IllegalArgumentException("Invalid input format.");

        String integerPart = splitNumber[0];
        String fractionalPart = splitNumber[1];

        // Convert integer part to decimal base
        integerPart = parseToDecimal(integerPart, sourceBase, targetBase).toString();

        // Convert fractional part to decimal base
        fractionalPart = parseFracToDecimal(fractionalPart, sourceBase);

        System.out.println("A: " + integerPart);
        System.out.println("B: " + fractionalPart);

        return "IMPLEMENT ME";
    }

    // Converts the integer part from source base to decimal (base 10)
    private static BigInteger parseToDecimal(String sourceNumber, int sourceBase, int targetBase) {

        if (isZero(sourceNumber)) return BigInteger.ZERO;

        BigInteger sum = BigInteger.ZERO;
        int length = sourceNumber.length();

        for (int i = 0; i < length; i++) {
            char digitChar = sourceNumber.charAt(i);
            int coefficient;

            // handle valid characters (0-9, A-Z)
            if (Character.isDigit(digitChar)) {
                coefficient = digitChar - '0';
            } else if (Character.isLetter(digitChar)) {
                coefficient = Character.toUpperCase(digitChar) - 'A' + 10;
            } else {
                throw new IllegalArgumentException("Error! Invalid character in source number. ");
            }

            if (coefficient >= sourceBase) {
                String error = String.format("Error! Invalid digit. Source base: %d Target base: %d", sourceBase, targetBase);
                throw new IllegalArgumentException(error);
            }

            int exponent = length - i - 1;
            sum = sum.add(BigInteger.valueOf(coefficient).multiply(BigInteger.valueOf(sourceBase).pow(exponent)));
        }
        return sum;
    }

    // Converts the integer part from decimal (base 10) to the target base
    private static String parseDecimalToTargetBase(BigInteger decimal, int targetBase) {

        if (decimal.compareTo(BigInteger.ZERO) == 0) return "0";

        StringBuilder sb = new StringBuilder();
        BigInteger bigTargetBase = BigInteger.valueOf(targetBase);

        while (!decimal.equals(BigInteger.ZERO)) {
            BigInteger reminder = decimal.remainder(bigTargetBase);
            decimal = decimal.divide(bigTargetBase);

            // Convert the reminder to a character in the target base
            if (reminder.compareTo(BigInteger.TEN) < 0) {
                sb.append(reminder); // the current reminder is 0-9
            } else {
                // Convert 10-35 to A-Z
                sb.append((char) ('A' + reminder.intValue() - 10));
            }
        }
        return sb.reverse().toString();
    }

    // Converts a fractional-part number on any base to decimal (base 10)
    private static String parseFracToDecimal(String frac, int sourceBase) {

        if (isZero(frac)) return "0".repeat(PRECISION);

        // set up variables
        int length = frac.length(); // length of the decimal part
        BigDecimal sum = BigDecimal.ZERO; // init the sum of the digits
        BigDecimal base = BigDecimal.valueOf(sourceBase);

        for (int i = 0; i < length; i++) {
            char charDigit = frac.charAt(i); // current char
            int coefficient = charToInt(charDigit); // current coefficient

            if (coefficient >= sourceBase)
                throw new IllegalArgumentException("Error! Invalid digit for the source base.");

            BigDecimal decimalValue = BigDecimal.valueOf(coefficient).divide(base.pow(i + 1));
            sum = sum.add(decimalValue);
        }
        return sum.toString();
    }

    public static int charToInt(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (Character.toUpperCase(c) >= 'A' && Character.toUpperCase(c) <= 'Z') {
            return c - 'A' + 10;
        } else {
            throw new IllegalArgumentException("Error! Invalid character in fractional part.");
        }
    }

    private static boolean isZero(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }

        try {
            BigDecimal decimalNumber = new BigDecimal(number.trim());
            return decimalNumber.compareTo(BigDecimal.ZERO) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
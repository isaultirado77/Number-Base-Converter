package converter.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberBaseConverter {


    public static String nonFractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        BigInteger decimal = parseToDecimal(sourceNumber, sourceBase, targetBase);
        if (sourceNumber.equals(BigInteger.ZERO.toString())) return sourceNumber;
        else if (targetBase == 10) return decimal.toString();
        else return parseDecimalToTargetBase(decimal, targetBase);
    }

    public static String fractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        String[] splitNumber = sourceNumber.split("\\.");

        if (splitNumber.length > 2) throw new IllegalArgumentException("Invalid input format.");

        String integerPart = splitNumber[0];
        String fractionalPart = splitNumber[1];

        // Convert integer part
        integerPart = nonFractionConversion(integerPart, sourceBase, targetBase);
        //Convert fractional part
        fractionalPart = parseFracPartToTargetBase(fractionalPart, sourceBase, targetBase);

        String decimalString = integerPart + "." + fractionalPart;

        BigDecimal bigDecimal = new BigDecimal(decimalString);

        //return bigDecimal.toString();
        return "cok";
    }

    // Converts the integer part from source base to decimal (base 10)
    private static BigInteger parseToDecimal(String sourceNumber, int sourceBase, int targetBase) {
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

    // Converts the fractional part from source base to decimal
    private static BigDecimal parseFracPartToDecimal(String fracPart, int sourceBase) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal base = BigDecimal.valueOf(sourceBase);

        for (int i = 0; i < fracPart.length(); i++) {
            char digitChar = fracPart.charAt(i);
            int coefficient;

            if (Character.isDigit(digitChar)) {
                coefficient = digitChar - '0';
            } else if (Character.isLetter(digitChar)) {
                coefficient = Character.toUpperCase(digitChar) - 'A' + 10;
            } else {
                throw new IllegalArgumentException("Error! Invalid character in fractional part.");
            }

            if (coefficient >= sourceBase) {
                throw new IllegalArgumentException("Error! Invalid digit for the source base.");
            }
            BigDecimal decimalValue = BigDecimal.valueOf(coefficient).divide(base.pow(i + 1));
            sum = sum.add(decimalValue);
        }
        return sum;
    }

    // Converts the fractional part from source base to decimal
    private static String parseFracPartToTargetBase(String fracPart, int sourceBase, int targetBase) {
        // set up variables
        BigDecimal decimalFraction = parseFracPartToDecimal(fracPart, sourceBase);
        BigDecimal targetBaseDecimal = BigDecimal.valueOf(targetBase);
        StringBuilder sb = new StringBuilder();

        int maxIterations = decimalFraction.toString().length();
        int iteration = 0;
        BigDecimal epsilon = new BigDecimal("0.00001");

        while (decimalFraction.compareTo(BigDecimal.ZERO) != 0 && iteration < maxIterations) {
            decimalFraction = decimalFraction.multiply(targetBaseDecimal);
            int digit = decimalFraction.intValue();
            sb.append(digit);
            decimalFraction = decimalFraction.subtract(BigDecimal.valueOf(digit));

            if (decimalFraction.compareTo(epsilon) < 0) {
                break;
            }
            iteration++;
        }

        return sb.toString();
    }
}

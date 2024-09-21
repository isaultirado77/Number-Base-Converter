package converter.model;

import java.math.BigInteger;

public class NumberBaseConverter {


    public static String nonFractionConversion(String sourceNumber, int sourceBase, int targetBase){

        BigInteger decimal = parseToDecimal(sourceNumber, sourceBase, targetBase);

        if (targetBase == 10) return decimal.toString();
        else return parseDecimalToTargetBase(decimal, targetBase);
    }

    public static String fractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        String[] splitNumber = sourceNumber.split("\\.");

        if( splitNumber.length > 2) throw new IllegalArgumentException("");

        String integerPart = splitNumber[0];
        String fractionalPart = splitNumber[1];

        integerPart = nonFractionConversion(integerPart, sourceBase, targetBase);
        fractionalPart = parseFracPartToTargetBase(fractionalPart, sourceBase, targetBase);
        return integerPart + "\\." + fractionalPart;
    }

    private static BigInteger parseToDecimal(String sourceNumber, int sourceBase, int targetBase) {
        BigInteger sum = BigInteger.ZERO;
        int length = sourceNumber.length();

        for (int i = 0; i < length; i++) {
            char digitChar = sourceNumber.charAt(i);
            int coefficient;

            // handle valid characters (0-0, A-Z)
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

    private static BigInteger parseFracPartToDecimal(String fracPart, int sourceBase, int targetBase) {
        BigInteger sum = BigInteger.ZERO;
        int length = fracPart.length();

        for (int i = 1; i < length; i++) {
            char digitChar = fracPart.charAt(i);
            int coefficient;

            // handle valid characters (0-0, A-Z)
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

            sum = sum.add(BigInteger.valueOf(coefficient).multiply(BigInteger.valueOf(sourceBase).pow(i)));
        }
        return sum;
    }

    private static String parseFracPartToTargetBase(String fracPart, int sourceBase, int targetBase) {
        BigInteger decimalFracPart = parseFracPartToDecimal(fracPart, sourceBase, targetBase);
        if (targetBase == 10)  return decimalFracPart.toString();



        return "";
    }
}

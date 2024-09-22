package converter.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class NumberBaseConverter {

    private static final int PRECISION = 5;

    public static String nonFractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        BigInteger decimal = parseIntegerToDecimal(sourceNumber, sourceBase, targetBase);
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
        integerPart = parseIntegerToDecimal(integerPart, sourceBase, targetBase).toString();

        // Convert fractional part to decimal base
        fractionalPart = parseFracToDecimal(fractionalPart, sourceBase);

        // Get BigDecimal value and round it
        BigDecimal decimal = buildBigDecimal(integerPart, fractionalPart);
        decimal = roundFractionalPart(decimal);

        return parseFracDecimalToTargetBase(decimal, targetBase);
    }

    // Converts the integer part from source base to decimal (base 10)
    private static BigInteger parseIntegerToDecimal(String sourceNumber, int sourceBase, int targetBase) {

        if (isZero(sourceNumber)) return BigInteger.ZERO;

        BigInteger sum = BigInteger.ZERO;
        int length = sourceNumber.length();

        for (int i = 0; i < length; i++) {
            char digitChar = sourceNumber.charAt(i);
            int coefficient = charToInt(digitChar);

            if (coefficient >= sourceBase) throw new IllegalArgumentException(String.format("Error! Invalid digit. Source base: %d Target base: %d", sourceBase, targetBase));

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
            int digit = reminder.intValue();
            char charDigit = intToChar(digit);
            sb.append(charDigit);
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

            BigDecimal decimalValue = BigDecimal.valueOf(coefficient).divide(base.pow(i + 1), MathContext.DECIMAL128);
            sum = sum.add(decimalValue);
        }
        return sum.toString();
    }

    private static BigDecimal buildBigDecimal(String intP, String fracP) {
        BigDecimal intPart = new BigDecimal(intP);
        BigDecimal fracPart = new BigDecimal(fracP);
        return intPart.add(fracPart);
    }

    private static BigDecimal roundFractionalPart(BigDecimal number) {
        return number.setScale(PRECISION, RoundingMode.DOWN);
    }

    private static String parseFracDecimalToTargetBase(BigDecimal decimal, int targetBase) {
        // Separate integer part from decimal part
        String intPart = decimal.setScale(0, RoundingMode.DOWN).toString();
        BigDecimal fracPart = decimal.remainder(BigDecimal.ONE);

        // Convert int part to target base
        intPart = parseDecimalToTargetBase(new BigInteger(intPart), targetBase);

        // Convert frac part to target Base
        String strFracPart = parseFracPartToTargetBase(fracPart, targetBase);

        return (intPart + "." + strFracPart).toLowerCase();
    }

    private static String parseFracPartToTargetBase(BigDecimal decPart, int base) {
        BigDecimal targetBase = BigDecimal.valueOf(base);
        StringBuilder sb = new StringBuilder();
        int maxDigits = 5;

        while (maxDigits-- > 0) {
            decPart = decPart.multiply(targetBase); // multiply current decimal part by the target base
            // convert current digit to char
            int digit = decPart.intValue();
            char charDigit = intToChar(digit);
            sb.append(charDigit);
            // Eliminate the integer part of the fractional
            decPart = decPart.subtract(BigDecimal.valueOf(digit));
        }
        // if the decimal part is < 5 digits fill it with zeros
        while (sb.length() < maxDigits) {
            sb.append("0");
        }

        return sb.toString();
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

    private static int charToInt(char ch) {
        if (Character.isDigit(ch)) {
            return ch - '0';
        } else if (Character.isLetter(ch)) {
            return Character.toUpperCase(ch) - 'A' + 10;
        } else {
            throw new IllegalArgumentException("Error! Invalid character: " + ch);
        }
    }

    private static char intToChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (value + '0');
        } else if (value >= 10 && value <= 35) {
            return (char) (value - 10 + 'A');
        } else {
            throw new IllegalArgumentException("Error! Invalid integer: " + value);
        }
    }
}
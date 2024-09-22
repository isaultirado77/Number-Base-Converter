package converter.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * The NumberBaseConverter class provides methods for converting numbers
 * between different bases, supporting both integer and fractional parts.
 * It can handle conversions for arbitrary bases, including both integer
 * and fractional numbers, and ensures precision for fractional conversions.
 * Features:
 * - Converts integers between any two bases.
 * - Handles fractional numbers with a precision of up to 5 decimal places.
 * - Ensures valid input by checking digits based on the source base.
 * Key Methods:
 * - nonFractionConversion: Converts an integer number between two bases.
 * - fractionConversion: Converts a number with a fractional part between two bases.
 * - parseIntegerToDecimal: Converts an integer from a given base to decimal (base 10).
 * - parseDecimalToTargetBase: Converts a decimal integer to a target base.
 * - parseFracToDecimal: Converts a fractional part from any base to decimal.
 * - parseFracDecimalToTargetBase: Converts a fractional decimal to a target base.
 */

public class NumberBaseConverter {

    private static final int PRECISION = 5;

    /*
    Converts an integer number between two bases.
    Return a string with the input number converted to target base.
     */
    public static String nonFractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        BigInteger decimal = parseIntegerToDecimal(sourceNumber, sourceBase, targetBase);
        if (sourceNumber.equals(BigInteger.ZERO.toString())) return "0";
        else if (targetBase == 10) return decimal.toString();
        else return parseDecimalToTargetBase(decimal, targetBase);
    }

    /*
    Converts a number with a fractional part between two bases.
    Return a string with the input number converted to target base.
     */
    public static String fractionConversion(String sourceNumber, int sourceBase, int targetBase) {

        String[] splitNumber = sourceNumber.split("\\.");

        if (splitNumber.length > 2) throw new NumberBaseConversionException("Invalid input format.");

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

            if (coefficient >= sourceBase)
                throw new NumberBaseConversionException(String.format("Error! Invalid digit. Source base: %d Target base: %d", sourceBase, targetBase));

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

    // Converts the integer part from decimal (base 10) to the target base
    private static String parseFracToDecimal(String frac, int sourceBase) {

        if (isZero(frac)) return "0".repeat(PRECISION);

        int length = frac.length(); // length of the decimal part
        BigDecimal sum = BigDecimal.ZERO; // init the sum of the digits
        BigDecimal base = BigDecimal.valueOf(sourceBase);

        for (int i = 0; i < length; i++) {
            char charDigit = frac.charAt(i); // current char
            int coefficient = charToInt(charDigit); // current coefficient

            if (coefficient >= sourceBase)
                throw new NumberBaseConversionException("Error! Invalid digit for the source base.");

            BigDecimal decimalValue = BigDecimal.valueOf(coefficient).divide(base.pow(i + 1), MathContext.DECIMAL32);
            sum = sum.add(decimalValue);
        }
        return sum.toString();
    }

    // Combine the integer and fraction parts into a single BigDecimal.
    private static BigDecimal buildBigDecimal(String intP, String fracP) {
        BigDecimal intPart = new BigDecimal(intP);
        BigDecimal fracPart = new BigDecimal(fracP);
        return intPart.add(fracPart);
    }

    // Rounds the number to a fixed number of decimal places, without rounding up.
    private static BigDecimal roundFractionalPart(BigDecimal number) {
        return number.setScale(PRECISION, RoundingMode.DOWN);
    }

    // Converts a BigDecimal number into its target base, separating integer and fractional parts.
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

    // Converts the fractional part of a BigDecimal number into the target base with a fixed precision.
    private static String parseFracPartToTargetBase(BigDecimal decPart, int base) {
        BigDecimal targetBase = BigDecimal.valueOf(base);
        StringBuilder sb = new StringBuilder();
        int maxDigits = 5;

        while (maxDigits-- > 0) {
            decPart = decPart.multiply(targetBase); // multiply current decimal part by the target base

            int digit = decPart.intValue(); // Get current digit on integer form

            char charDigit = intToChar(digit);  // Get char representation of the current char

            sb.append(charDigit); // Append the current digit

            decPart = decPart.subtract(BigDecimal.valueOf(digit)); // Eliminate the integer part of the fractional
        }

        return sb.toString();
    }

    // Method to check if a string represents zero.
    // Returns true for null, empty, or "0"; false otherwise.
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

    // Converts a character to its corresponding integer value.
    // Returns the integer value for digits (0-9) and letters (A-Z).
    // Throws IllegalArgumentException for invalid characters.
    private static int charToInt(char ch) {
        if (Character.isDigit(ch)) {
            return ch - '0';
        } else if (Character.isLetter(ch)) {
            return Character.toUpperCase(ch) - 'A' + 10;
        } else {
            throw new NumberBaseConversionException("Error! Invalid character: " + ch);
        }
    }

    // Converts a character to its corresponding integer value.
    // Returns the integer value for digits (0-9) and letters (A-Z).
    // Throws IllegalArgumentException for invalid characters.
    private static char intToChar(int value) {
        if (value >= 0 && value <= 9) {
            return (char) (value + '0');
        } else if (value >= 10 && value <= 35) {
            return (char) (value - 10 + 'A');
        } else {
            throw new NumberBaseConversionException("Error! Invalid integer: " + value);
        }
    }
}

class NumberBaseConversionException extends RuntimeException {
    public NumberBaseConversionException(String message) {
        super(message);
    }
}
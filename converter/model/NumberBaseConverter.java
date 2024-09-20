package converter.model;

import java.math.BigInteger;

public class NumberBaseConverter {

//    private final String sourceNumber;
//    private final int sourceBase;
//    private final int targetBase;
//
//    public NumberBaseConverter(ConversionRequest request) {
//        this.sourceNumber = request.representation;
//        this.sourceBase = request.getSource();
//        this.targetBase = request.getTarget();
//    }

    private BigInteger parseToDecimal(String sourceNumber, int sourceBase, int targetBase) {
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
                throw new IllegalArgumentException("Error! Invalid character in source number");
            }

            if (coefficient >= sourceBase) {
                String error = String.format("Error! Invalid digit. Source base: %d Target base: %d",
                        sourceBase, targetBase);
                throw new IllegalArgumentException("");
            }

            int exponent = length - i - 1;
            sum = sum.add(BigInteger.valueOf(coefficient).multiply(BigInteger.valueOf(sourceBase).pow(exponent)));
        }
        return sum;
    }
}

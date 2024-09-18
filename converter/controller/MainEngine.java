package converter.controller;

import converter.io.IOHandler;
import converter.io.Printer;
import converter.model.Converter;

public class MainEngine {

    public static int promptDecimalNumber() {
        Printer.print("Enter number in decimal system: ");
        return IOHandler.readInteger();
    }

    public static int promptTargetBase() {
        Printer.print("Enter target base: ");
        return IOHandler.readInteger();
    }

    public static String decimalToBinary(int decimal) {
        return Converter.decimalToBinary(decimal);
    }

    public static String decimalToOctal(int decimal) {
        return Converter.decimalToOctal(decimal);
    }

    public static String decimalToHexadecimal(int decimal) {
        return Converter.decimalToHexadecimal(decimal); 
    }
}

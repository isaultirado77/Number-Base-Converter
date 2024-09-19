package converter.controller;

import converter.io.IOHandler;
import converter.io.Printer;
import converter.model.Converter;

public class MainEngine {

    public static String promptUserOption() {
        Printer.print("Do you want to convert /from decimal or /to decimal? (To quit type /exit) ");
        return IOHandler.readNextLine();
    }

    public static int promptDecimalNumber() {
        Printer.print("Enter number in decimal system: ");
        return IOHandler.readInteger();
    }

    public static int promptTargetBase() {
        Printer.print("Enter target base: ");
        return IOHandler.readInteger();
    }

    public static String promptSourceNumber(){
        System.out.print("Enter the source number: ");
        return IOHandler.readNextLine();
    }

    public static int promptSourceBase() {
        Printer.print("Enter the source base: ");
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

    public static String binaryToDecimal(String binary) {
        return String.valueOf(Converter.binaryToDecimal(binary));
    }

    public static String binaryToOctal(String binary) {
        return Converter.binaryToOctal(binary);
    }

    public static String binaryToHexadecimal(String binary) {
        return Converter.binaryToHexadecimal(binary);
    }

    public static String octalToDecimal(String octal) {
        return Converter.octalToDecimal(octal);
    }

    public static String hexaToDecimal(String hexa) {
        return Converter.hexaToDecimal(hexa);
    }
}
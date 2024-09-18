package converter.controller;

import converter.io.IOHandler;
import converter.io.Printer;

public class MainEngine {

    public static int promptDecimalNumber() {
        Printer.print("Enter number in decimal system: ");
        return IOHandler.readInteger();
    }

    public static int promptTargetBase() {
        Printer.print("Enter target base: ");
        return IOHandler.readInteger();
    }
}

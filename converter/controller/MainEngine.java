package converter.controller;

import converter.io.IOHandler;
import converter.io.Printer;

public class MainEngine {

    public int promptDecimalNumber() {
        Printer.println("Enter number in decimal system:");
        return IOHandler.readInteger();
    }

    public int promptTargetBase() {
        Printer.println("Enter target base:");
        return IOHandler.readInteger();
    }
}

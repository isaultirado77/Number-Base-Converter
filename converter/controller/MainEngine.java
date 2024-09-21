package converter.controller;

import converter.io.IOHandler;
import converter.io.Printer;
import converter.model.ConversionRequest;
import converter.model.NumberBaseConverter;
import converter.model.NumeralSystem;

public class MainEngine {

    public static String promptSourceTargetBase() {
        Printer.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
        return IOHandler.readNextLine();
    }

    public static String promptNumberToConvert(NumeralSystem numeralSystem) {
        int source = numeralSystem.getSource();
        int target = numeralSystem.getTarget();
        System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", source, target);
        return IOHandler.readNextLine();
    }

    public static String executeConversion(ConversionRequest request) {
        String sourceNumber = request.getRepresentation();
        int sourceBase = request.getSource();
        int targetBase = request.getTarget();

        return sourceNumber.contains(".") ?
                NumberBaseConverter.fractionConversion(sourceNumber, sourceBase, targetBase) :
                NumberBaseConverter.nonFractionConversion(sourceNumber, sourceBase, targetBase);
    }
}
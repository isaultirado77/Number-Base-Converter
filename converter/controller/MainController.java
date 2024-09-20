package converter.controller;

import converter.io.IOHandler;
import converter.io.Printer;
import converter.model.NumeralSystem;

import java.math.BigInteger;

public class MainController {

    public void start() {
        while (true) {
            MainEngine.promptSourceTargetBase();
            String input = IOHandler.readNextLine();

            if ("/exit".equalsIgnoreCase(input)) {
                break;
            }

            NumeralSystem numeralSystem = getNumeralSystem(input);
            if (numeralSystem != null) {
                handleConversions(numeralSystem);
            }
        }

    }

    private NumeralSystem getNumeralSystem(String input) {
        try {
            String[] bases = input.split(" ");
            int sourceBase = Integer.parseInt(bases[0]);
            int targetBase = Integer.parseInt(bases[1]);
            return new NumeralSystem(sourceBase, targetBase);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Printer.println("Invalid input! Please enter two valid base numbers.");
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void handleConversions(NumeralSystem numeralSystem) {
        while (true) {
            MainEngine.promptNumberToConvert(numeralSystem);
            String numberInput = IOHandler.readNextLine();

            if ("/back".equalsIgnoreCase(numberInput)) break;

            String convertedNumber = convertNumber(numberInput, numeralSystem);
            if (convertedNumber != null) {
                System.out.println("Conversion result: " + convertedNumber);
            }
        }
    }

    private String convertNumber(String numberInput, NumeralSystem numeralSystem) {
        try {
            BigInteger number = new BigInteger(numberInput, numeralSystem.getSource());
            return number.toString(numeralSystem.getTarget());
        } catch (NumberFormatException e) {
            Printer.println("Invalid number! Please enter a valid number for the base.");
            return null;
        }
    }

}
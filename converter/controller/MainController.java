package converter.controller;

import converter.io.Printer;
import converter.model.ConversionRequest;
import converter.model.NumeralSystem;

public class MainController {

    public void start() {
        while (true) {
            String input = MainEngine.promptSourceTargetBase();;

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

            String numberInput = MainEngine.promptNumberToConvert(numeralSystem);

            if ("/back".equalsIgnoreCase(numberInput)) break;

            ConversionRequest conversionRequest = new ConversionRequest(numberInput, numeralSystem);

            // implement convert number to selected base
        }
    }
}
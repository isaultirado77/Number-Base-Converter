package converter.controller;

import converter.io.Printer;

public class MainController {

    public void run() {
        while (true) {
            String option = MainEngine.promptUserOption();

            if (option.equals("/exit")) break;

            handleUserMenuOption(option);
        }
    }

    private void handleUserMenuOption(String option) {
        switch (option) {
            case "/from":
                fromOption();
                break;
            case "/to":
                toOption();
                break;
            default:
                Printer.println("Error! Invalid option.\n");
                break;
        }
    }

    private void fromOption() {
        try {
            int decimal = MainEngine.promptDecimalNumber();
            int targetBase = MainEngine.promptTargetBase();
            String targetBaseNumber = parseDecimalToTargetBase(decimal, targetBase);
            Printer.printf("Conversion result: %s\n\n", targetBaseNumber);
        } catch (IllegalArgumentException e) {
            Printer.println(e.getMessage());
        }
    }

    private String parseDecimalToTargetBase(int decimal, int targetBase) {
        return switch (targetBase) {
            case 2 -> MainEngine.decimalToBinary(decimal);
            case 8 -> MainEngine.decimalToOctal(decimal);
            case 16 -> MainEngine.decimalToHexadecimal(decimal);
            default -> throw new IllegalArgumentException("Error! Incorrect target base. \n");
        };
    }

    private void toOption() {
        try {
            String sourceNumber = MainEngine.promptSourceNumber();
            int sourceBase = MainEngine.promptSourceBase();
            String decimalNumber = parseToDecimal(sourceNumber, sourceBase);
            Printer.printf("Conversion to decimal: %s\n\n", decimalNumber);
        } catch (NumberFormatException e) {
            Printer.println(e.getMessage() + "\n");
        }
    }

    private String parseToDecimal(String sourceNumber, int sourceBase) {
        return switch (sourceBase) {
            case 2 -> MainEngine.binaryToDecimal(sourceNumber);
            case 8 -> MainEngine.octalToDecimal(sourceNumber);
            case 16 -> MainEngine.hexaToDecimal(sourceNumber);
            default -> throw new IllegalArgumentException("Error! Incorrect target base. \n");
        };
    }
}
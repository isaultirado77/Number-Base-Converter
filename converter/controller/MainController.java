package converter.controller;

import converter.Main;
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
            String targetBaseNumber = parseDecimalToTargetBse(decimal, targetBase);
        } catch (IllegalArgumentException e) {
            Printer.println(e.getMessage());
        }
    }

    private String parseDecimalToTargetBse(int decimal, int targetBase) {
        return switch (targetBase) {
            case 2 -> MainEngine.decimalToBinary(decimal);
            case 8 -> MainEngine.decimalToOctal(decimal);
            case 16 -> MainEngine.decimalToHexadecimal(decimal);
            default -> throw new IllegalArgumentException("Error! Incorrect target base. \n");
        };
    }

    private void toOption() {
        try {
            int sourceNumber = MainEngine.promptSourceNumber();
            int sourceBase = MainEngine.promptSourceBase();
            parseSourceNumberToDecimal(sourceNumber, sourceBase);

        } catch (NumberFormatException e) {
            Printer.println(e.getMessage() + "\n");
        }
    }

    private void parseSourceNumberToDecimal(int sourceNumber, int sourceBase) {
    }
}

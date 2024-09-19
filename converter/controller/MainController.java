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
            fromDecimalToTargetBse(decimal, targetBase);
        } catch (NumberFormatException e) {
            Printer.println(e.getMessage());
        }
    }

    private void fromDecimalToTargetBse(int decimal, int targetBase) {
    }

    private void toOption() {
        try {
            int sourceNumber = MainEngine.promptSourceNumber();
            int sourceBase = MainEngine.promptSourceBase();
            fromSourceNumberToDecimal(sourceNumber, sourceBase);

        } catch (NumberFormatException e) {
            Printer.println(e.getMessage() + "\n");
        }
    }

    private void fromSourceNumberToDecimal(int sourceNumber, int sourceBase) {
    }
}

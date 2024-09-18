package converter.controller;


import converter.model.Converter;

public class MainController {

    public void run() {
        int decimal = MainEngine.promptDecimalNumber();
        int base = MainEngine.promptTargetBase();

        String converted;

        if (base == 2) {
            converted = Converter.decimalToBinary(decimal);
            System.out.println("Conversion result: " + converted);
        } else if (base == 8) {
            converted = Converter.decimalToOctal(decimal);
            System.out.println("Conversion result: " + converted);
        } else if (base == 16) {
            converted = Converter.decimalToHexadecimal(decimal);
            System.out.println("Conversion result: " + converted);
        } else {
            System.out.println("Invalid option");
        }
    }
}

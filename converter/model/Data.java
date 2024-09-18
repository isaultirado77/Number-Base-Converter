package converter.model;

public class Data {
    int decimal;
    String binary;
    String octal;
    String hexadecimal;

    public Data() {
        decimal = 0;
        binary = "00";
        octal = "000";
        hexadecimal = "0000";
    }

    public Data(int decimal) {
        this.decimal = decimal;
    }
}

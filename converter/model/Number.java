package converter.model;

public class Number {
    int decimal;
    String binary;
    String octal;
    String hexadecimal;

    public Number() {
        decimal = 0;
        binary = "00";
        octal = "000";
        hexadecimal = "0000";
    }

    public Number(int decimal) {
        this.decimal = decimal;
    }
}

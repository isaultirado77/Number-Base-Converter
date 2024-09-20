package converter.model;

public class NumberWithSystem {

    String representation;
    NumeralSystem numeralSystem;

    public NumberWithSystem(String representation, NumeralSystem numeralSystem) {
        this.representation = representation;
        this.numeralSystem = numeralSystem;
    }

    @SuppressWarnings("unused")
    public String getRepresentation() {
        return representation;
    }

    @SuppressWarnings("unused")

    public int getSource() {
        return numeralSystem.getSource();
    }

    @SuppressWarnings("unused")
    public int getTarget() {
        return numeralSystem.getTarget();
    }
}

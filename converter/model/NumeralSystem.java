package converter.model;

public class NumeralSystem {

    private int source;
    private int target;
    public static final int MAX_BASE = 36;
    public static final int MIN_BASE = 2;

    public NumeralSystem(int source, int target) throws BaseError {
        this.source = source;
        this.target = target;
        if (source < MIN_BASE || source > MAX_BASE || target < MIN_BASE || target > MAX_BASE) {
            throw new BaseError(source < MIN_BASE || source > MAX_BASE ? source : target);
        }
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    class BaseError extends Exception{
        public BaseError(int base) {
            super(String.format("Error! Base %d is not in the range of integers between %d and %d inclusive.", base,
                    MIN_BASE, MAX_BASE));
        }
    }
}

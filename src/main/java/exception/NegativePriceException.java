package exception;

public class NegativePriceException extends Exception{
    public NegativePriceException() {
        super("Entered price is negative");
    }
}

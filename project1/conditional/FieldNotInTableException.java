package project1.conditional;

public class FieldNotInTableException extends Exception {
    public FieldNotInTableException() {
    }

    public FieldNotInTableException(String message) {
        super(message);
    }
}

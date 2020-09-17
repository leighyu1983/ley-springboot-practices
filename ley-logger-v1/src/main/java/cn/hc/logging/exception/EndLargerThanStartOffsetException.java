package cn.hc.logging.exception;

public class EndLargerThanStartOffsetException extends RuntimeException {

    public EndLargerThanStartOffsetException() {
        super();
    }

    public EndLargerThanStartOffsetException(String message) {
        super(message);
    }
}

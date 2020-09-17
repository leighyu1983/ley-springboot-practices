package cn.hc.logging.exception;


public class NotSupportedException extends RuntimeException {

	public NotSupportedException() {
		super();
	}

	public NotSupportedException(String message) {
		super(message);
	}
}

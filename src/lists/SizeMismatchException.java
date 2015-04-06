package lists;

public class SizeMismatchException extends RuntimeException {

	private static final long serialVersionUID = -927514551910137133L;

	public SizeMismatchException () {
		super("Size was set incorrectly!");
	}

	public SizeMismatchException (String message) {
		super(": Size was set incorrectly!" + message);
	}

}

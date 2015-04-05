package lists;

public class InvalidSliceException extends RuntimeException {

	private static final long serialVersionUID = -9165265652859795946L;

	public InvalidSliceException () {
		super("Invalid slice!");
	}

	public InvalidSliceException (String message) {
		super("Element not found! " + message);
	}
}

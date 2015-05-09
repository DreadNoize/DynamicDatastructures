package errors;

public class IndexOutOfBoundsException extends RuntimeException {

	private static final long serialVersionUID = -3061382900186543648L;

	public IndexOutOfBoundsException () {
		super("Index out of bounds!");
	}

	public IndexOutOfBoundsException (String message) {
		super("Index out of bounds! " + message);
	}

}

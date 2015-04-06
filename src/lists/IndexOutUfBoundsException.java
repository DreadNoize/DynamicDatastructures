package lists;

public class IndexOutUfBoundsException extends RuntimeException {

	private static final long serialVersionUID = -3061382900186543648L;

	public IndexOutUfBoundsException () {
		super("Index out of bounds!");
	}

	public IndexOutUfBoundsException (String message) {
		super("Index out of bounds! " + message);
	}

}

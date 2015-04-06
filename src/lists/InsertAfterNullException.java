package lists;

public class InsertAfterNullException extends RuntimeException {

	private static final long serialVersionUID = 6576180060215467767L;

	public InsertAfterNullException () {
		super("Cannot insert after null! Argument was null");
	}
}

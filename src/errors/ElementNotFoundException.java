package errors;

public class ElementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5623796685445812702L;
	
	public ElementNotFoundException () {
		super("Element not found!");
	}

	public ElementNotFoundException (String message) {
		super("Element not found! " + message);
	}
}

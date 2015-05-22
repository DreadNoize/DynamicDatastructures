package ringpuffer;

public class Ringpuffer {
	private final int INIT_SIZE = 10;
	private int currentSize = 10;
	private int[] arli = new int[INIT_SIZE];
	private int first;
	private int end;

	private boolean isFull() {
		return first == end && arli[first] != -1;
	}
	
	private boolean isEmpty() {
		return first == end && arli[first] == -1;
	}
	
	public void offer(int data) {
		arli[end++] = data;
	}
	
	public int poll() {
		int temp = arli[first]; // -1 if isEmpty()
		arli[first++] = -1;
		return temp;
	}
}

package ringpuffer;

public class Ringpuffer {
	private int   currentSize;
	private int[] arli;
	private int   first = 0;
	private int   end   = 0;

	public Ringpuffer(int cap) {
		if(cap > 0) {
		currentSize = cap;
		arli = new int[currentSize];
		arli[0] = 0; // 0 is poison value
		}
	}

	private boolean isFull() { // reclaim somehow? isHalfFull() is tricky
		return first == end && arli[first] != 0;
	}

	private boolean isEmpty() {
		return first == end && arli[first] == 0;
	}

	public void offer(int data) {
		if (!isFull()) {
		arli[end++ % currentSize] = data;
		} else {
			enlarge();
			offer(data);
		}
	}

	private void enlarge() {
		currentSize *= 2;
		System.out.println("enlarging to " + currentSize);
		int[] newArr = new int[currentSize];
		System.arraycopy(arli, 0, newArr, 0, arli.length);
		arli = newArr;
		newArr = null;
	}

	public int poll() {
		if (!isEmpty()) {
		int temp = arli[first];
		arli[first] = 0;
		first = (first + 1) % currentSize;
		return temp;
		}
		return -1;
	}

	private int peek() {
		if(!isEmpty()) {
			return arli[first];
		}
		return -1;
	}
}

package ringpuffer;

public class Ringpuffer {
	private int arrSize;
	private int[] arli;
	private int first = 0;
	private int end = 0;
	private int size = 0;

	public Ringpuffer(int cap) {
		if (cap > 0) {
			arrSize = cap;
			arli = new int[arrSize];
			arli[0] = -1; // -1 definition of empty
		} else {
			throw new IllegalArgumentException(
					"arr of negative or 0 length makes no sense!");
		}
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		if (size == 0) {
			return first == end && arli[first] == -1;
		}
		return false;
	}

	private boolean isFull() {
		if (size == arrSize) {
			return first == end && arli[first] != -1;
		}
		return false;
	}

	public void offer(int element) {
		if (!isFull()) {
			arli[end] = element;
			end = (end + 1) % arrSize;
		} else {
			enlarge();
			arli[end] = element;
			end = (end + 1) % arrSize;
		}
		size++;
	}

	public int poll() {
		if (!isEmpty()) {
			int temp = arli[first];
			arli[first] = -1;
			first = (first + 1) % arrSize;
			size--;
			return temp;
		}
		return -1;
	}

	public int peek() {
		if (!isEmpty()) {
			return arli[first];
		}
		return -1;
	}

	/**
	 * this method copies arli's values in an array of twice the size in such a
	 * way that 'first' of arli is copied to newArr[0].
	 */
	private void enlarge() {
		// System.out.print("enlarging to ");
		int[] newArr = new int[arrSize * 2];
		for (int i = 0; i < size; i++) {
			newArr[i] = arli[first];
			first = (first + 1) % (arrSize);
		}
		arli = newArr;
		first = 0;
		end = size;
		arrSize *= 2;
		// System.out.println(arli.length);
		newArr = null;
	}

}


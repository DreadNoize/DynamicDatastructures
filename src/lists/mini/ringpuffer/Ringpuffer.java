package ringpuffer;

import java.util.Arrays;

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
		}
	}

	private boolean isEmpty() {
		if (size == 0) {
			return first == end && arli[first] == -1;
		}
		return false;
	}

	private boolean isFull() { // reclaim somehow? isHalfFull() is tricky
		if (size == arrSize) {
			System.out.println(first == end % arrSize && arli[first] != -1);
			return first == end && arli[first] != -1; // maybe more checking
														// later?
		}
		return false; // a little dangerous when there are inconsistencies
						// between size and arrSize
	}

	public void offer(int data) {
		if (!isFull()) {
			arli[end] = data;
			end = (end + 1) % arrSize;
		} else {
			enlarge();
			offer(data);
		}
		size++;
	}

	private void enlarge() {
		arrSize *= 2;
		System.out.print("enlarging to ");
		int[] newArr = new int[arrSize];
		System.arraycopy(arli, 0, newArr, 0, arli.length);
		arli = newArr;
		System.out.println(Arrays.toString(arli));
		newArr = null;
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

	private int peek() {
		if (!isEmpty()) {
			return arli[first];
		}
		return -1;
	}
}

// TODO: emptyval = -1, poisonVal -1?
// TODO: reclaim space by size
// TODO: when enlarging put stuff in the beginning of array

package ringpuffer;

public class testBuffer {

	public static void main(String[] args) {
		int j;
		Ringpuffer r1 = new Ringpuffer(1);

		for (int i = 0; i < 16; i++) {
			r1.offer(i);
		}
		for (int i = 0; i < 11; i++) {
			j = r1.poll(); // for debugging; debugger prints "exhaustingly thorough"
			System.out.println(j);
		}
		for (int i = 0; i < 19; i++) {
			r1.offer(i);
		}
		for (int i = 0; i < 29; i++) {
			j = r1.poll();
			System.out.println(j);
		}
	}
}

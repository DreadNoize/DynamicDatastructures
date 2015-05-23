package ringpuffer;

public class testBuffer {

	public static void main(String[] args) {
		int j;
		Ringpuffer r1 = new Ringpuffer(10);

		for (int i = 0; i < 16; i++) {
			r1.offer(i);
		}
		System.out.println("offered 16");
		for (int i = 0; i < 5; i++) {
			j = r1.poll(); // for debugging; debugger prints "exhaustingly thorough"
			System.out.println(j);
		}
		System.out.println("polled 5");
		for (int i = 0; i < 45; i++) {
			r1.offer(i);
		}
		System.out.println("offered 45");
		for (int i = 0; i < 60; i++) {
			j = r1.poll();
			System.out.println(j);
		}
		System.out.println("polled 60");
	}
}

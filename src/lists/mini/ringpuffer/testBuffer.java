package ringpuffer;

public class testBuffer {

	public static void main(String[] args) {
		Ringpuffer r1 = new Ringpuffer(1);

		for (int i = 0; i < 16; i++) {
			r1.offer(i);
		}
		for (int i = 0; i < 11; i++) {
			System.out.println(r1.poll());
		}
		for (int i = 0; i < 10; i++) {
			r1.offer(i);
		}
		for (int i = 0; i < 15; i++) {
			System.out.println(r1.poll());
		}
	}
}

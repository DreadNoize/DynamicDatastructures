package ringpuffer;

public class testBuffer {

	public static void main(String[] args) {
		Ringpuffer r1 = new Ringpuffer();
		for (int i = 0; i < 6; i++) {
			r1.offer(i);
		}
		for (int i = 0; i < 6; i++) {
			System.out.println(r1.poll());
		}
	}

}

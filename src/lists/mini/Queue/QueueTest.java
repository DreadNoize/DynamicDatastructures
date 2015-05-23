package Queue;

import java.util.stream.IntStream;

import adapter.QueueAdapter;

public class QueueTest {

	public static void main(String[] args) {
		QueueAdapter q = new QueueAdapter(2);
		IntStream.range(0, 20).forEach(i -> q.offer(i));
		System.out.println(q.peek());
		IntStream.range(0, 20).forEach(i -> System.out.println(q.poll()));
	}

}

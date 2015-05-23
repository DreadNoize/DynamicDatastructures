package Queue;

import java.util.stream.IntStream;

import adapter.QueueAdapter;

/* assert requires the -ea (enable assertions) flag for jvm.
 * In eclipse go to 'Run → Run conf → arguments tab → vm options field
 *
 * IntStream requires the Java 8 Stream API. For this:
 * The Java 8 JRE has to be present and enabled
 * The compiler compliance level has to be set to 1.8
 */

public class QueueTest {

	public static void main(String[] args) {
		QueueAdapter q = new QueueAdapter(2);
		IntStream.range(0, 20).forEach(i -> q.offer(i));

		System.out.println(q.peek());

		IntStream.range(0, 20).forEach(i -> System.out.println(q.poll()));
	}
}

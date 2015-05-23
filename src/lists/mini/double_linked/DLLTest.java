package double_linked;

import java.util.stream.IntStream;

/* assert requires the -ea (enable assertions) flag for jvm.
 * assert is not needed for function and will be skipped if no -ea is passed.
 * In eclipse go to 'Run → Run conf → arguments tab → vm options field
 *
 * IntStream requires the Java 8 Stream API. For this:
 * The Java 8 JRE has to be present and enabled
 * The compiler compliance level has to be set to 1.8
 */

public class DLLTest {
	public static void main(String[] args) {
		DLList<Object> test = new DLList<>("hallo", "hier",
				"ist was noch fehlt:", "remove() methode",
				"diese Aussage ist:", true);
		assert !test.isEmpty();
		System.out.println(test.getSize());
		IntStream.range(0, 5).forEach(i -> System.out.println(test.get(i)));
		test.remove(5);
		test.add(false); // werden boolean werte eigentlich geautoboxt!?
		System.out.println(test);
		System.out.println(test.printTypes());

	}
}

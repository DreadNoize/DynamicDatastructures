package lists.single_linked;

/**
 * Created by ra on 02.04.15. 'Simple generic list' tester.
 */

public class TestList {
	public static void main(String args[]) {

		for (int i = 0; i <= 40; i++) {
			System.out.print("=");
		}

		System.out.println("\nSimple linked list");

		for (int i = 0; i <= 40; i++) {
			System.out.print("=");
		}

		System.out.println();

		List<Integer> integers = new List<Integer>(1, 2, 3, 4, 5, 6, 7, 8, 9);
		System.out.println(integers);

		List<String> strings = new List<String>("Will", "this", "slice", "and", "clone?");

		List<Boolean> bools = new List<Boolean>();

		System.out.println("\n"
				+ "Boolean list display, delete and append methods: " + "\n");
		System.out.println(bools);
		bools.append(true);
		bools.append(false);
		bools.append(false);
		bools.append(true);
		bools.addAtIndex(0, false);
		bools.delete_byVal(true);
		bools.delete_byVal(false);
		System.out.println(bools);

		System.out.println("\n" + "Strings delete index 3, reverse:" + "\n");
		System.out.println(strings);
		strings.delete_byIndex(3);
		System.out.println(strings);
		//strings.reverse();
		System.out.println(strings);

		System.out.println("\n" + "Integers addAt 9 10, indexOf 5, getNode 8:"
				+ "\n");
		System.out.println(integers);
		integers.addAtIndex(9, 10);
		System.out.println(integers);
		System.out.println(integers.indexOf(5));
		System.out.println(integers.getNode(8));

		System.out.println("\n" + "Integer cloning and sublisting" + "\n");
		System.out.println(integers);
		List<Integer> integerClone = integers.clone();
		integerClone.getNode(9).setData(6);
		System.out.println("integer list clone: " + integerClone);
		System.out.println("Node 5 orig == Node 5 clone: "
				+ (integers.getNode(5) == integerClone.getNode(5)));

		System.out.println("conned integer with its modified clone: "
				+ integers.concat(integerClone));
		System.out.println("Does the empty List<String> contain \"This\"? "
				+ new List<String>().contains("This"));
		System.out.println(integers);
		System.out.println(java.util.Arrays.toString(integers.toArray()));

		System.out.println(integerClone);
		System.out.println(integerClone.subList(0, 7));

		List<Integer> ints = integers.cloneRec();
		System.out.println(ints);
		System.out.println(ints.getNode(9));

		System.out.println(strings.indexOf("slice"));

		integers.minimum();
	}

}

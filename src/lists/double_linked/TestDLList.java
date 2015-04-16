package lists.double_linked;

/**
 * Created by ra on 02.04.15. 'Simple generic DLList' tester.
 */

public class TestDLList {
	public static void main(String args[]) {

		for (int i = 0; i <= 40; i++) {
			System.out.print("=");
		}

		System.out.println("\nDouble linked list");

		for (int i = 0; i <= 40; i++) {
			System.out.print("=");
		}

		System.out.println();

		DLList<Integer> integers = new DLList<Integer>(1, 2, 3, 4, 5, 6, 7, 8, 9);
		for (Integer integ: integers)
			System.out.println(integ);

		DLList<String> strings = new DLList<String>("This", "might", "work", "now!");

		DLList<Boolean> bools = new DLList<Boolean>();

		System.out.println("\n"
				+ "Boolean DLList display, delete and append methods: " + "\n");
		System.out.println(bools);
		bools.append(true);
		bools.append(false);
		bools.append(false);
		bools.append(true);
		bools.addAtIndex(0, false);
		bools.delete_byVal(true);
		bools.delete_byVal(false);
		System.out.println(bools);

		System.out.println("\n" + "Strings delete index 3 and reverse:" + "\n");
		System.out.println(strings);
		strings.delete_byIndex(3);
		System.out.println(strings);
		strings.reverse();
		System.out.println(strings);
		strings.reverse();
		System.out.println(strings);
		System.out.println("Does the empty DLList<String> contain \"This\"? " + new DLList<String>().contains("This"));

		System.out.println("\n" + "Integers addAt 9 10, indexOf 5, getNode 8:"
				+ "\n");
		integers.addAtIndex(9, 10);
		System.out.println(integers);
		System.out.println(integers.indexOf(5));
		System.out.println(integers.getNode(8));

		System.out.println("\n" + "Integer cloning and subDLListing" + "\n");
		System.out.println(integers);
		DLList<Integer> integerClone = integers.clone();
		integerClone.getNode(9).setData(6);
		System.out.println("integer DLList clone: " + integerClone);
		System.out.println("Node 5 orig == Node 5 clone: " + (integers.getNode(5) == integerClone.getNode(5)));
		System.out.println("conned integer with its modified clone: " + integers.concat(integerClone));

		System.out.println(integerClone);
		System.out.println(integerClone.subList(0, 4));

		System.out.println("Recursive clone: " + integers.cloneRec());
		
		integers.recReverse(integers);
		System.out.println(integers);
	}
}

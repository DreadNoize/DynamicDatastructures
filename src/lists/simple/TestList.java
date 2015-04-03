package lists.simple;

/**
 * Created by ra on 02.04.15. Simple generic list tester.
 */

public class TestList {
    public static void main(String args[]) {
        Node<Integer> n1 = new Node<Integer>(1);
        Node<Integer> n2 = new Node<Integer>(2);
        Node<Integer> n3 = new Node<Integer>(3);
        Node<Integer> n4 = new Node<Integer>(4);

        Node<String> s1 = new Node<String>("This");
        Node<String> s2 = new Node<String>("might");
        Node<String> s3 = new Node<String>("even");
        Node<String> s4 = new Node<String>("work.");

        List<Integer> integers = new List<Integer>(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<String> strings = new List<String>(s1, s2, s3, s4);

        List<Boolean> bools = new List<Boolean>();
        System.out.println("Boolean list display, delete and append methods: ");
        bools.display();
        bools.append(true);
        bools.append(false);
        bools.append(false);
        bools.append(true);
        bools.addAt(0, false);
        bools.delete_byVal(true);
        bools.delete_byVal(false);
        bools.display();

        System.out.println("Strings delete index 3:");
        strings.display();
        strings.delete_byIndex(3);
        strings.display();

        System.out.println("Integers addAt 9 10, indexOf 5, getNode 8:");
        integers.addAt(9, 10);
        integers.display();
        System.out.println(integers.indexOf(5));
        System.out.println(integers.getNode(8));
        
        List<Integer> clone = integers.clone();
        clone.delete_byIndex(0);
        clone.display();
        integers.display();

    }
}

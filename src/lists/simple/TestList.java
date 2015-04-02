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
        bools.display();
        bools.append(true);
        bools.append(false);
        bools.append(false);
        bools.append(true);
//        l3.delete_byVal(true);
//        l2.delete_byVal("work."); here be dragons.

        strings.display();
        bools.display();
        integers.display();

    }
}

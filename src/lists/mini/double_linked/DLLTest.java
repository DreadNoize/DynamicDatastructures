package double_linked;

// this requires the -ea (enable assertions) flag
// in eclipse go to 'Run → Run conf → arguments tab → vm options field

public class DLLTest {
    public static void main(String[] args) {
        DLList<Object> test = new DLList<Object>("hallo", "hier", "ist was noch fehlt:", "remove() methode", "diese Aussage ist:", true); // varargs
        assert !test.isEmpty();
        System.out.println(test.getSize());
        System.out.println(test.get(0));
        System.out.println(test.get(1));
        System.out.println(test.get(2));
        System.out.println(test.get(3));
        System.out.println(test.get(4));
        System.out.println(test.get(5));
        test.remove(5);
        test.add(false); // werden boolean werte eigentlich geautoboxt!?
        System.out.println(test);
        System.out.println(test.printTypes());

    }
}
package lists.mini.double_linked;

// this requires the -ea (enable assertions) flag
// go to 'Run → Run conf → arguments tab → vm options field

public class DLLTest {
    public static void main(String[] args) {
        DLList<Object> test = new DLList<Object>("hi", 'c', "hen", 34 , true);
        assert !test.isEmpty();
        System.out.println(test.getSize());
        System.out.println(test.get(4));
    }
}
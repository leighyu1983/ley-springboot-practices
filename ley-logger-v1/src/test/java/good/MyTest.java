package good;


import org.junit.Test;

public class MyTest {
    @Test
    public void test1() {
        MyAccount a = new MyAccount();
        a.setName("my account name 1");
        a.setId("my id 333");

        System.out.println(a.toString());
    }
}

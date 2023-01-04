import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// @TestMethodOrder(MethodOrderer.MethodName.class) : configure our test class to order test methods execution by method name & method arguments.
@TestMethodOrder(MethodOrderer.MethodName.class) public class MethodsOrderedByNameTest {

    @Test void testB() {
        System.out.println("running test B");
    }

    @Test void testA() {
        System.out.println("running test A");
    }

    @Test void testD() {
        System.out.println("running test D");
    }

    @Test void testC() {
        System.out.println("running test C");
    }

}

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/* By default, test classes and methods will be ordered using algorithm that is deterministic but intentionally not obvious. And although true unit tests typically should
   not rely on the order in which they're executed. but there are times when we do need to enforce a specific test method execution order.
   For example: when we work with INTEGRATION TESTS.

   @TestMethodOrder : this class level annotation allow us to apply specific execution order on our unit test methods in test class. this annotation allows us to use
   'MethodOrderer' interface, and we can use this interface to specify which order we would like to apply to these test methods.
   ex: Random, MethodName, OrderAnnotation, DisplayName, AlphaNumeric

 */

// @TestMethodOrder(MethodOrderer.Random.class) : make sure that test methods in this test class will execute in random order.
@TestMethodOrder(MethodOrderer.Random.class) public class MethodOrderedRandomlyTest {

    @Test void testA() {
        System.out.println("running test A");
    }

    @Test void testB() {
        System.out.println("running test B");
    }

    @Test void testC() {
        System.out.println("running test C");
    }

    @Test void testD() {
        System.out.println("running test D");
    }

}

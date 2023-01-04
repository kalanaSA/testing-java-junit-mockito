import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/* how to make test methods execute in any order we like. Being able to control which test method executes first and which this method executes it second is very helpful when
   we're working with INTEGRATION TESTS.
   ex: especially with those tests that need to save something in the database and then find that same record, update that same record, and then delete that same.
 * unless we absolutely need to use a specific execution order, most of the time it is better that we test methods do not depend on each other, and they can still work well,
   even if the execution order is random.                                                                                                                                         */

// @TestMethodOrder(MethodOrderer.OrderAnnotation.class) : to execute test methods in any order we like.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) public class MethodsOrderedByOrderIndexTest {

    @Order(3) @Test void testB() {
        System.out.println("running test B");
    }

    @Order(4) @Test void testA() {
        System.out.println("running test A");
    }

    @Order(1) @Test void testD() {
        System.out.println("running test D");
    }

    @Order(2) @Test void testC() {
        System.out.println("running test C");
    }

}

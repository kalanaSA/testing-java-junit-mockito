import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/* how to order execution of test classes : test classes typically should not rely on the order in which they're executed, but there are times when it is desirable to enforce a
   specific test class execution order. For example,
        - we might want to specifically indicate that this classes should execute unit tests in a random order. this is to ensure that there are no accidental dependencies between
          this test classes.
        - when doing INTEGRATION TESTING we might want to control which test class executes first and which test class gets executed second.
        - we might also want to consider execution of our test classes just to make your test reports look more organized.
 * Instead of configuring execution order for method or a class in each class, junit allows us to provide this configuration for entire project globally. And to do that we can use
   property file that need to be placed into resources folder.
        - right mouse click test folder -> create new package called 'resources'.
        - right mouse click 'resources' folder -> 'open module setting' -> modules tab -> mark this created 'resources' directory as 'test resources' root.
        - right mouse click the 'resources' folder -> new -> resources bundle -> create a resource bundle called 'junit-platform' to create a property file. -> add configurations.
 * In this project,
        - if we execute all test classes in this project, we don't really know which one will run first and which one will run second.
        - If we're doing the INTEGRATION TESTING, then we want to USER related tests to execute first. this is because PRODUCT cannot be ordered without an existing USER
          account. So the order in which we want these tests to be executed is we want all USER related tests to run first, then PRODUCT related tests to run second,
          and at the end ORDER related tests.
 * @Order(1) : to specify which test class will be executed first and which test class will be executed second, we will use @Order annotation at the class level.                */

@Order(3) public class OrderServiceTest {

    @BeforeAll static void setup() {
        System.out.println("Test method related to User Orders");
    }

    @Test void testCreateOrder_WhenOrderIdIsMissing_throwsOrderServiceException() {
    }

}

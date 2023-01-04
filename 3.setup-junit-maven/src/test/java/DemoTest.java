import org.junit.jupiter.api.Test;

public class DemoTest {

    /* By clicking green button or by any other options that IDE has provided, will run unit tests by IDE(IntelliJ) not by the Maven even this is a maven project.
     * However, it is not always that we need to execute unit tests using development environment. Sometimes we need our unit tests to be executed when we *build our project using MAVEN.
       (when we build our project using maven, unit tests should run after compile and before package happens using maven)
     * To run unit tests using maven we need to go pom.xml dir and run mvn commands(mvn package/test) using terminal. but even though mvn commands ran and build succeed, we can see
       zero tests have executed/unit test won't run(refer img).
     * So to be unable our application execute unit tests during the MAVEN build lifecycle we need to add plugin called 'maven surefire' to our pom.xml file.                        */
    @Test void testDemo() {

    }

    /*
    MAVEN Build Life cycle :
    validate - validate the project is correct and all necessary information is available
    compile - compile the source code of the project
    test - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
    package - take the compiled code and package it in its distributable format, such as a JAR.
    verify - run any checks on results of integration tests to ensure quality criteria are met
    install - install the package into the local repository, for use as a dependency in other projects locally
    deploy - done in the build environment, copies the final package to the remote repository for sharing with other developers and projects.

    in case if we need to build or package our project, but we do not want our unit tests to be executed we can use below command. this will package your product, but it will not
    execute any tests:
            - mvn package -Dmaven.test.skip=true                                                                                                                                 */

}

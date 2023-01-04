package com.example.addingtestingsupporttospringBootApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/* Adding Testing Support to SpringBoot Application : Springboot provides very good support for unit testing and integration testing in our applications.
	 	- create a new web application or a microservice using 'spring initializr' tool.(look at pom.xml file)

* spring-boot-starter-test : this is the dependency that we need to add to our project to be able to unit testing of our springboot application.
    - when we create a springboot project using 'spring initializr', it will add this dependency for us automatically. if not, we can search 'spring-boot-starter-test' dependency
      on 'mvnRepository' and add it into the pom.xml as a dependency.
	 	- we can see what are the all the libraries this one dependency(spring-boot-starter-test) will bring to our project using 'mvnRepository' site 'compile dependencies' section.
	 	  (refer img_01). Notice that this dependency also includes 'junit-jupiter', 'mockito-core' and 'mockito-junit-jupiter' Support. so if we add this one dependency to our project,
	 	  then we will also be able to use both 'junit-jupiter', 'mockito-core' and 'mockito-junit-jupiter' to test our application.
			And there are other libraries that will also be added to our project, For example, the 'json-path', 'assertj-core', 'hamcrest'. These are all very useful libraries to have
		  when testing our applications.

* spring-security-test : this will help us test our springboot application if it has 'Sprint security' enabled and if some of our API endpoints require user to be authenticated.
		- Now, if we also use 'spring security' in our project, then there is one more dependency that we'll need to add called 'spring-security-test'. if we add 'spring-security'
		  dependency for our project using 'spring initializr' tool this 'spring-security-test' dependency also will be added for us automatically by 'spring initializr'.

* So to be able to test our springboot application with JUnit and Mockito, these are the two main dependencies that we need to add. (springBoot-starter-test & spring-security-test) */

@SpringBootTest class AddingTestingSupportToSpringBootApplicationTests {

    @Test void contextLoads() {
    }

}

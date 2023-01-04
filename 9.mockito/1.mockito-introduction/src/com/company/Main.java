package com.company;

/* Mockito framework and how to use it to test our code:
    - Mockito is an open source testing framework created for Java. And it allows us to easily create 'test doubles' objects in unit tests. So Mockito is used in a unit tests, and
      it allows us to create 'test doubles' objects.
    - Mockito helps us to create fake objects and mock objects to let us test our method under test in isolation from its dependencies(objects).
    - 'test doubles' objects : This 'test doubles' is an object that can stand in for a real object in a test. similar to how a stunt, double stands for an actor in a movie.
      When writing unit test, we will come across objects that are called mock objects or fake objects or spy objects or a stub. and all of these different types of objects are
      called test doubles, and developers use them to temporarily replace a real object.
    - why do we need to replace a real object with a fake object : (refer img_1)let's say we have a method that we need to test. this method is called 'createUser' & it accepts two
      method arguments. One method argument is User object, and another method argument is a data access object that works with mysql database. 'createUser' method is our method
      under test, and we are interested in testing code we have written inside this method. But this method is using a data access object to save user in a database. The code
      inside the 'save' method is completely different code, and we are not interested in testing 'save' method. We are only interested in testing the 'createUser' method in isolation
      from a 'save' method. So if we do not do anything about it, then a real save method will be invoked, and it will actually attempt to save user in a database. and in the unit
      test we do not want this to happen unless we work on INTEGRATION TESTS, and we do want to make new records in the database. So for us to be able to test the 'createUser'
      method without invoking real code inside a save method, we will use mockito to create a mock object for mysql data access object. The mock object is not a real object, and it
      allows us to mock the behavior of its methods. So if we use mockito to create mock objects for mysql data access object, then we can mock the behavior of the 'save' method.
      DAO(Data Access Object): use to store data in the database.

         ex: For example,
         we can test 'createUser' method for use case when 'save' method throws an exception. and we can test how our method under test the 'createUser' method  works if the 'save'
         method throws an exception.
         and we can create a separate unit test method for a use case when save method returns null instead of a valid user ID for example.
         And in this case we can test if our method under test still works and still returns expected result. So if we use mock object instead of a real database access object,
         then we can control how our save method should behave like Should it return a valid value, or should it return null instead of valid value or should it throw an exception?
         Or we can even configure it to call the real method. usually when we use mock object instead of a real database access object, then no real 'save' method is called and no
         user is saved in actual real database.

* So because we can mock the behavior of 'save' method, we can test our 'createUser' method, that is method under test for different possible use cases.                                */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
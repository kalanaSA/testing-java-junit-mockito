package org.example.estore.data;

import org.example.estore.model.User;

import java.util.HashMap;
import java.util.Map;

// this is just a demonstration for store user details in memory instead of a database.
public class UsersRepositoryImpl implements UsersRepository {

    //as a temporary storage, we've used hash map.
    Map<String, User> users = new HashMap<>();

    @Override public boolean save(User user) {
        boolean returnValue = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            returnValue = true;
        }
        return returnValue;
    }

}

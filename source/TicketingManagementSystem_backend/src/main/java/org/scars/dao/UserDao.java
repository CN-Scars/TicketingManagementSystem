package org.scars.dao;

import org.scars.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    User getUserById(Long id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);

    List<User> searchUsers(String name, String email);
}

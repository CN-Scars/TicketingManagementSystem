package org.scars.server.dao;

import org.scars.pojo.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);

    List<User> searchUsers(String name, String email);
    String getUserNameById(Long id);
}

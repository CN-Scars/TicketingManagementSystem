package org.scars.server.dao;

import org.scars.pojo.entity.User;

import java.util.List;

public interface UserDao {
    /**
     * 获取所有用户
     *
     * @return
     */
    List<User> getUsers();

    /**
     * 根据id获取用户
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 添加用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 更新用户
     *
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 删除用户
     *
     * @param name
     * @param email
     * @return
     */

    List<User> searchUsers(String name, String email);

    /**
     * 根据用户名和邮箱搜索用户
     *
     * @param id
     * @return
     */
    String getUserNameById(Long id);
}

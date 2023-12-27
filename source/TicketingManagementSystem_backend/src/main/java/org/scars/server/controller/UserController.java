package org.scars.server.controller;

import org.scars.server.dao.UserDao;
import org.scars.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // Flutter跨域请求问题解决
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserDao userDao;

    /**
     * 获取所有用户信息
     * @return
     */
    @GetMapping
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    /**
     * 根据用户名和邮箱搜索用户
     * @param name
     * @param email
     * @return
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name, @RequestParam String email) {
        return userDao.searchUsers(name, email);
    }

    /**
     * 更新用户信息
     * @param user
     */
    @PutMapping
    public void updateUser(@RequestBody User user) {
        userDao.updateUser(user);
    }

    /**
     * 删除用户信息
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDao.deleteUser(id);
    }

    /**
     * 添加用户
     * @param user
     */
    @PostMapping
    public void addUser(@RequestBody User user) {
        userDao.addUser(user);
    }
}

package org.scars.controller;

import org.scars.dao.UserDao;
import org.scars.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // Flutter跨域请求问题解决
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserDao userDao;

    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    /**
     * 根据用户属性信息获取用户信息
     *
     * @param name
     * @param email
     * @return
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name, @RequestParam String email) {
        return userDao.searchUsers(name, email);
    }

    /**
     * 修改用户信息
     *
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

    @PostMapping
    public void addUser(@RequestBody User user) {
        userDao.addUser(user);
    }
}

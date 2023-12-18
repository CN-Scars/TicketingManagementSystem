package org.scars.dao.Impl;

import org.scars.dao.UserDao;
import org.scars.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private Connection connection;

    public UserDaoImpl() {
        // 建立数据库连接
        String url = "jdbc:mysql://localhost:3306/attraction_ticket_sales?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "password";

        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    // 从数据库中获取所有用户信息
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("UserID"));
                user.setName(resultSet.getString("Username"));
                user.setEmail(resultSet.getString("Email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        // 类似地实现
        return null;
    }

    @Override
    public void addUser(User user) {
        // 实现添加用户
        String sql = "INSERT INTO User(Username, Email) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("添加成功！");
            } else {
                System.out.println("添加失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        // 从user中获取id和其它属性实现修改
        String sql = "UPDATE User SET Username = ?, Email = ? WHERE UserID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setLong(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Long id) {
        // 实现删除用户
        String sql = "DELETE FROM User WHERE UserID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            if (rows > 0) {
                System.out.println("删除成功！");
            } else {
                System.out.println("删除失败！");
            }
            int rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> searchUsers(String name, String email) {
        // 条件查询用户
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User WHERE Username LIKE ? AND Email LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "%" + email + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("UserID"));
                    user.setName(resultSet.getString("Username"));
                    user.setEmail(resultSet.getString("Email"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}

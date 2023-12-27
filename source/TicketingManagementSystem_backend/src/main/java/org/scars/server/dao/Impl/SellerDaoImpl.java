package org.scars.server.dao.Impl;

import org.scars.server.dao.SellerDao;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class SellerDaoImpl implements SellerDao {
    private Connection connection;

    public SellerDaoImpl() {
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

    /**
     * 根据id获取售票员名称
     * @param id
     * @return
     */
    @Override
    public String getSellerNameById(Long id) {
        String SellerName = null;
        String sql = "SELECT Name FROM seller where SellerId="+id;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            SellerName= resultSet.getString("Name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SellerName;
    }
}

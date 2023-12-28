package org.scars.server.dao.Impl;

import org.scars.common.constant.StatusConstant;
import org.scars.pojo.vo.OrderVO;
import org.scars.server.dao.OrderDao;
import org.scars.server.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private UserDao userDao;
    @Autowired
    private TicketDaoImpl ticketDao;
    @Autowired
    private SellerDaoImpl sellerDao;

    private Connection connection;

    public OrderDaoImpl() {
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
     * 从数据库中获取所有订单信息
     *
     * @return
     */
    @Override
    public List<OrderVO> getOrders() {
        System.out.println("获取了所有订单信息");

        List<OrderVO> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order`";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                OrderVO order = OrderVO.builder()
                        .orderID(resultSet.getLong("OrderID"))
                        .userName(userDao.getUserNameById(resultSet.getLong("UserID")))
                        .ticketName(ticketDao.getTicketNameById(resultSet.getLong("TicketID")))
                        .quantity(resultSet.getInt("Quantity"))
                        .totalPrice(resultSet.getDouble("TotalPrice"))
                        .orderDate(new Date(resultSet.getTimestamp("OrderDate").getTime()))
                        .sellerName(sellerDao.getSellerNameById(resultSet.getLong("SellerId")))
                        .state(resultSet.getInt("State"))
                        .build();
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 退单
     *
     * @param id
     */
    @Override
    public void refundOrder(Long id) {
        System.out.println("进行了退单");

        String sql = "UPDATE `order` SET State=? WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, StatusConstant.DISABLE);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "Select TicketID from `order` where OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            sql = "UPDATE ticket SET Stock=Stock+1 WHERE TicketID = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setObject(1, resultSet.getObject(1));
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索订单
     *
     * @param orderID
     * @param userID
     * @param orderDate
     * @param sellerId
     * @param state
     * @return
     */
    @Override
    public List<OrderVO> searchOrders(Integer orderID, String userName, String ticketName, Date orderDate, String sellerName, Integer state) {
        List<OrderVO> orders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM `order` WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (userName != null && !userName.isEmpty()) {
            sql.append(" AND UserID IN (SELECT UserID FROM `user` WHERE Username LIKE ?)");
            params.add("%" + userName + "%");
        }
        if (sellerName != null && !sellerName.isEmpty()) {
            sql.append(" AND SellerId IN (SELECT SellerId FROM seller WHERE Name LIKE ?)");
            params.add("%" + sellerName + "%");
        }
        if (ticketName != null && !ticketName.isEmpty()) {
            sql.append(" AND TicketID IN (SELECT TicketID FROM ticket WHERE Ticketname LIKE ?)");
            params.add("%" + ticketName + "%");
        }

        if (orderDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(orderDate);
            sql.append(" AND OrderDate = ?");
            params.add(formattedDate);
        }

        if (orderID != null) {
            sql.append(" AND OrderID = ?");
            params.add(orderID);
        }

        if (state != null) {
            sql.append(" AND State = ?");
            params.add(state);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderVO order = OrderVO.builder()
                            .orderID(resultSet.getLong("OrderID"))
                            .userName(userDao.getUserNameById(resultSet.getLong("UserID")))
                            .ticketName(ticketDao.getTicketNameById(resultSet.getLong("TicketID")))
                            .quantity(resultSet.getInt("Quantity"))
                            .totalPrice(resultSet.getDouble("TotalPrice"))
                            .orderDate(new Date(resultSet.getTimestamp("OrderDate").getTime()))
                            .sellerName(sellerDao.getSellerNameById(resultSet.getLong("SellerId")))
                            .state(resultSet.getInt("State"))
                            .build();
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}

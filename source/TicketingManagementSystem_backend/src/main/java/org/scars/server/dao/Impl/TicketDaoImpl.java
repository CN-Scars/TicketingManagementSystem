package org.scars.server.dao.Impl;

import org.scars.server.dao.TicketDao;
import org.scars.pojo.entity.Ticket;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketDaoImpl implements TicketDao {
    private Connection connection;

    public TicketDaoImpl() {
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
     * 从数据库中获取所有门票信息
     *
     * @return
     */
    @Override
    public List<Ticket> getTickets() {
        List<Ticket> Tickets = new ArrayList<>();
        String sql = "SELECT * FROM ticket";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketID(resultSet.getLong("TicketID"));
                ticket.setTicketName(resultSet.getString("TicketName"));
                ticket.setStock(resultSet.getInt("Stock"));
                ticket.setPrice(resultSet.getDouble("Price"));
                Tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Tickets;
    }

    /**
     * 添加门票
     *
     * @param ticket
     */
    @Override
    public void addTicket(Ticket ticket) {
        System.out.println("添加了门票！" + ticket);

        String sql = "insert into ticket(TicketID, Price, Stock, TicketName) VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, ticket.getTicketID());
            preparedStatement.setObject(2, ticket.getPrice());
            preparedStatement.setObject(3, ticket.getStock());
            preparedStatement.setObject(4, ticket.getTicketName());
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

    /**
     * 更新门票
     *
     * @param ticket
     */
    @Override
    public void updateTicket(Ticket ticket) {
        String sql = "update ticket set Price=?,Stock=?,TicketName=? where TicketID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, ticket.getPrice());
            preparedStatement.setObject(2, ticket.getStock());
            preparedStatement.setObject(3, ticket.getTicketName());
            preparedStatement.setObject(4, ticket.getTicketID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除门票
     *
     * @param id
     */
    @Override
    public void deleteTicket(Long id) {
        System.out.println("删除了门票！" + id);

        String sql = "delete from ticket where TicketID =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("删除成功！");
            } else {
                System.out.println("删除失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索门票
     *
     * @param ticketName
     * @param price
     * @param stock
     * @return
     */
    @Override
    public List<Ticket> searchTickets(String ticketName, Double price, Integer stock) {
        List<Ticket> tickets = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ticket WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (ticketName != null && !ticketName.isEmpty()) {
            sql.append(" AND ticketName LIKE ?");
            params.add("%" + ticketName + "%");
        }

        if (price != null) {
            sql.append(" AND price = ?");
            params.add(price);
        }

        if (stock != null) {
            sql.append(" AND stock = ?");
            params.add(stock);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setPrice(resultSet.getDouble("Price"));
                    ticket.setStock(resultSet.getInt("Stock"));
                    ticket.setTicketName(resultSet.getString("TicketName"));
                    ticket.setTicketID(resultSet.getLong("TicketID"));
                    tickets.add(ticket);
                }
            }

            System.out.println(preparedStatement.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    /**
     * 根据门票id获取门票名
     *
     * @param id
     * @return
     */
    @Override
    public String getTicketNameById(Long id) {
        String TicketName = null;
        String sql = "SELECT TicketName FROM ticket where TicketID=" + id;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            TicketName = resultSet.getString("TicketName");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TicketName;
    }
}

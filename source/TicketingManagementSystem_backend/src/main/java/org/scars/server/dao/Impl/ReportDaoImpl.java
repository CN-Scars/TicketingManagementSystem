package org.scars.server.dao.Impl;

import org.scars.pojo.vo.ClerkReport;
import org.scars.pojo.vo.Report;
import org.scars.server.dao.ReportDao;
import org.scars.server.dao.SellerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class ReportDaoImpl implements ReportDao {
    @Autowired
    private SellerDao sellerDao;

    private Connection connection;

    public ReportDaoImpl() {
        // �������ݿ�����
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
     * ��ȡ��Ʊ���۱���
     *
     * @return
     */
    @Override
    public List<Report> getReports() {
//        List<Report> reports = new ArrayList<>();
//        String query = "SELECT OrderDate, TotalPrice, SUM(Quantity) as Quantity FROM `order` WHERE state = 1 GROUP BY OrderDate, TotalPrice";
//
//        try (Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(query)) {
//
//            Date currentDate = null;
//            Report report = null;
//            while (resultSet.next()) {
//                Date date = resultSet.getDate("OrderDate");
//                if (!date.equals(currentDate)) {
//                    if (report != null) {
//                        reports.add(report);
//                    }
//                    currentDate = date;
//                    report = new Report();
//                    report.setDate(currentDate);
//                    report.setSalesByPrice(new HashMap<>());
//                }
//                Double price = resultSet.getDouble("TotalPrice");
//                int quantity = resultSet.getInt("Quantity");
//                report.getSalesByPrice().put(price, quantity);
//                report.setTotalSales(report.getTotalSales() + quantity);
//            }
//            if (report != null) {
//                reports.add(report);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(reports);
//
//        return reports;

        List<Report> reports = new ArrayList<>();
        String query = "SELECT OrderDate, TotalPrice, SUM(Quantity) as Quantity FROM `order` WHERE state = 1 GROUP BY OrderDate, TotalPrice";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Date currentDate = null;
            Report report = null;
            while (resultSet.next()) {
                Date date = resultSet.getDate("OrderDate");
                if (!date.equals(currentDate)) {
                    if (report != null) {
                        reports.add(report);
                    }
                    currentDate = date;
                    report = new Report();
                    report.setDate(currentDate);
                    report.setSalesByPrice(new TreeMap<>());  // ʹ��TreeMap�滻HashMap
                }
                Double price = resultSet.getDouble("TotalPrice");
                int quantity = resultSet.getInt("Quantity");
                report.getSalesByPrice().put(price, quantity);
                report.setTotalSales(report.getTotalSales() + quantity);
            }
            if (report != null) {
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(reports);

        return reports;
    }

    /**
     * ��ȡ��ƱԱ���۱���
     *
     * @return
     */
    @Override
    public List<ClerkReport> getClerkReports() {
        List<ClerkReport> clerkReports = new ArrayList<>();
        String query = "SELECT SellerId, OrderDate, SUM(TotalPrice) as Charge FROM `order` WHERE State = 1 GROUP BY SellerId, OrderDate";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // �ӽ�����л�ȡ���ݲ�����ClerkReport����
                ClerkReport clerkReport = createClerkReportFromResultSet(resultSet);
                clerkReports.add(clerkReport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(clerkReports);

        return clerkReports;
    }

    /**
     * �ӽ�����л�ȡ���ݲ�����ClerkReport����
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private ClerkReport createClerkReportFromResultSet(ResultSet resultSet) throws SQLException {
        String clerkName = sellerDao.getSellerNameById(resultSet.getLong("SellerId"));
        Date date = resultSet.getDate("OrderDate");
        Double charge = resultSet.getDouble("Charge");

        System.out.println(clerkName);

        return ClerkReport.builder()
                .clerkName(clerkName)
                .date(date)
                .charge(charge)
                .build();
    }
}

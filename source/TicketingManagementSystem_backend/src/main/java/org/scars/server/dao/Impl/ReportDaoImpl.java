package org.scars.server.dao.Impl;

import org.scars.common.constant.StatusConstant;
import org.scars.pojo.vo.ClerkReportVO;
import org.scars.pojo.vo.ReportVO;
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
    public List<ReportVO> getReports() {
        List<ReportVO> reportVOS = new ArrayList<>();
        String query = "SELECT OrderDate, TotalPrice, SUM(Quantity) as Quantity FROM `order` WHERE state = " + StatusConstant.ENABLE +
                " GROUP BY OrderDate, TotalPrice";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Date currentDate = null;
            ReportVO reportVO = null;
            while (resultSet.next()) {
                Date date = resultSet.getDate("OrderDate");
                if (!date.equals(currentDate)) {
                    if (reportVO != null) {
                        reportVOS.add(reportVO);
                    }
                    currentDate = date;
                    reportVO = new ReportVO();
                    reportVO.setDate(currentDate);
                    reportVO.setSalesByPrice(new TreeMap<>());  // ʹ��TreeMap�滻HashMap
                }
                Double price = resultSet.getDouble("TotalPrice");
                int quantity = resultSet.getInt("Quantity");
                reportVO.getSalesByPrice().put(price, quantity);
                reportVO.setTotalSales(reportVO.getTotalSales() + quantity);
            }
            if (reportVO != null) {
                reportVOS.add(reportVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportVOS;
    }

    /**
     * ��ȡ��ƱԱ���۱���
     *
     * @return
     */
    @Override
    public List<ClerkReportVO> getClerkReports() {
        Map<String, ClerkReportVO> clerkReportMap = new HashMap<>();
        String query = "SELECT SellerId, OrderDate, SUM(TotalPrice) as TotalCharge FROM `order` WHERE State = " + StatusConstant.ENABLE +
                " GROUP BY SellerId, OrderDate";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String clerkName = sellerDao.getSellerNameById(resultSet.getLong("SellerId"));
                Date date = resultSet.getDate("OrderDate");
                Double totalCharge = resultSet.getDouble("TotalCharge");

                String key = clerkName + date.toString();
                if (clerkReportMap.containsKey(key)) {
                    ClerkReportVO existingReport = clerkReportMap.get(key);
                    existingReport.setCharge(existingReport.getCharge() + totalCharge);
                } else {
                    ClerkReportVO newReport = ClerkReportVO.builder()
                            .clerkName(clerkName)
                            .date(date)
                            .charge(totalCharge)
                            .build();
                    clerkReportMap.put(key, newReport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(clerkReportMap.values());
    }

    /**
     * �ӽ�����л�ȡ���ݲ�����ClerkReport����
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private ClerkReportVO createClerkReportFromResultSet(ResultSet resultSet) throws SQLException {
        String clerkName = sellerDao.getSellerNameById(resultSet.getLong("SellerId"));
        Date date = resultSet.getDate("OrderDate");
        Double totalCharge = resultSet.getDouble("TotalCharge");  // ��ȡ��ƱԱ����������۶�

        return ClerkReportVO.builder()
                .clerkName(clerkName)
                .date(date)
                .charge(totalCharge)  // ������ƱԱ����������۶�
                .build();
    }
}

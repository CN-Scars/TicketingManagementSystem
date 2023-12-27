package org.scars.pojo.entity;

import org.springframework.data.relational.core.sql.In;

public class Order {
    private Integer orderID;    // 订单ID
    private Integer userID; // 用户ID
    private Integer ticketID;   // 门票ID
    private Integer quantity;   // 购买数量
    private Double totalPrice;  // 总价
    private String orderDate;   // 下单日期
    private Integer sellerId;   // 售票员ID
    private Integer state;  // 订单状态，1为已购买，0为已退票

    public Order(int orderID, int userID, int ticketID, int quantity, double totalPrice, String orderDate, int sellerId, int state) {
        this.orderID = orderID;
        this.userID = userID;
        this.ticketID = ticketID;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.sellerId = sellerId;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", userID=" + userID +
                ", ticketID=" + ticketID +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", sellerId=" + sellerId +
                ", state=" + state +
                '}';
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

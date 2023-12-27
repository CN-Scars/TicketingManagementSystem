package org.scars.pojo.entity;

public class Ticket {
    private Long ticketID;
    private String ticketName;
    private Double price;
    private Integer stock;

    public Ticket() {
    }

    public Ticket(Long ticketID, String ticketName, double price, Integer stock) {
        this.ticketID = ticketID;
        this.ticketName = ticketName;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", ticketName='" + ticketName + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}

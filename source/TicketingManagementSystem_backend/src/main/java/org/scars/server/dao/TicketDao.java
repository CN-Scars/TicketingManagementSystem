package org.scars.server.dao;

import org.scars.pojo.entity.Ticket;

import java.util.List;

public interface TicketDao {
    List<Ticket> getTickets();
    void addTicket(Ticket ticket);
    void updateTicket(Ticket ticket);
    void deleteTicket(Long id);
    List<Ticket> searchTickets(String ticketName, Double price, Integer stock);
    String getTicketNameById(Long id);
}

package org.scars.server.dao;

import org.scars.pojo.entity.Ticket;

import java.util.List;

public interface TicketDao {
    /**
     * 获取所有票
     *
     * @return
     */
    List<Ticket> getTickets();

    /**
     * 根据id获取票
     *
     * @param ticket
     */
    void addTicket(Ticket ticket);

    /**
     * 添加票
     *
     * @param ticket
     */
    void updateTicket(Ticket ticket);

    /**
     * 更新票
     *
     * @param id
     */
    void deleteTicket(Long id);

    /**
     * 删除票
     *
     * @param ticketName
     * @param price
     * @param stock
     * @return
     */
    List<Ticket> searchTickets(String ticketName, Double price, Integer stock);

    /**
     * 根据票名搜索票
     *
     * @param id
     * @return
     */
    String getTicketNameById(Long id);
}

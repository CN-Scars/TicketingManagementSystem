package org.scars.server.controller;

import org.scars.server.dao.TicketDao;
import org.scars.pojo.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // Flutter跨域请求问题解决
@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketDao ticketDao;

    /**
     * 获取所有门票
     *
     * @return
     */
    @GetMapping
    public List<Ticket> getTickets() {
        return ticketDao.getTickets();
    }

    /**
     * 添加门票
     *
     * @param ticket
     */
    @PostMapping
    public void addTicket(@RequestBody Ticket ticket) {
        ticketDao.addTicket(ticket);
    }

    /**
     * 搜索门票
     *
     * @param ticketName
     * @param price
     * @param stock
     * @return
     */
    @GetMapping("/search")
    public List<Ticket> searchTickets(@RequestParam(required = false) String ticketName, @RequestParam(required = false) Double price, @RequestParam(required = false) Integer stock) {
        return ticketDao.searchTickets(ticketName, price, stock);
    }

    /**
     * 更新门票
     *
     * @param ticket
     */
    @PutMapping
    public void updateTicket(@RequestBody Ticket ticket) {
        ticketDao.updateTicket(ticket);
    }

    /**
     * 删除门票
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketDao.deleteTicket(id);
    }
}

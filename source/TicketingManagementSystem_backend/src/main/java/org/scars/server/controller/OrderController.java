package org.scars.server.controller;

import org.scars.pojo.vo.OrderVO;
import org.scars.server.dao.OrderDao;
import org.scars.pojo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")  // Flutter跨域请求问题解决
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    /**
     * 获取所有订单信息
     *
     * @return
     */
    @GetMapping
    public List<OrderVO> getOrders() {
        return orderDao.getOrders();
    }

    /**
     * 退单
     *
     * @param id
     */
    @PutMapping("/{id}")
    public void refundOrder(@PathVariable Long id) {
        orderDao.refundOrder(id);
    }

    @GetMapping("/search")
    public List<OrderVO> searchOrders(@RequestParam(required = false) Integer orderID, @RequestParam(required = false) String userName, @RequestParam(required = false) String ticketName, @RequestParam(required = false) String orderDate, @RequestParam(required = false) String sellerName, @RequestParam(required = false) Integer state) {
        Date date = null;
        if (orderDate != null && !orderDate.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            try {
                date = format.parse(orderDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return orderDao.searchOrders(orderID, userName, ticketName, date, sellerName, state);
    }
}

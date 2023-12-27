package org.scars.server.dao;

import org.scars.pojo.entity.Order;
import org.scars.pojo.vo.OrderVO;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    List<OrderVO> getOrders();

    void refundOrder(Long id);

    List<OrderVO> searchOrders(Integer orderID, String userName, String ticketName, Date orderDate, String sellerName, Integer state);
}

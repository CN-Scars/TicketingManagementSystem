package org.scars.server.dao;

import org.scars.pojo.vo.OrderVO;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    /**
     * 获取所有订单
     *
     * @return
     */
    List<OrderVO> getOrders();

    /**
     * 根据id获取订单
     *
     * @param id
     */

    void refundOrder(Long id);

    /**
     * 退票
     *
     * @param orderID
     * @param userName
     * @param ticketName
     * @param orderDate
     * @param sellerName
     * @param state
     * @return
     */

    List<OrderVO> searchOrders(Integer orderID, String userName, String ticketName, Date orderDate, String sellerName, Integer state);
}

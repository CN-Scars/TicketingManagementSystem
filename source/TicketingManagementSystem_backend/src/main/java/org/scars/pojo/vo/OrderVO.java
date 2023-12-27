package org.scars.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderVO {
    private Long orderID;    // 订单ID
    private String userName;    // 用户名
    private String ticketName;  // 票名
    private Integer quantity;  // 购买数量
    private Double totalPrice;  // 总价
    private Date orderDate;   // 下单时间
    private String sellerName;  // 售票员姓名
    private Integer state;  // 订单状态
}

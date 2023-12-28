package org.scars.server.dao;

public interface SellerDao {
    /**
     * 获取所有卖家
     *
     * @param id
     * @return
     */
    String getSellerNameById(Long id);
}

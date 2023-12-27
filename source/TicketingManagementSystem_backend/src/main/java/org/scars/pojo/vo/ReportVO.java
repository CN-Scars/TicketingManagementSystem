package org.scars.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
//@Builder
public class ReportVO {
    private Date date;
    private Integer totalSales;
    private Map<Double, Integer> salesByPrice;

    public ReportVO() {
        this.totalSales = 0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public Map<Double, Integer> getSalesByPrice() {
        return salesByPrice;
    }

    public void setSalesByPrice(Map<Double, Integer> salesByPrice) {
        this.salesByPrice = salesByPrice;
    }
}

package org.scars.pojo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
//@Builder
public class Report {
    private Date date;
    private Integer totalSales;
    private Map<Double, Integer> salesByPrice;

    public Report() {
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

package org.scars.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClerkReportVO {
    private String clerkName;
    private Date date;
    private Double charge;
}

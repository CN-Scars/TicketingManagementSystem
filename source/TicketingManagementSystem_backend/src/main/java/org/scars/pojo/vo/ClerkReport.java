package org.scars.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClerkReport {
    private String clerkName;
    private Date date;
    private double charge;
}

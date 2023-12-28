package org.scars.server.dao;

import org.scars.pojo.vo.ClerkReportVO;
import org.scars.pojo.vo.ReportVO;

import java.util.List;

public interface ReportDao {
    /**
     * 获取所有报表
     *
     * @return
     */
    List<ReportVO> getReports();

    /**
     * 获取所有售票员报表
     *
     * @return
     */
    List<ClerkReportVO> getClerkReports();
}

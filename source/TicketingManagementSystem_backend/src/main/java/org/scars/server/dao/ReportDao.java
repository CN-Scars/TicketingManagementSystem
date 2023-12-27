package org.scars.server.dao;

import org.scars.pojo.vo.ClerkReportVO;
import org.scars.pojo.vo.ReportVO;

import java.util.List;

public interface ReportDao {
    List<ReportVO> getReports();
    List<ClerkReportVO> getClerkReports();
}

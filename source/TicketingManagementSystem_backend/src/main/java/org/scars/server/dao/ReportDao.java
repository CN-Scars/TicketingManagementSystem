package org.scars.server.dao;

import org.scars.pojo.vo.ClerkReport;
import org.scars.pojo.vo.Report;

import java.util.List;

public interface ReportDao {
    List<Report> getReports();
    List<ClerkReport> getClerkReports();
}

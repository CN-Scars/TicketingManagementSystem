package org.scars.server.controller;

import org.scars.pojo.vo.ClerkReportVO;
import org.scars.pojo.vo.ReportVO;
import org.scars.server.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")  // Flutter跨域请求问题解决
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportDao reportDao;

    /**
     * 获取门票销售报告
     * @return
     */
    @GetMapping("/reports")
    public List<ReportVO> getReports() {
        System.out.println("请求了获取门票销售报告");

        return reportDao.getReports();
    }

    /**
     * 获取售票员销售报告
     * @return
     */
    @GetMapping("/clerk_reports")
    public List<ClerkReportVO> getClerkReports() {
        System.out.println("请求了获取售票员销售报告");

        return reportDao.getClerkReports();
    }
}

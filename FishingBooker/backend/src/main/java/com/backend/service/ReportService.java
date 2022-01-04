package com.backend.service;

import com.backend.model.Report;
import com.backend.repository.IReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    IReportRepository reportRepository;

    public ReportService() {}

    public void save(Report report) {
        this.reportRepository.save(report);
    }
}

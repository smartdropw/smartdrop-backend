package com.smart.drop.analytics.application.services;

import com.smart.drop.analytics.domain.model.entities.Metric;
import com.smart.drop.analytics.domain.model.entities.Report;
import com.smart.drop.analytics.domain.model.repositories.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(Integer userId,
                               String title,
                               String content,
                               String type,
                               List<MetricDraft> metricDrafts) {
        Report savedReport = reportRepository.saveReport(Report.create(userId, title, content, type));

        List<Metric> savedMetrics = reportRepository.saveMetrics(
                metricDrafts == null
                        ? List.of()
                        : metricDrafts.stream()
                        .map(draft -> Metric.create(userId, savedReport.reportId(), draft.name(), draft.value(), draft.unit()))
                        .toList()
        );

        return savedReport.withMetrics(savedMetrics);
    }

    @Transactional(readOnly = true)
    public Report getReportById(Integer reportId) {
        Report report = reportRepository.findReportById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found: " + reportId));
        List<Metric> metrics = reportRepository.findMetricsByReportId(reportId);
        return report.withMetrics(metrics);
    }

    @Transactional(readOnly = true)
    public List<Report> getReportsByUserId(Integer userId) {
        return reportRepository.findReportsByUserId(userId).stream()
                .map(report -> report.withMetrics(reportRepository.findMetricsByReportId(report.reportId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Report> getAllReports() {
        return reportRepository.findAllReports().stream()
                .map(report -> report.withMetrics(reportRepository.findMetricsByReportId(report.reportId())))
                .toList();
    }

    public record MetricDraft(String name, BigDecimal value, String unit) {
    }
}


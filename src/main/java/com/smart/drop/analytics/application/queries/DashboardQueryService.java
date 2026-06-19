package com.smart.drop.analytics.application.queries;

import com.smart.drop.analytics.domain.model.entities.Metric;
import com.smart.drop.analytics.domain.model.entities.Report;
import com.smart.drop.analytics.domain.model.repositories.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardQueryService {

    private final ReportRepository reportRepository;

    public DashboardQueryService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public DashboardReadModel getDashboardByUserId(Integer userId) {
        List<Report> reports = reportRepository.findReportsByUserId(userId);
        List<Metric> metrics = reportRepository.findMetricsByUserId(userId);

        BigDecimal averageMetricValue = metrics.stream()
                .map(Metric::value)
                .filter(value -> value != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long countedMetrics = metrics.stream().map(Metric::value).filter(value -> value != null).count();
        if (countedMetrics > 0) {
            averageMetricValue = averageMetricValue.divide(BigDecimal.valueOf(countedMetrics), 2, RoundingMode.HALF_UP);
        }

        LocalDateTime lastReportGeneratedAt = reports.stream()
                .map(Report::generatedAt)
                .filter(value -> value != null)
                .max(Comparator.naturalOrder())
                .orElse(null);

        List<DashboardReadModel.MetricCard> recentMetrics = metrics.stream()
                .sorted(Comparator.comparing(Metric::recordedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(10)
                .map(metric -> new DashboardReadModel.MetricCard(
                        metric.metricId(),
                        metric.reportId(),
                        metric.name(),
                        metric.value(),
                        metric.unit(),
                        metric.recordedAt()
                ))
                .toList();

        List<DashboardReadModel.ReportCard> recentReports = reports.stream()
                .sorted(Comparator.comparing(Report::generatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(10)
                .map(report -> new DashboardReadModel.ReportCard(
                        report.reportId(),
                        report.title(),
                        report.type(),
                        report.generatedAt(),
                        report.metrics().size()
                ))
                .toList();

        return new DashboardReadModel(
                userId,
                reports.size(),
                metrics.size(),
                countedMetrics > 0 ? averageMetricValue : BigDecimal.ZERO,
                lastReportGeneratedAt,
                recentMetrics,
                recentReports
        );
    }
}


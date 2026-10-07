package com.smart.drop.analytics.domain.model.repositories;

import com.smart.drop.analytics.domain.model.entities.Metric;
import com.smart.drop.analytics.domain.model.entities.Report;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository for reports and metrics.
 */
public interface ReportRepository {

    Report saveReport(Report report);

    Optional<Report> findReportById(Integer reportId);

    List<Report> findReportsByUserId(Integer userId);

    List<Report> findAllReports();

    Metric saveMetric(Metric metric);

    List<Metric> saveMetrics(List<Metric> metrics);

    List<Metric> findMetricsByUserId(Integer userId);

    List<Metric> findMetricsByReportId(Integer reportId);
}


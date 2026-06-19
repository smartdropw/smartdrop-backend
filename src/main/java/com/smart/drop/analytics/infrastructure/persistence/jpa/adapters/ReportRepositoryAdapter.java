package com.smart.drop.analytics.infrastructure.persistence.jpa.adapters;

import com.smart.drop.analytics.domain.model.entities.Metric;
import com.smart.drop.analytics.domain.model.entities.Report;
import com.smart.drop.analytics.domain.model.repositories.ReportRepository;
import com.smart.drop.analytics.infrastructure.persistence.jpa.entities.MetricEntity;
import com.smart.drop.analytics.infrastructure.persistence.jpa.entities.ReportEntity;
import com.smart.drop.analytics.infrastructure.persistence.jpa.repositories.MetricJpaRepository;
import com.smart.drop.analytics.infrastructure.persistence.jpa.repositories.ReportJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ReportRepositoryAdapter implements ReportRepository {

    private final ReportJpaRepository reportJpaRepository;
    private final MetricJpaRepository metricJpaRepository;

    public ReportRepositoryAdapter(ReportJpaRepository reportJpaRepository,
                                   MetricJpaRepository metricJpaRepository) {
        this.reportJpaRepository = reportJpaRepository;
        this.metricJpaRepository = metricJpaRepository;
    }

    @Override
    public Report saveReport(Report report) {
        ReportEntity entity = new ReportEntity();
        entity.setReportId(report.reportId());
        entity.setUserId(report.userId());
        entity.setTitle(report.title());
        entity.setContent(report.content());
        entity.setType(report.type());
        entity.setGeneratedAt(report.generatedAt() == null ? LocalDateTime.now() : report.generatedAt());
        ReportEntity saved = reportJpaRepository.save(entity);
        return toReportDomain(saved, List.of());
    }

    @Override
    public Optional<Report> findReportById(Integer reportId) {
        return reportJpaRepository.findById(reportId)
                .map(entity -> toReportDomain(entity, findMetricsByReportId(entity.getReportId())));
    }

    @Override
    public List<Report> findReportsByUserId(Integer userId) {
        return reportJpaRepository.findByUserId(userId).stream()
                .map(entity -> toReportDomain(entity, entity.getMetrics().stream().map(this::toMetricDomain).toList()))
                .toList();
    }

    @Override
    public List<Report> findAllReports() {
        return reportJpaRepository.findAll().stream()
                .map(entity -> toReportDomain(entity, findMetricsByReportId(entity.getReportId())))
                .toList();
    }

    @Override
    public Metric saveMetric(Metric metric) {
        MetricEntity entity = toMetricEntity(metric);
        MetricEntity saved = metricJpaRepository.save(entity);
        return toMetricDomain(saved);
    }

    @Override
    public List<Metric> saveMetrics(List<Metric> metrics) {
        return metrics.stream().map(this::saveMetric).toList();
    }

    @Override
    public List<Metric> findMetricsByUserId(Integer userId) {
        return metricJpaRepository.findByUserId(userId).stream().map(this::toMetricDomain).toList();
    }

    @Override
    public List<Metric> findMetricsByReportId(Integer reportId) {
        return metricJpaRepository.findByReport_ReportId(reportId).stream().map(this::toMetricDomain).toList();
    }

    private MetricEntity toMetricEntity(Metric metric) {
        MetricEntity entity = new MetricEntity();
        entity.setMetricId(metric.metricId());
        entity.setUserId(metric.userId());
        entity.setName(metric.name());
        entity.setValue(metric.value());
        entity.setUnit(metric.unit());
        entity.setRecordedAt(metric.recordedAt() == null ? LocalDateTime.now() : metric.recordedAt());

        if (metric.reportId() != null) {
            ReportEntity reportEntity = reportJpaRepository.findById(metric.reportId())
                    .orElseThrow(() -> new IllegalArgumentException("Report not found: " + metric.reportId()));
            entity.setReport(reportEntity);
        }
        return entity;
    }

    private Report toReportDomain(ReportEntity entity, List<Metric> metrics) {
        return new Report(
                entity.getReportId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getType(),
                entity.getGeneratedAt(),
                metrics
        );
    }

    private Metric toMetricDomain(MetricEntity entity) {
        return new Metric(
                entity.getMetricId(),
                entity.getUserId(),
                entity.getReport() == null ? null : entity.getReport().getReportId(),
                entity.getName(),
                entity.getValue(),
                entity.getUnit(),
                entity.getRecordedAt()
        );
    }
}


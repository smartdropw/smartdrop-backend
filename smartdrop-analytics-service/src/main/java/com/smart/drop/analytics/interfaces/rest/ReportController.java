package com.smart.drop.analytics.interfaces.rest;

import com.smart.drop.analytics.application.services.ReportService;
import com.smart.drop.analytics.domain.model.entities.Metric;
import com.smart.drop.analytics.domain.model.entities.Report;
import com.smart.drop.analytics.interfaces.rest.dto.CreateReportRequest;
import com.smart.drop.analytics.interfaces.rest.dto.MetricResponse;
import com.smart.drop.analytics.interfaces.rest.dto.ReportResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportResponse> create(@RequestBody CreateReportRequest request) {
        Report created = reportService.createReport(
                request.userId(),
                request.title(),
                request.content(),
                request.type(),
                request.metrics() == null
                        ? List.of()
                        : request.metrics().stream()
                        .map(metric -> new ReportService.MetricDraft(metric.name(), metric.value(), metric.unit()))
                        .toList()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponse> getById(@PathVariable Integer reportId) {
        Report report = reportService.getReportById(reportId);
        return ResponseEntity.ok(toResponse(report));
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAll(@RequestParam(required = false) Integer userId) {
        List<ReportResponse> data = (userId == null ? reportService.getAllReports() : reportService.getReportsByUserId(userId))
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(data);
    }

    private ReportResponse toResponse(Report report) {
        return new ReportResponse(
                report.reportId(),
                report.userId(),
                report.title(),
                report.content(),
                report.type(),
                report.generatedAt(),
                report.metrics().stream().map(this::toMetricResponse).toList()
        );
    }

    private MetricResponse toMetricResponse(Metric metric) {
        return new MetricResponse(
                metric.metricId(),
                metric.userId(),
                metric.reportId(),
                metric.name(),
                metric.value(),
                metric.unit(),
                metric.recordedAt()
        );
    }
}


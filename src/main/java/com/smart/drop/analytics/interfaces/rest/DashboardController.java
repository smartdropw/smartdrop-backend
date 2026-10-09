package com.smart.drop.analytics.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.smart.drop.analytics.application.queries.DashboardQueryService;
import com.smart.drop.analytics.application.queries.DashboardReadModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "4. Analytics — DashboardController", description = "Consolidado de KPIs operativos y telemetría en tiempo real")
@RestController
@RequestMapping("/api/v1/analytics/dashboard")
public class DashboardController {

    private final DashboardQueryService dashboardQueryService;

    public DashboardController(DashboardQueryService dashboardQueryService) {
        this.dashboardQueryService = dashboardQueryService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DashboardReadModel> getDashboardByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(dashboardQueryService.getDashboardByUserId(userId));
    }
}


package com.smart.drop.analytics.interfaces.rest;

import com.smart.drop.analytics.application.queries.DashboardQueryService;
import com.smart.drop.analytics.application.queries.DashboardReadModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


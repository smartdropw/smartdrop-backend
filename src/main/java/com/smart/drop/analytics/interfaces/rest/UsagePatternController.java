package com.smart.drop.analytics.interfaces.rest;

import com.smart.drop.analytics.application.services.UsagePatternService;
import com.smart.drop.analytics.domain.model.entities.UsagePattern;
import com.smart.drop.analytics.interfaces.rest.dto.CreateUsagePatternRequest;
import com.smart.drop.analytics.interfaces.rest.dto.UpdateUsagePatternStatusRequest;
import com.smart.drop.analytics.interfaces.rest.dto.UsagePatternResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics/patterns")
public class UsagePatternController {

    private final UsagePatternService usagePatternService;

    public UsagePatternController(UsagePatternService usagePatternService) {
        this.usagePatternService = usagePatternService;
    }

    @PostMapping
    public ResponseEntity<UsagePatternResponse> create(@RequestBody CreateUsagePatternRequest request) {
        UsagePattern created = usagePatternService.createPattern(
                request.userId(),
                request.type(),
                request.title(),
                request.desc(),
                request.location(),
                request.impact(),
                request.tag(),
                request.time()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @GetMapping("/{patternId}")
    public ResponseEntity<UsagePatternResponse> getById(@PathVariable Integer patternId) {
        return usagePatternService.getById(patternId)
                .map(pattern -> ResponseEntity.ok(toResponse(pattern)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UsagePatternResponse>> getByUserId(@PathVariable Integer userId) {
        List<UsagePatternResponse> data = usagePatternService.getByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(data);
    }

    @GetMapping
    public ResponseEntity<List<UsagePatternResponse>> getAll() {
        List<UsagePatternResponse> data = usagePatternService.getAll()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{patternId}/status")
    public ResponseEntity<UsagePatternResponse> updateStatus(
            @PathVariable Integer patternId,
            @RequestBody UpdateUsagePatternStatusRequest request
    ) {
        UsagePattern updated = usagePatternService.updateStatus(patternId, request.tag());
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{patternId}")
    public ResponseEntity<Void> delete(@PathVariable Integer patternId) {
        usagePatternService.deleteById(patternId);
        return ResponseEntity.noContent().build();
    }

    private UsagePatternResponse toResponse(UsagePattern pattern) {
        return new UsagePatternResponse(
                pattern.patternId(),
                pattern.userId(),
                pattern.type(),
                pattern.title(),
                pattern.desc(),
                pattern.location(),
                pattern.impact(),
                pattern.tag(),
                pattern.time()
        );
    }
}

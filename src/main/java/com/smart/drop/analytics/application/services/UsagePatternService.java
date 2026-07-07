package com.smart.drop.analytics.application.services;

import com.smart.drop.analytics.domain.model.entities.UsagePattern;
import com.smart.drop.analytics.domain.model.repositories.UsagePatternRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsagePatternService {

    private final UsagePatternRepository usagePatternRepository;

    public UsagePatternService(UsagePatternRepository usagePatternRepository) {
        this.usagePatternRepository = usagePatternRepository;
    }

    public UsagePattern createPattern(Integer userId, String type, String title, String desc, String location, String impact, String tag, String time) {
        UsagePattern pattern = UsagePattern.create(userId, type, title, desc, location, impact, tag, time);
        return usagePatternRepository.save(pattern);
    }

    @Transactional(readOnly = true)
    public Optional<UsagePattern> getById(Integer patternId) {
        return usagePatternRepository.findById(patternId);
    }

    @Transactional(readOnly = true)
    public List<UsagePattern> getByUserId(Integer userId) {
        return usagePatternRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<UsagePattern> getAll() {
        return usagePatternRepository.findAll();
    }

    public UsagePattern updateStatus(Integer patternId, String tag) {
        UsagePattern existing = usagePatternRepository.findById(patternId)
                .orElseThrow(() -> new IllegalArgumentException("UsagePattern not found: " + patternId));

        String type = "resolved".equalsIgnoreCase(tag) ? "success" : "warning";
        UsagePattern updated = new UsagePattern(
                existing.patternId(),
                existing.userId(),
                type,
                existing.title(),
                existing.desc(),
                existing.location(),
                existing.impact(),
                tag,
                "Just now"
        );
        return usagePatternRepository.save(updated);
    }

    public void deleteById(Integer patternId) {
        usagePatternRepository.deleteById(patternId);
    }
}

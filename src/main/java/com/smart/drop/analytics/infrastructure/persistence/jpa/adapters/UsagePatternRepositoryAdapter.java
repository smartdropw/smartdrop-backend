package com.smart.drop.analytics.infrastructure.persistence.jpa.adapters;

import com.smart.drop.analytics.domain.model.entities.UsagePattern;
import com.smart.drop.analytics.domain.model.repositories.UsagePatternRepository;
import com.smart.drop.analytics.infrastructure.persistence.jpa.entities.UsagePatternEntity;
import com.smart.drop.analytics.infrastructure.persistence.jpa.repositories.UsagePatternJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsagePatternRepositoryAdapter implements UsagePatternRepository {

    private final UsagePatternJpaRepository usagePatternJpaRepository;

    public UsagePatternRepositoryAdapter(UsagePatternJpaRepository usagePatternJpaRepository) {
        this.usagePatternJpaRepository = usagePatternJpaRepository;
    }

    @Override
    public UsagePattern save(UsagePattern usagePattern) {
        UsagePatternEntity entity = toEntity(usagePattern);
        UsagePatternEntity saved = usagePatternJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<UsagePattern> findById(Integer patternId) {
        return usagePatternJpaRepository.findById(patternId).map(this::toDomain);
    }

    @Override
    public List<UsagePattern> findByUserId(Integer userId) {
        return usagePatternJpaRepository.findByUserId(userId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<UsagePattern> findAll() {
        return usagePatternJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Integer patternId) {
        usagePatternJpaRepository.deleteById(patternId);
    }

    private UsagePatternEntity toEntity(UsagePattern domain) {
        UsagePatternEntity entity = new UsagePatternEntity();
        entity.setPatternId(domain.patternId());
        entity.setUserId(domain.userId());
        entity.setType(domain.type());
        entity.setTitle(domain.title());
        entity.setDesc(domain.desc());
        entity.setLocation(domain.location());
        entity.setImpact(domain.impact());
        entity.setTag(domain.tag());
        entity.setTime(domain.time());
        return entity;
    }

    private UsagePattern toDomain(UsagePatternEntity entity) {
        return new UsagePattern(
                entity.getPatternId(),
                entity.getUserId(),
                entity.getType(),
                entity.getTitle(),
                entity.getDesc(),
                entity.getLocation(),
                entity.getImpact(),
                entity.getTag(),
                entity.getTime()
        );
    }
}

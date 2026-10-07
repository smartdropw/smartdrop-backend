package com.smart.drop.analytics.domain.model.entities;

/**
 * Immutable domain entity representing anomalous water usage patterns.
 */
public record UsagePattern(
        Integer patternId,
        Integer userId,
        String type,
        String title,
        String desc,
        String location,
        String impact,
        String tag,
        String time
) {
    public UsagePattern {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
    }

    public static UsagePattern create(Integer userId, String type, String title, String desc, String location, String impact, String tag, String time) {
        return new UsagePattern(null, userId, type, title, desc, location, impact, tag, time);
    }
}

package com.smart.drop.analytics.interfaces.rest.dto;

public record CreateUsagePatternRequest(
        Integer userId,
        String type,
        String title,
        String desc,
        String location,
        String impact,
        String tag,
        String time
) {
}

package com.wearewaes.assignment.diff.domain.integration.service.client.cache;

import com.wearewaes.assignment.diff.domain.model.Diff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class abstract the values that can be cached
 * It uses Lombok to remove unnecessary boilerplate code (get/setter, equal and hashcode, etc)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationDiffCacheEntry {
    private String id;
    private String leftValue;
    private String rightValue;
    private List<Diff> diffs;

    public IntegrationDiffCacheEntry(String id, String leftValue, String rightValue) {
        this.id = id;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }
}

package com.wearewaes.assignment.cache.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class abstract a cached difference between to values
 * It uses Lombok to remove unnecessary code (get/setter, equal and hashcode, etc)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheDiff {
    private int offset;
    private int length;
}

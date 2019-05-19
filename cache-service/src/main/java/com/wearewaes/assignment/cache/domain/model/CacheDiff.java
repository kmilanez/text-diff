package com.wearewaes.assignment.cache.domain.model;

import lombok.*;

/**
 * This class abstract a cached difference between to values
 * It uses Lombok to remove unnecessary code (get/setter, equal and hashcode, etc)
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CacheDiff {
    private int offset;
    private int length;
}

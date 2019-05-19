package com.wearewaes.assignment.diff.domain.model;

import lombok.*;

/**
 * This class abstracts a difference between two values, containing the
 * offset and difference length
 * It uses Lombok to remove unnecessary boilerplate code (get/setter, equal and hashcode, etc)
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Diff {
    private Integer offset;
    private Integer length;
}

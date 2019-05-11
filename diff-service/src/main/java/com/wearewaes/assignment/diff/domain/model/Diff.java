package com.wearewaes.assignment.diff.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class abstracts a difference between two values, containing the
 * offset and difference length
 * It uses Lombok to remove unnecessary boilerplate code (get/setter, equal and hashcode, etc)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diff {
    private Integer offset;
    private Integer length;
}

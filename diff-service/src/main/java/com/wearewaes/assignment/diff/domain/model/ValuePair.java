package com.wearewaes.assignment.diff.domain.model;

import lombok.*;

/**
 * This class abstracts the service response
 * It uses Lombok to remove unnecessary boilerplate code (get/setter, equal and hashcode, etc)
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValuePair {
    private String leftValue;
    private String rightValue;

    public boolean haveDifferentSize() {
        return leftValue.length() != rightValue.length();
    }

    public boolean areEqual() {
        return leftValue.equals(rightValue);
    }
}

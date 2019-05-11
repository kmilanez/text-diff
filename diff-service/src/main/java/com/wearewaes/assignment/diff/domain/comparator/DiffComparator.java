package com.wearewaes.assignment.diff.domain.comparator;

/**
 * This interface defines a common contract for a difference comparator
 * for a given sequence of values and return then into a reduced value
 * or container
 */
public interface DiffComparator<R, T> {
    T compare(R values);
}

package com.wearewaes.assignment.diff.domain.comparator;

import com.wearewaes.assignment.diff.domain.model.Diff;
import com.wearewaes.assignment.diff.domain.model.ValuePair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a value pair comparator that returns a list of difference offset and
 * length
 */
@Component
public class ValuePairDiffComparator implements DiffComparator<ValuePair, List<Diff>> {

    private static final int LENGTH_CLEAN_STATE = 0;
    private static final int OFFSET_CLEAN_STATE = 0;

    /**
     * Iterate over two string values and return the difference offsets and lengths
     * of these differences
     * @param valuePair string value pair
     * @return list with differences (offset, length)
     */
    public List<Diff> compare(ValuePair valuePair) {

        final List<Diff> valuePairDiffs = new ArrayList<>();

        final String leftValue = valuePair.getLeftValue();
        final String rightValue = valuePair.getRightValue();

        int length = LENGTH_CLEAN_STATE;
        int offset = OFFSET_CLEAN_STATE;

        for (int pos = 0; pos < leftValue.length(); pos++) {
            if (valuesAreDifferentInPosition(leftValue, rightValue, pos)) {
                if (isCapturingDiffs(length)) {
                    length++;
                } else {
                    offset = pos;
                    length++;
                }
            } else {
                if (isCapturingDiffs(length)) {
                    valuePairDiffs.add(new Diff(offset, length));
                    offset = OFFSET_CLEAN_STATE;
                    length = LENGTH_CLEAN_STATE;
                }
            }
        }

        return valuePairDiffs;
    }

    /**
     * Check if the values between two string of same size, at a given index, are not the same
     * @param firstValue string value
     * @param secondValue string value
     * @param index index to compare
     * @return true if values are not the same
     */
    private boolean valuesAreDifferentInPosition(String firstValue, String secondValue, int index) {
        return firstValue.charAt(index) != secondValue.charAt(index);
    }

    /**
     * Check if length is not in clean state(0), which indicate that algorithm is capturing differences
     * for a given offset
     * @param length difference length
     * @return true if its no in clean state(0)
     */
    private boolean isCapturingDiffs(int length) {
        return length != OFFSET_CLEAN_STATE;
    }
}

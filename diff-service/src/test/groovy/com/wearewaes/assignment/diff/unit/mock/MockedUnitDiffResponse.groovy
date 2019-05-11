package com.wearewaes.assignment.diff.unit.mock

import com.wearewaes.assignment.diff.domain.model.Diff
import com.wearewaes.assignment.diff.domain.model.DiffResponse
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus
import com.wearewaes.assignment.diff.domain.model.ValuePair

class MockedUnitDiffResponse {
    public static final TEST_1_DIFF_RESPONSE = new DiffResponse("123450", "Hello")
    public static final TEST_2_DIFF_RESPONSE = new DiffResponse("123451", "Hallo")
    public static final TEST_3_DIFF_RESPONSE = new DiffResponse("123451",
            new ValuePair("Hello", "Hello"), DiffResponseStatus.ARE_EQUAL)
    public static final TEST_4_DIFF_RESPONSE = new DiffResponse("123451",
            new ValuePair("Hello", "Hallo"), [new Diff(1,1)])
}

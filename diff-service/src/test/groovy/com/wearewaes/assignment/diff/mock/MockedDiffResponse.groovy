package com.wearewaes.assignment.diff.mock

import com.wearewaes.assignment.diff.domain.model.Diff
import com.wearewaes.assignment.diff.domain.model.DiffResponse
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus
import com.wearewaes.assignment.diff.domain.model.ValuePair

class MockedDiffResponse {
    public static final TEST_1_DIFF_RESPONSE = new DiffResponse("12340", "Hello")

    public static final TEST_2_DIFF_RESPONSE = new DiffResponse("12341", "Hallo")

    public static final TEST_4_DIFF_RESPONSE = new DiffResponse("12343",
            new ValuePair("{\"value\":\"Hello!\"}", "{\"value\":\"Hello!\"}"),
            DiffResponseStatus.ARE_EQUAL)

    public static final TEST_5_DIFF_RESPONSE = new DiffResponse("12344",
            new ValuePair("{\"value\":\"Good morning!\"}", "{\"value\":\"Guten tag!\"}"),
            DiffResponseStatus.HAVE_DIFFERENT_SIZE)

    public static final TEST_6_DIFF_RESPONSE = new DiffResponse("12345",
            new ValuePair("{\"value\":\"Hello!\"}", "{\"value\":\"Hallo!\"}"),
            [new Diff(11,1)])
}

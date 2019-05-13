package com.wearewaes.assignment.diff.unit.domain.comparator

import com.wearewaes.assignment.diff.domain.comparator.ValuePairDiffComparator
import com.wearewaes.assignment.diff.domain.model.Diff
import com.wearewaes.assignment.diff.domain.model.ValuePair
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class ValuePairDiffComparatorSpec extends Specification {

    private ValuePairDiffComparator comparator

    def setup() {
        comparator = new ValuePairDiffComparator()
    }

    def "When values are 'Hello' and 'Hallo', with same length, return Diff(1,1)"() {
        given:
            def values = new ValuePair("Hello", "Hallo")
            def expectedDiffs = [new Diff(1,1)]
        when:
            List<Diff> valueDiffs = comparator.compare(values)
        then:
            assertThat(valueDiffs).isNotEmpty()
            assertThat(valueDiffs.size()).isEqualTo(1)
            assertThat(valueDiffs).isEqualTo(expectedDiffs)
    }

    def "When values are JSON payloads, with same length, return [Diff(10,1), Diff(14,1)"() {
        given:
            def values = new ValuePair("{\"value\":\"SpocK\"}",
                    "{\"value\":\"spock\"}")
            def expectedDiffs = [new Diff(10,1), new Diff(14,1)]
        when:
            List<Diff> valueDiffs = comparator.compare(values)
        then:
            assertThat(valueDiffs).isNotEmpty()
            assertThat(valueDiffs.size()).isEqualTo(2)
            assertThat(valueDiffs).isEqualTo(expectedDiffs)
    }
}

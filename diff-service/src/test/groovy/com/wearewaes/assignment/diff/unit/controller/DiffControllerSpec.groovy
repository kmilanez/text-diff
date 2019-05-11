package com.wearewaes.assignment.diff.unit.controller

import com.wearewaes.assignment.diff.controller.DiffController
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus
import com.wearewaes.assignment.diff.service.DiffEvaluationService
import com.wearewaes.assignment.diff.service.SaveValuesService
import com.wearewaes.assignment.diff.unit.mock.MockedUnitDiffResponse
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification
import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class DiffControllerSpec extends Specification {

    @Mock
    private DiffEvaluationService diffService
    @Mock
    private SaveValuesService saveValuesService

    @InjectMocks
    private DiffController controller

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should throw an IllegalArgumentException when trying to get value diffs with a null Id"() {
        given:
            def id = null
        when:
            when(diffService.getDifference(id)).thenThrow(IllegalArgumentException)
            controller.getDifference(id)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should throw an IllegalArgumentException when trying to get value diffs with an empty Id"() {
        given:
            def id = null
        when:
            when(diffService.getDifference(id)).thenThrow(IllegalArgumentException)
            controller.getDifference(id)
        then:
            thrown(IllegalArgumentException)
    }

    def "Should create a new diff entry and save left value it when value is not cached yet"() {
        given:
            def id = MockedUnitDiffResponse.TEST_1_DIFF_RESPONSE.getId()
            def encodedValue = "SGVsbG8="
            def diffResponse = MockedUnitDiffResponse.TEST_1_DIFF_RESPONSE
        when:
            when(saveValuesService.saveLeftValue(id, encodedValue)).thenReturn(diffResponse)
            def response = controller.saveLeftValue(id, encodedValue)
        then:
            assertThat(response).isNotNull()
            assertThat(response).isEqualTo(diffResponse)
            assertThat(response.getValue()).isEqualTo(diffResponse.getValue())
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.SAVED.name())
    }

    def "Should create a new diff entry and save right value it when value is not cached yet"() {
        given:
            def id = MockedUnitDiffResponse.TEST_2_DIFF_RESPONSE.getId()
            def encodedValue = "SGFsbG8="
            def diffResponse = MockedUnitDiffResponse.TEST_2_DIFF_RESPONSE
        when:
            when(saveValuesService.saveRightValue(id, encodedValue)).thenReturn(diffResponse)
            def response = controller.saveRightValue(id, encodedValue)
        then:
        assertThat(response).isNotNull()
        assertThat(response.getValue()).isEqualTo(diffResponse.getValue())
        assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.SAVED.name())
    }


    def "Should return EQUAL response when left and right are equal"() {
        given:
            def id = MockedUnitDiffResponse.TEST_3_DIFF_RESPONSE.getId()
            def diffResponse = MockedUnitDiffResponse.TEST_3_DIFF_RESPONSE
        when:
            when(diffService.getDifference(id)).thenReturn(diffResponse)
            def response = controller.getDifference(id)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.ARE_EQUAL.name())
    }

    def "Should return HAVE_DIFFERENCES response when left and right have differences"() {
        given:
            def id = MockedUnitDiffResponse.TEST_4_DIFF_RESPONSE.getId()
            def diffResponse = MockedUnitDiffResponse.TEST_4_DIFF_RESPONSE
        when:
            when(diffService.getDifference(id)).thenReturn(diffResponse)
            def response = controller.getDifference(id)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.HAVE_DIFFERENCES.name())
            assertThat(response.getDiffs()).isEqualTo(diffResponse.getDiffs())
    }
}

package com.wearewaes.assignment.diff.unit.controller

import com.wearewaes.assignment.diff.controller.DiffSaveValuesController
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus
import com.wearewaes.assignment.diff.service.SaveValuesService
import com.wearewaes.assignment.diff.mock.MockedDiffResponse
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class DiffSaveValuesControllerSpec extends Specification {

    @Mock
    private SaveValuesService saveValuesService

    @InjectMocks
    private DiffSaveValuesController controller

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "Should create a new diff entry and save left value it when value is not cached yet"() {
        given:
            def id = MockedDiffResponse.TEST_1_DIFF_RESPONSE.getId()
            def encodedValue = "SGVsbG8="
            def diffResponse = MockedDiffResponse.TEST_1_DIFF_RESPONSE
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
            def id = MockedDiffResponse.TEST_2_DIFF_RESPONSE.getId()
            def encodedValue = "SGFsbG8="
            def diffResponse = MockedDiffResponse.TEST_2_DIFF_RESPONSE
        when:
            when(saveValuesService.saveRightValue(id, encodedValue)).thenReturn(diffResponse)
            def response = controller.saveRightValue(id, encodedValue)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getValue()).isEqualTo(diffResponse.getValue())
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.SAVED.name())
    }

}

package com.wearewaes.assignment.diff.unit.controller

import com.wearewaes.assignment.diff.controller.DiffEvaluationController
import com.wearewaes.assignment.diff.domain.model.DiffResponseStatus
import com.wearewaes.assignment.diff.service.DiffEvaluationService
import com.wearewaes.assignment.diff.mock.MockedDiffResponse
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.when

class DiffEvaluationControllerSpec extends Specification {

    @Mock
    private DiffEvaluationService diffService

    @InjectMocks
    private DiffEvaluationController controller


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

    def "Should return EQUAL response when left and right are equal"() {
        given:
            def id = MockedDiffResponse.TEST_4_DIFF_RESPONSE.getId()
            def diffResponse = MockedDiffResponse.TEST_4_DIFF_RESPONSE
        when:
            when(diffService.getDifference(id)).thenReturn(diffResponse)
            def response = controller.getDifference(id)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.ARE_EQUAL.name())
    }

    def "Should return HAVE_DIFFERENCES response when left and right have differences"() {
        given:
            def id = MockedDiffResponse.TEST_6_DIFF_RESPONSE.getId()
            def diffResponse = MockedDiffResponse.TEST_6_DIFF_RESPONSE
        when:
            when(diffService.getDifference(id)).thenReturn(diffResponse)
            def response = controller.getDifference(id)
        then:
            assertThat(response).isNotNull()
            assertThat(response.getStatus().name()).isEqualTo(DiffResponseStatus.HAVE_DIFFERENCES.name())
            assertThat(response.getDiffs()).isEqualTo(diffResponse.getDiffs())
    }

}

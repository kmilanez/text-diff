package com.wearewaes.assignment.diff.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * This class abstracts the service response
 * It uses Lombok to remove unnecessary boilerplate code (get/setter, equal and hashcode, etc)
 */
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class DiffResponse {

    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ValuePair valuePair;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Diff> diffs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DiffResponseStatus status;

    public DiffResponse(String id, String value) {
        this.id = id;
        this.value = value;
        this.status = DiffResponseStatus.SAVED;
    }

    public DiffResponse(String id, ValuePair valuePair, DiffResponseStatus status) {
        this.id = id;
        this.valuePair = valuePair;
        this.status = status;
    }

    public DiffResponse(String id, ValuePair valuePair, List<Diff> diffs) {
        this.id = id;
        this.valuePair = valuePair;
        this.diffs = diffs;
        this.status = DiffResponseStatus.HAVE_DIFFERENCES;
    }
}

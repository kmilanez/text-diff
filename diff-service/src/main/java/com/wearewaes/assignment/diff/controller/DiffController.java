package com.wearewaes.assignment.diff.controller;

import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import com.wearewaes.assignment.diff.service.DiffEvaluationService;
import com.wearewaes.assignment.diff.service.SaveValuesService;
import org.springframework.web.bind.annotation.*;

/**
 * REST service endpoints to get difference between left and right values and
 * save them
 */
@RestController
@RequestMapping("/v1/diff")
public class DiffController {

    private DiffEvaluationService diffService;
    private SaveValuesService saveValuesService;

    public DiffController(DiffEvaluationService diffService,
                          SaveValuesService saveValuesService) {
        this.diffService = diffService;
        this.saveValuesService = saveValuesService;
    }

    @GetMapping("/{id}")
    public DiffResponse getDifference(@PathVariable("id") String id) {
        return diffService.getDifference(id);
    }

    @PostMapping("/{id}/left")
    public DiffResponse saveLeftValue(@PathVariable("id") String id, @RequestBody String leftValue) {
        return saveValuesService.saveLeftValue(id, leftValue);
    }

    @PostMapping("/{id}/right")
    public DiffResponse saveRightValue(@PathVariable("id") String id, @RequestBody String rightValue) {
        return saveValuesService.saveRightValue(id, rightValue);
    }
}

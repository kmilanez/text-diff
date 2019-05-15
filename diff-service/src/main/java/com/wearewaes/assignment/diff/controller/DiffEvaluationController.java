package com.wearewaes.assignment.diff.controller;

import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import com.wearewaes.assignment.diff.service.DiffEvaluationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/diff")
public class DiffEvaluationController {

    private DiffEvaluationService service;

    public DiffEvaluationController(DiffEvaluationService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public DiffResponse getDifference(@PathVariable("id") String id) {
        return service.getDifference(id);
    }

}

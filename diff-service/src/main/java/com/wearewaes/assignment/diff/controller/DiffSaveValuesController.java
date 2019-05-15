package com.wearewaes.assignment.diff.controller;

import com.wearewaes.assignment.diff.domain.model.DiffResponse;
import com.wearewaes.assignment.diff.service.SaveValuesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/diff")
public class DiffSaveValuesController {
    private SaveValuesService service;

    public DiffSaveValuesController(SaveValuesService service) {
        this.service = service;
    }

    @PostMapping("/{id}/left")
    public DiffResponse saveLeftValue(@PathVariable("id") String id, @RequestBody String leftValue) {
        return service.saveLeftValue(id, leftValue);
    }

    @PostMapping("/{id}/right")
    public DiffResponse saveRightValue(@PathVariable("id") String id, @RequestBody String rightValue) {
        return service.saveRightValue(id, rightValue);
    }
}

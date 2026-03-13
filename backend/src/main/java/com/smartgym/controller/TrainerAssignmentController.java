package com.smartgym.controller;

import com.smartgym.model.TrainerAssignment;
import com.smartgym.service.TrainerAssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer-assignments")
public class TrainerAssignmentController {

    private final TrainerAssignmentService assignmentService;

    public TrainerAssignmentController(TrainerAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public TrainerAssignment assign(@RequestParam Long trainerId,
                                    @RequestParam Long memberId,
                                    @RequestParam(required = false) String notes) {
        return assignmentService.assign(trainerId, memberId, notes);
    }

    @GetMapping
    public List<TrainerAssignment> list() {
        return assignmentService.list();
    }
}

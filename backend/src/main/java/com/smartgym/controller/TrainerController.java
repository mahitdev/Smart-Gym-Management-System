package com.smartgym.controller;

import com.smartgym.model.Trainer;
import com.smartgym.service.TrainerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public Trainer create(@RequestBody Trainer trainer) {
        return trainerService.save(trainer);
    }

    @GetMapping
    public List<Trainer> list() {
        return trainerService.findAll();
    }
}

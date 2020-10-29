package com.example.demo.controller;

import com.example.demo.model.Trainer;
import com.example.demo.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainer addTrainer(@RequestBody @Valid Trainer trainer){
        return trainerService.addTrainer(trainer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Trainer> getAllTrainersByGrouped(@RequestParam(name = "grouped") boolean grouped){
        return trainerService.getAllTrainersByGrouped(grouped);
    }

    @DeleteMapping("/{trainer_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainerById(@PathVariable long trainer_id){
        trainerService.deleteTrainerById(trainer_id);
    }
}

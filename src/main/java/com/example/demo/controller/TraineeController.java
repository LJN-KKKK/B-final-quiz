package com.example.demo.controller;

import com.example.demo.model.Trainee;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/trainees")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainee addTrainee(@RequestBody @Valid Trainee trainee){
        return traineeService.addTrainee(trainee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Trainee> getAllTraineesByGrouped(@RequestParam(name = "grouped") boolean grouped){
        return traineeService.getAllTraineesByGrouped(grouped);
    }

    @DeleteMapping("/{trainee_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTraineeById(@PathVariable long trainee_id){
        traineeService.deleteTraineeById(trainee_id);
    }
}

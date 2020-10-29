package com.example.demo.service;

import com.example.demo.exception.PersonNotExistsException;
import com.example.demo.model.Trainee;
import com.example.demo.repository.TraineeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public Trainee addTrainee(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    public List<Trainee> getAllTraineesByGrouped(boolean grouped) {
        return traineeRepository.findAllByGrouped(grouped);
    }

    public void deleteTraineeById(long trainee_id) {
        if(!traineeRepository.existsById(trainee_id)){
            throw new PersonNotExistsException("trainee does not exist");
        }
        traineeRepository.deleteById(trainee_id);
    }
}

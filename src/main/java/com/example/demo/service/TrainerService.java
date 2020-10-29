package com.example.demo.service;

import com.example.demo.exception.PersonNotExistsException;
import com.example.demo.model.Trainer;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer addTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
        return trainer;
    }

    public List<Trainer> getAllTrainersByGrouped(boolean grouped) {
        return trainerRepository.findAllByGrouped(grouped);
    }

    public void deleteTrainerById(long trainer_id) {
        if(!trainerRepository.existsById(trainer_id)){
            throw new PersonNotExistsException("trainer does not exist");
        }
        trainerRepository.deleteById(trainer_id);
    }
}

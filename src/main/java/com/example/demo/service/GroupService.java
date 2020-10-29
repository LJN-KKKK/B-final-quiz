package com.example.demo.service;

import com.example.demo.exception.TrainerNotEnoughException;
import com.example.demo.model.Group;
import com.example.demo.model.Trainee;
import com.example.demo.model.Trainer;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GroupService {
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final GroupRepository groupRepository;
    private static final Integer min_group_num = 2;

    public GroupService(TraineeRepository traineeRepository, TrainerRepository trainerRepository, GroupRepository groupRepository) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.groupRepository = groupRepository;
    }

    public List<Group> autoGrouping() {
        List<Trainee> traineeList = traineeRepository.findAll();
        List<Trainer> trainerList = trainerRepository.findAll();

        reSetGroup(trainerList, traineeList);

        int traineeNum = traineeList.size();
        int trainerNum = trainerList.size();

        if(trainerNum < min_group_num) { throw new TrainerNotEnoughException("当前讲师数量不足"); }

        Collections.shuffle(traineeList);
        Collections.shuffle(trainerList);

        int group_num = trainerNum/2;
        for(int i = group_num; i >= 1; i--){
            this.createGroup("Team " + (group_num - i + 1),
                    trainerList.subList((group_num - i) * trainerNum / group_num, (group_num - i) * trainerNum / group_num + 2),
                    traineeList.subList((i - 1) * traineeNum / group_num, i * traineeNum / group_num));
        }

        return groupRepository.findAll();
    }

    public void createGroup(String groupName, List<Trainer> trainerList, List<Trainee> traineeList){
        Group trainGroup = groupRepository.save(Group.builder()
                .trainers(trainerList)
                .trainees(traineeList)
                .name(groupName).build());

        trainerList.forEach(trainer -> {
            trainerRepository.updateGroupIdById(trainer.getId(), trainGroup.getId());
            trainerRepository.updateGroupedById(trainer.getId(), true);
        });

        traineeList.forEach(trainee -> {
            traineeRepository.updateGroupById(trainee.getId(), trainGroup.getId());
            traineeRepository.updateGroupedById(trainee.getId(), true);
        });
    }

    public void reSetGroup(List<Trainer> trainerList, List<Trainee> traineeList){
        trainerList.forEach(trainer -> {
            trainerRepository.updateGroupIdById(trainer.getId(), null);
            trainerRepository.updateGroupedById(trainer.getId(), false);
        });

        traineeList.forEach(trainee -> {
            traineeRepository.updateGroupById(trainee.getId(), null);
            traineeRepository.updateGroupedById(trainee.getId(), false);
        });

        groupRepository.deleteAll();
    }

    public List<Group> getAllGroup() {
        return groupRepository.findAll();
    }
}

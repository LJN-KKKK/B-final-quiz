package com.example.demo.service;

import com.example.demo.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private static final Integer min_group_num = 2;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}

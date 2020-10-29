package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/auto-grouping")
    @ResponseStatus(HttpStatus.OK)
    public List<Group> autoGrouping(){
        return groupService.autoGrouping();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Group> getAllGroup(){
        return groupService.getAllGroup();
    }
}

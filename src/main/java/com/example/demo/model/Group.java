package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "train_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "name不能为空")
    private String name;

    //TODO GTB-知识点: *  @OneToMany和@ManyToOne不一定要成对出现，加深一下关系映射的理解。
    @OneToMany(mappedBy = "group")
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "group")
    private List<Trainee> trainees;
}

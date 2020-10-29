package com.example.demo.service;

import com.example.demo.exception.PersonNotExistsException;
import com.example.demo.model.Trainee;
import com.example.demo.repository.TraineeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {
    private TraineeService traineeService;
    @Mock
    private TraineeRepository traineeRepository;

    private Trainee trainee;

    @BeforeEach
    void setUp(){
        traineeService = new TraineeService(traineeRepository);
        trainee = Trainee.builder()
                .id(233L)
                .name("kkkk")
                .grouped(false)
                .build();
    }

    @Nested
    class AddTrainee{
        @Nested
        class WhenTraineeIsValid{
            @Test
            public void should_return_new_trainee() {
                when(traineeRepository.save(trainee)).thenReturn(trainee);

                Trainee traineeNew = traineeService.addTrainee(trainee);

                assertThat(traineeNew).isEqualTo(Trainee.builder()
                        .id(233L)
                        .name("kkkk")
                        .grouped(false)
                        .build());
            }
        }
    }

    @Nested
    class DeleteTrainee{
        @Nested
        class WhenTraineeExists{
            @Test
            public void should_return_void() {
                when(traineeRepository.existsById(233L)).thenReturn(true);
                doNothing().when(traineeRepository).deleteById(233L);

                traineeService.deleteTraineeById(233L);

                verify(traineeRepository, times(1)).existsById(233L);
                verify(traineeRepository, times(1)).deleteById(233L);
            }
        }

        @Nested
        class WhenTraineeNotExists{
            @Test
            public void should_throw_exception() {
                when(traineeRepository.existsById(233L)).thenReturn(false);

                PersonNotExistsException thrownException = assertThrows(PersonNotExistsException.class, () -> traineeService.deleteTraineeById(233L));

                assertThat(thrownException.getMessage()).containsSequence("trainee does not exist");
            }
        }
    }

    @Nested
    class FindByGrouped {
        @Nested
        class WhenNotAllTraineesGrouped{
            @Test
            public void should_return_trainees() {
                List<Trainee> traineeList = new ArrayList<>();
                traineeList.add(trainee);

                when(traineeRepository.findAllByGrouped(false)).thenReturn(traineeList);

                List<Trainee> foundTrainees = traineeService.getAllTraineesByGrouped(false);

                assertThat(foundTrainees.get(0)).isEqualTo(Trainee.builder()
                        .id(233L)
                        .name("kkkk")
                        .grouped(false)
                        .build());
            }
        }
    }
}


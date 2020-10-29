package com.example.demo.service;

import com.example.demo.exception.PersonNotExistsException;
import com.example.demo.model.Trainer;
import com.example.demo.repository.TrainerRepository;
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
public class TrainerServiceTest {
    private TrainerService trainerService;
    @Mock
    private TrainerRepository trainerRepository;

    private Trainer trainer;

    @BeforeEach
    void setUp(){
        trainerService = new TrainerService(trainerRepository);
        trainer = Trainer.builder()
                .id(233L)
                .name("kkkk")
                .grouped(false)
                .build();
    }

    @Nested
    class AddTrainer{
        @Nested
        class WhenTrainerIsValid{
            @Test
            public void should_return_new_trainer() {
                when(trainerRepository.save(trainer)).thenReturn(trainer);

                Trainer trainerNew = trainerService.addTrainer(trainer);

                assertThat(trainerNew).isEqualTo(Trainer.builder()
                        .id(233L)
                        .name("kkkk")
                        .grouped(false)
                        .build());
            }
        }
    }

    @Nested
    class DeleteTrainer{
        @Nested
        class WhenTrainerExists{
            @Test
            public void should_return_void() {
                when(trainerRepository.existsById(233L)).thenReturn(true);
                doNothing().when(trainerRepository).deleteById(233L);

                trainerService.deleteTrainerById(233L);

                verify(trainerRepository, times(1)).existsById(233L);
                verify(trainerRepository, times(1)).deleteById(233L);
            }
        }

        @Nested
        class WhenTrainerNotExists{
            @Test
            public void should_throw_exception() {
                when(trainerRepository.existsById(233L)).thenReturn(false);

                PersonNotExistsException thrownException = assertThrows(PersonNotExistsException.class, () -> trainerService.deleteTrainerById(233L));

                assertThat(thrownException.getMessage()).containsSequence("trainer does not exist");
            }
        }
    }

    @Nested
    class FindByGrouped {
        @Nested
        class WhenNotAllTrainersGrouped{
            @Test
            public void should_return_trainers() {
                List<Trainer> trainerList = new ArrayList<>();
                trainerList.add(trainer);

                when(trainerRepository.findAllByGrouped(false)).thenReturn(trainerList);

                List<Trainer> foundTrainers = trainerService.getAllTrainersByGrouped(false);

                assertThat(foundTrainers.get(0)).isEqualTo(Trainer.builder()
                        .id(233L)
                        .name("kkkk")
                        .grouped(false)
                        .build());
            }
        }
    }
}


package com.example.demo.controller;

import com.example.demo.model.Trainer;
import com.example.demo.service.TrainerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainerController.class)
@AutoConfigureJsonTesters
public class TrainerControllerTest {
    @MockBean
    private TrainerService trainerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Trainer> trainerJson;
    @Autowired
    private JacksonTester<List<Trainer>> trainerListJson;

    private Trainer trainer;

    @BeforeEach
    public void beforeEach(){
        trainer = Trainer.builder()
                .id(233L)
                .name("kkkk")
                .grouped(false)
                .build();
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(trainerService);
    }

    @Nested
    class CreateTrainer{
        @Nested
        class WhenTrainerIsValid{
            @Test
            public void should_return_new_trainer_and_CREATED() throws Exception{
                MockHttpServletRequestBuilder requestBuilder = post("/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(trainerJson.write(trainer).getJson());

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

                verify(trainerService, times(1)).addTrainer(trainer);

            }
        }
    }
    @Nested
    class GetAllTrainersByGrouped{

        @Test
        public void should_return_all_trainers_not_grouped() throws Exception{

            List<Trainer> trainerList = new ArrayList<>();
            trainerList.add(trainer);

            when(trainerService.getAllTrainersByGrouped(false)).thenReturn(trainerList);

            MockHttpServletResponse response = mockMvc.perform(get("/trainers?grouped=false", false))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getContentAsString())
                    .isEqualTo(trainerListJson.write(trainerList).getJson());

            verify(trainerService, times(1)).getAllTrainersByGrouped(false);
        }
    }

    @Nested
    class DeleteTrainerById{

        @Test
        public void should_return_NO_CONTENT() throws Exception{

            doNothing().when(trainerService).deleteTrainerById(233L);

            mockMvc.perform(delete("/trainers/233", 233L))
                    .andExpect(status().isNoContent());

            verify(trainerService, times(1)).deleteTrainerById(233L);
        }
    }
}

package com.example.demo.controller;

import com.example.demo.model.Trainee;
import com.example.demo.service.TraineeService;
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

@WebMvcTest(TraineeController.class)
@AutoConfigureJsonTesters
public class TraineeControllerTest {
    @MockBean
    private TraineeService traineeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Trainee> traineeJson;
    @Autowired
    private JacksonTester<List<Trainee>> traineeListJson;

    private Trainee trainee;

    @BeforeEach
    public void beforeEach(){
        trainee = Trainee.builder()
                .id(233L)
                .name("kkkk")
                .grouped(false)
                .build();
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(traineeService);
    }

    @Nested
    class CreateTrainee{
        @Nested
        class WhenTraineeIsValid{
            @Test
            public void should_return_new_trainee_and_CREATED() throws Exception{
                MockHttpServletRequestBuilder requestBuilder = post("/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson.write(trainee).getJson());

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

                verify(traineeService, times(1)).addTrainee(trainee);

            }
        }
    }
    @Nested
    class GetAllTraineesByGrouped{

        @Test
        public void should_return_all_trainees_not_grouped() throws Exception{

            List<Trainee> traineeList = new ArrayList<>();
            traineeList.add(trainee);

            when(traineeService.getAllTraineesByGrouped(false)).thenReturn(traineeList);

            MockHttpServletResponse response = mockMvc.perform(get("/trainees?grouped=false", false))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            assertThat(response.getContentAsString())
                    .isEqualTo(traineeListJson.write(traineeList).getJson());

            verify(traineeService, times(1)).getAllTraineesByGrouped(false);
        }
    }

    @Nested
    class DeleteTraineeById{

        @Test
        public void should_return_NO_CONTENT() throws Exception{

            doNothing().when(traineeService).deleteTraineeById(233L);

            mockMvc.perform(delete("/trainees/233", 233L))
                    .andExpect(status().isNoContent());

            verify(traineeService, times(1)).deleteTraineeById(233L);
        }
    }
}

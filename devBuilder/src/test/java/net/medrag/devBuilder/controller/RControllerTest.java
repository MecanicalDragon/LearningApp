package net.medrag.devBuilder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.medrag.devBuilder.DevBuilderApplication;
import net.medrag.devBuilder.model.Request;
import net.medrag.devBuilder.service.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevBuilderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class RControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Repository repository;

    private Request request;

    @BeforeEach
    public void setUp() {
        request = new Request();
        request.setKey("05");
        request.setName("Vasiliy");
        request.setSurname("Drugov");
        request.setAge(30);
        request.setSkillsCount(3);
        request.setBday("20.11.1988 10:05");
        request.setRace("Hohol");
    }

    @AfterEach
    public void tearDown() {
        request = null;
    }

    @Test
    public void getDev() throws Exception {
        when(repository.getById(any(String.class))).thenReturn(request);

        this.mvc.perform(get("/devBuilder/getDeveloper/taskId-01"))
                .andExpect(content().contentType("text/xml;charset=UTF-8"))
                .andExpect(status().isOk());

        ArgumentCaptor<String> ending = ArgumentCaptor.forClass(String.class);
        verify(repository).getById(ending.capture());
        assertThat(ending.getValue(), equalTo("01"));
    }

    @Test
    public void setServicesAmount() throws Exception {

        when(repository.save(any(Request.class))).thenReturn(request);
        this.mvc.perform(post("/devBuilder/setSkillsCount").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").value(request));

        ArgumentCaptor<Request> ending = ArgumentCaptor.forClass(Request.class);
        verify(repository).save(ending.capture());
        assertThat(ending.getValue(), equalTo(request));
    }

    @Test
    public void getStartUpData() throws Exception {

        Map<String, Request> map = new HashMap<>();
        map.put("req-01", request);
        map.put("req-02", request);
        when(repository.getAll()).thenReturn(map);
        this.mvc.perform(get("/devBuilder/getStartUpData").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.raceTypes").isArray())
                .andExpect(jsonPath("$.raceTypes", hasSize(10)))
                .andExpect(jsonPath("$.raceTypes[0]").value("JEW"));
    }

}

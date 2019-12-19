package net.medrag.devBuilder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.medrag.devBuilder.DevBuilderApplication;
import net.medrag.devBuilder.model.Request;
import net.medrag.devBuilder.model.StartUpData;
import net.medrag.devBuilder.service.Processor;
import net.medrag.schema.RaceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DevBuilderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class RControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Processor processor;

    private Request request;

    @Before
    public void setUp(){
        request = new Request();
        request.setKey("05");
        request.setName("Vasiliy");
        request.setSurname("Drugov");
        request.setAge(30);
        request.setSkillsCount(3);
        request.setBday("20.11.1988 10:05");
        request.setRace("Hohol");
    }

    @After
    public void tearDown(){
        request = null;
    }

    @Test
    public void getDev() throws Exception {
        when(processor.process(any(String.class))).thenReturn("someXml");
        this.mvc.perform(get("/devBuilder/getDeveloper/taskId-01"))
                .andExpect(content().contentType("text/xml;charset=UTF-8"))
                .andExpect(jsonPath("$").value("someXml"));
    }

    @Test
    public void setServicesAmount() throws Exception {

        when(processor.setServicesAmount(any(Request.class))).thenReturn(request);
        this.mvc.perform(post("/devBuilder/setSkillsCount").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").value(request));
    }

    @Test
    public void getStartUpData() throws Exception {

        Map<String, Request> map = new HashMap<>();
        map.put("req-01", request);
        map.put("req-02", request);

        StartUpData startUpData = new StartUpData();
        startUpData.setStatus(200);
        startUpData.setRaceTypes(RaceType.values());
        startUpData.setRequests(map);

        when(processor.getStartUpData()).thenReturn(startUpData);
        this.mvc.perform(get("/devBuilder/getStartUpData").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.raceTypes").isArray())
                .andExpect(jsonPath("$.raceTypes", hasSize(10)))
                .andExpect(jsonPath("$.raceTypes[0]").value("JEW"));
    }

}

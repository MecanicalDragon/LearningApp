package net.medrag.devBuilder.service;

import net.medrag.devBuilder.model.Request;
import net.medrag.schema.RaceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
public class ProcessorTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Processor processor() {
            //  btw, this test goes on real database
            return new Processor(null, null, null);
        }
    }

    @Autowired
    private Processor processor;

    @MockBean
    private Repository repository;

    private Request request;

    @BeforeEach
    public void setUp() {
        request = Request.builder().key("key").name("Viktor").surname("Kram").age(25).skillsCount(5)
                .bday("12.12.1988 12:00").race("Churka").build();
    }

    @AfterEach
    public void tearDown() {
        request = null;
    }

    @Test
    public void process() {
        when(repository.getById(anyString())).thenReturn(request);
        String result = processor.process("task-09");
        System.out.println(result);
        verify(repository, times(1)).getById(anyString());
    }

    @Test
    public void removeRequest() {
        when(repository.remove(anyString())).thenReturn(request);
        processor.removeRequest("12");
        verify(repository, times(1)).remove("12");
    }

    @Test
    public void setServicesAmount() {
        when(repository.save(any(Request.class))).thenReturn(request);
        processor.setServicesAmount(request);
        verify(repository, times(1)).save(any(Request.class));
    }

    @Test
    public void getStartUpData() {
        when(repository.getAll()).thenReturn(null);
        processor.getStartUpData();
        verify(repository, times(1)).getAll();
    }

    @Test
    public void getRaceType() {
        RaceType race = null;
        try {
            Method getRaceType = processor.getClass().getDeclaredMethod("getRaceType", String.class);
            getRaceType.setAccessible(true);
            race = (RaceType) getRaceType.invoke(processor, "Barabashka");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(race);
        assertNotNull(race);
    }

    @PostConstruct
    public void init() {
        try {
            Field repositoryField = processor.getClass().getDeclaredField("repository");
            repositoryField.setAccessible(true);
            repositoryField.set(processor, repository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
package net.medrag.devBuilder.service;

import net.medrag.devBuilder.model.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
public class RepositoryTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public Repository repository() {
            //  btw, this test goes on real database
            return new Repository();
        }
    }

    @Autowired
    private Repository repository;

    private Request request;

    @BeforeEach
    public void setUp() {
        request = Request.builder().key("00").name("Viktor").surname("Kram").age(25).skillsCount(5)
                .bday("00.00.0000 12:00").race("Churka").build();
        repository.save(request);
    }

    @AfterEach
    public void tearDown() {
        request = null;
        repository.remove("00");
        repository.remove("-1");
    }

    @Test
    public void getById() {
        Request r = repository.getById("00");
        assertEquals(request.getKey(), r.getKey());
        assertEquals(request.getName(), r.getName());
        assertEquals(request.getSurname(), r.getSurname());
        assertEquals(request.getAge(), r.getAge());
    }

    @Test
    public void save() {
        request.setName("Boris");
        request.setName("Eltsin");
        repository.save(request);
        Request r = repository.getById("00");
        assertEquals(request.getKey(), r.getKey());
        assertEquals(request.getName(), r.getName());
        assertEquals(request.getSurname(), r.getSurname());
        assertEquals(request.getAge(), r.getAge());
    }

    @Test
    public void remove() {
        repository.remove("00");
        Request r = repository.getById("00");
        assertNull(r);
    }

    @Test
    public void getAll() {
        request.setKey("-1");
        request.setName("Boris");
        request.setSurname("Eltsin");
        repository.save(request);
        Map<String, Request> all = repository.getAll();
        Request boris = all.get("-1");
        Request viktor = all.get("00");
        assertNotNull(boris);
        assertNotNull(viktor);
        assertEquals(boris.getName(), "Boris");
        assertEquals(viktor.getName(), "Viktor");
    }
}
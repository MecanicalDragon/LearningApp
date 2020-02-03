package net.medrag.WsSimulator.controller

import net.medrag.WsSimulator.WsSimulator
import net.medrag.WsSimulator.model.Response
import net.medrag.WsSimulator.service.ResponseRepo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@ExtendWith(value = [SpringExtension::class])
@SpringBootTest(classes = [WsSimulator::class])
@TestPropertySource(locations = ["classpath:test.properties"])
@AutoConfigureMockMvc
internal class RControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var repo: ResponseRepo

    @BeforeEach
    fun setUp() {
        repo.save(Response("id", "xml"))
        repo.save(Response("id2", "xml2"))
        repo.save(Response("id3", "xml3"))
    }

    @AfterEach
    fun tearDown() {
        repo.deleteAll()
    }

    @Test
    fun getResp() {
        mvc.perform(get("/getResp").param("id", "id2"))
                .andExpect(jsonPath("$").isString)
                .andExpect(jsonPath("$").value("xml2"))
    }

    @Test
    fun getAll() {
        mvc.perform(get("/getReqs"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$[0]").value("id"))
                .andExpect(jsonPath("$[1]").value("id2"))
                .andExpect(jsonPath("$[2]").value("id3"))
    }
}
package net.medrag.devBuilder.service.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.medrag.schema.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Slf4j
@Service
public class JmsSender {

    static final String DESTINATION = "DESTINATION_QUEUE";

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendJms(Developer developer) {
        log.info("Sending message...");
        try {
            String msg = new ObjectMapper().writeValueAsString(developer);
            jmsTemplate.send(DESTINATION, mc -> mc.createObjectMessage(msg));
            log.info("Message have been sent.");
        } catch (JsonProcessingException e) {
            log.error("Exception during jms message sending: {}", e.getMessage());
        }
    }
}

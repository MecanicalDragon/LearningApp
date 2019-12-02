package net.medrag.devBuilder.service.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

/**
 * {@author} Stanislav Tretyakov
 * 22.11.2019
 */
@Slf4j
@Component
public class JmsReceiver {

    @JmsListener(destination = JmsSender.DESTINATION)
    public void receiveMsg(Message<String>message){
        log.info("New JMS message received!");
        log.info("{}", message.getPayload());
    }
}

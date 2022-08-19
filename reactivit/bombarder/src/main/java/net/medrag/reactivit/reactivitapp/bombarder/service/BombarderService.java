package net.medrag.reactivit.reactivitapp.bombarder.service;

import lombok.RequiredArgsConstructor;
import net.medrag.reactivit.dto.RequestDto;
import net.medrag.reactivit.dto.ResponseDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Stanislav Tretyakov
 * 22.05.2022
 */
@Service
@RequiredArgsConstructor
public class BombarderService {

    private final RestTemplate restTemplate;

    private final BombardSignHolder bombardSignHolder;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    @EventListener(ApplicationReadyEvent.class)
    public void bombard() {
        new Thread(() -> {
            while (true) {
                if (bombardSignHolder.getActive()) {
                    executor.submit(() -> {
                        sendRequest();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }).start();
    }

    private String sendRequest() {
        final var randomString = getRandomString();
        var dto = new RequestDto(randomString, randomString);
        HttpHeaders headers = new HttpHeaders();
        headers.add("rquid", randomString);
        HttpEntity<RequestDto> entity = new HttpEntity<>(dto, headers);
        restTemplate.postForEntity("http://localhost:34344/post", entity, ResponseDto.class);
        return randomString;
    }

    public String hit() {
        return sendRequest();
    }

    public String getRandomString() {
        Random random = ThreadLocalRandom.current();
        byte[] r = new byte[8];
        random.nextBytes(r);
        return Base64.encodeBase64String(r);
    }

    public String switchDispatch() {
        return bombardSignHolder.switchDispatch();
    }
}

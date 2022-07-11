package net.medrag.springexamples.mbean;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Service
@RequiredArgsConstructor
public class MbeanService {

    private final SignHolder signHolder;

    @PostConstruct
    void timed(){
        new Thread(() -> {
            while (true){
                System.out.println(signHolder.getActive());
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}

package net.medrag.curatorapp.springintegration

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.PostConstruct

/**
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Service
class LeaderService {
    val leaderSign = AtomicBoolean(false)

    @PostConstruct
    fun leaderJob() {
        Thread {
            while (true) {
                if (leaderSign.get()) {
                    println("I'm a leader!")
                    Thread.sleep(2000)
                }
            }
        }.apply {
            isDaemon = true
            start()
        }
    }
}

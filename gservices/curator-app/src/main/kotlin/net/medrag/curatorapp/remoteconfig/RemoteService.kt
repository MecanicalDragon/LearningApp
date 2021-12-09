package net.medrag.curatorapp.remoteconfig

import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

/**
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Service
class RemoteService(
    private val remoteProps: RemoteProps
) {

    @PostConstruct
    fun init() {
        Thread {
            while (true) {
                println(remoteProps.prop1)
                println(remoteProps.list)
                println(remoteProps.animals)
                TimeUnit.SECONDS.sleep(2)
            }
        }.apply {
            isDaemon = true
            start()
        }
    }
}

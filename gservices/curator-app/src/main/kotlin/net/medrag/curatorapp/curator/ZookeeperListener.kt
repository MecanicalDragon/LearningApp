package net.medrag.curatorapp.curator

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.watch.PersistentWatcher
import org.apache.curator.x.async.AsyncCuratorFramework
import org.apache.zookeeper.Watcher
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * @author Stanislav Tretyakov
 * 14.11.2021
 */
@Service
class ZookeeperListener(
    private val curator: CuratorFramework,
    private val aCurator: AsyncCuratorFramework
) {

    @PostConstruct
    fun listen() {

//        watches all the time app lives. Works with Zookeeper 3.6+
        val persistentWatcher = PersistentWatcher(curator, "/pw", false)
        persistentWatcher.listenable.addListener(
            Watcher {
                println(it)
            }
        )
        persistentWatcher.start()

//        simple watcher is disposable and die after being invoked
        aCurator.watched()
            .getData()
            .forPath("/watch")
            .event()
            .thenAccept {
                println(it)
            }
    }
}

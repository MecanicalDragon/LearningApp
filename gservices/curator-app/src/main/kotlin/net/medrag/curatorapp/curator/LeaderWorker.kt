package net.medrag.curatorapp.curator

import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.recipes.leader.LeaderSelector
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener
import org.apache.curator.framework.state.ConnectionState
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

/**
 * @author Stanislav Tretyakov
 * 14.11.2021
 */
@Service
class LeaderWorker(
    private val curator: CuratorFramework
) {

    private val listener = object : LeaderSelectorListener {

        //        https://curator.apache.org/curator-recipes/leader-election.html
        override fun stateChanged(client: CuratorFramework?, newState: ConnectionState?) {
            println("state changed: $newState")
        }

        override fun takeLeadership(client: CuratorFramework?) {
            while (true) {
                println("working...")
                TimeUnit.SECONDS.sleep(2)
            }
        }
    }

    @PostConstruct
    fun doAsLeader() {
        val leaderSelector = LeaderSelector(curator, "/leader/job", listener)

        // start() can be called only once - other times it will throw Exception. require() for multiple invocations.
        leaderSelector.start()
    }
}

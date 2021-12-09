package net.medrag.curatorapp.springintegration

import org.springframework.integration.leader.AbstractCandidate
import org.springframework.integration.leader.Context
import org.springframework.stereotype.Component

/**
 * @author Stanislav Tretyakov
 * 09.12.2021
 */
@Component
class LeaderCandidate(
    private val leaderService: LeaderService
) : AbstractCandidate() {

    override fun onGranted(ctx: Context) {
        leaderService.leaderSign.set(true)
    }

    override fun onRevoked(ctx: Context) {
        leaderService.leaderSign.set(false)
    }
}

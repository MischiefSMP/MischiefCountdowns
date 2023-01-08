package com.mischiefsmp.countdowns

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object CountdownManager {
    private val pl = MischiefCountdowns.plugin
    private val bossBars = ConcurrentHashMap<UUID, BossBar>()
    var busy = false

    private fun update(cd: Int) {
        pl.server.onlinePlayers.forEach {
            if(it.hasPermission("countdowns.view")) {
                it.sendMessage("${pl.config.prefix} $cd")
                val id = it.uniqueId
                val bar = bossBars[id] ?: BossBar.bossBar(Component.text(cd), 0.0F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20).also { bossBar ->
                    bossBars[id] = bossBar
                    it.showBossBar(bossBar)
                }
                bar.name(Component.text(cd))
                bar.progress(0.0F)
            }
        }
    }

    fun create(time: Int) {
        object: BukkitRunnable() {
            val reach = System.currentTimeMillis() / 1000 + time
            var next = System.currentTimeMillis() / 1000 + 1
            var cd = time
            override fun run() {
                val current = System.currentTimeMillis() / 1000

                if(current == next) {
                    next++
                    update(cd)
                    cd--
                }

                if(current > reach) {
                    busy = false
                    bossBars.forEach { (uuid, bb) ->
                        pl.server.getPlayer(uuid)?.hideBossBar(bb)
                        bossBars.remove(uuid)
                    }
                    cancel()
                }
            }
        }.runTaskTimer(pl, 0, 1)
    }
}
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

    private fun update(cd: Int, progress: Float) {
        pl.server.onlinePlayers.forEach {
            if(it.hasPermission("countdowns.view")) {
                it.sendMessage("${pl.config.prefix} $cd")
                if(pl.config.bossbar) {
                    val id = it.uniqueId
                    val bar = bossBars[id] ?: BossBar.bossBar(Component.text(cd), progress, BossBar.Color.valueOf(pl.config.barColor), BossBar.Overlay.NOTCHED_20).also { bossBar ->
                        bossBars[id] = bossBar
                    }
                    it.showBossBar(bar)
                    bar.name(Component.text(cd))
                    bar.progress(progress)
                }
            }
        }
    }

    fun create(time: Int) {
        busy = true
        object: BukkitRunnable() {
            val reach = System.currentTimeMillis() / 1000 + time
            var next = System.currentTimeMillis() / 1000 + 1
            var cd = time
            override fun run() {
                val current = System.currentTimeMillis() / 1000

                if(current == next) {
                    next++
                    update(cd, (reach - current + 1).toFloat() / time)
                    cd--
                }

                if(current > reach) {
                    busy = false
                    clearBossbars()
                    cancel()
                }
            }
        }.runTaskTimer(pl, 0, 1)
    }

    fun clearBossbars() {
        bossBars.forEach { (uuid, bb) ->
            pl.server.getPlayer(uuid)?.hideBossBar(bb)
            bossBars.remove(uuid)
        }
    }
}
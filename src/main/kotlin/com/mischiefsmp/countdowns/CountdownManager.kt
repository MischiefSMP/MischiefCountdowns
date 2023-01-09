package com.mischiefsmp.countdowns

import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.ConcurrentHashMap

object CountdownManager {
    private val pl = MischiefCountdowns.plugin
    private val bars = ConcurrentHashMap<String, BossBar>()

    private fun update(id: String, cd: String, progress: Float) {
        val bossBar = bars[id]!!
        pl.server.onlinePlayers.forEach {
            if(it.hasPermission("countdowns.view")) {
                it.sendMessage("${pl.config.prefix} $cd")
                if(pl.config.bossbar) {
                    if(!bossBar.players.contains(it))
                        bossBar.addPlayer(it)
                    bossBar.setTitle(cd)
                    bossBar.progress = progress.toDouble()
                }
            }
        }
    }

    fun stop(id: String): Boolean {
        if(!bars.containsKey(id)) return false
        pl.server.onlinePlayers.forEach {
            bars[id]?.removePlayer(it)
        }
        bars.remove(id)
        return true
    }

    fun start(id: String, time: Int, color: String): Boolean {
        if(bars.containsKey(id)) return false

        bars[id] = pl.server.createBossBar("Countdown", BarColor.valueOf(color), BarStyle.SOLID)
        object: BukkitRunnable() {
            val reach = System.currentTimeMillis() / 1000 + time
            var next = System.currentTimeMillis() / 1000 + 1
            var cd = time
            override fun run() {
                val current = System.currentTimeMillis() / 1000

                if(current == next) {
                    next++
                    update(id, "$id: $cd", (reach - current + 1).toFloat() / time)
                    cd--
                }

                if(current > reach) {
                    stop(id)
                    cancel()
                }
            }
        }.runTaskTimer(pl, 0, 1)
        return true
    }

    fun clearBossbars() {
        pl.server.onlinePlayers.forEach { player ->
            bars.keys.forEach {
                bars[it]?.removePlayer(player)
            }
        }
        bars.clear()
    }
}
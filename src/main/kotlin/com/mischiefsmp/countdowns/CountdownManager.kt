package com.mischiefsmp.countdowns

import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.scheduler.BukkitRunnable

object CountdownManager {
    private val pl = MischiefCountdowns.plugin
    private var bossBar: BossBar? = null

    private fun update(cd: Int, progress: Float) {
        pl.server.onlinePlayers.forEach {
            if(it.hasPermission("countdowns.view")) {
                it.sendMessage("${pl.config.prefix} $cd")
                if(pl.config.bossbar && bossBar != null) {
                    if(!bossBar!!.players.contains(it))
                        bossBar!!.addPlayer(it)
                    bossBar!!.setTitle(cd.toString())
                    bossBar!!.progress = progress.toDouble()
                }
            }
        }
    }

    fun create(time: Int) {
        bossBar = pl.server.createBossBar("Countdown", BarColor.valueOf(pl.config.barColor), BarStyle.SOLID)
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
                    clearBossbars()
                    cancel()
                }
            }
        }.runTaskTimer(pl, 0, 1)
    }

    fun isBusy() = bossBar != null

    fun clearBossbars() {
        pl.server.onlinePlayers.forEach {
            bossBar?.removePlayer(it)
        }
        bossBar = null
    }
}
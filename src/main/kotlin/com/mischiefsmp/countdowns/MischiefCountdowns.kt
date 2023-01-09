package com.mischiefsmp.countdowns

import org.bukkit.plugin.java.JavaPlugin

class MischiefCountdowns: JavaPlugin() {
    init {
        plugin = this
    }

    val config = Config.load()

    override fun onEnable() {
        LangManager.init()
        getCommand("countdown")!!.run {
            CountdownCommand().also { cmd ->
                setExecutor(cmd)
                tabCompleter = cmd
            }
        }
    }

    override fun onDisable() {
        CountdownManager.clearBossbars()
    }

    companion object {
        lateinit var plugin: MischiefCountdowns
    }
}
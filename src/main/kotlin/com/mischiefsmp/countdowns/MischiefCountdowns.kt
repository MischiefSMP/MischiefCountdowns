package com.mischiefsmp.countdowns

import org.bukkit.plugin.java.JavaPlugin

class MischiefCountdowns: JavaPlugin() {
    init {
        plugin = this
    }

    val config = Config.load()

    override fun onEnable() {
        LangManager.init()
    }

    companion object {
        lateinit var plugin: MischiefCountdowns
    }
}
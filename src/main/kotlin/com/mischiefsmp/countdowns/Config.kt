package com.mischiefsmp.countdowns

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

class Config {
    lateinit var languages: List<String>
    lateinit var language: String

    fun save() = mapper.writeValue(cfgFile, this)

    companion object {
        private val mapper = ObjectMapper(YAMLFactory())
        private val pl = MischiefCountdowns.plugin
        private val cfgFile = File(pl.dataFolder, "config.yml")

        fun load(): Config {
            Utils.saveResource("config.yml")
            return mapper.readValue(cfgFile, Config::class.java)
        }
    }
}
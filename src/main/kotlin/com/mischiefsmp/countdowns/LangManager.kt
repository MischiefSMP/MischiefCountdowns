package com.mischiefsmp.countdowns

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

class TLConfig {
    @JsonProperty("cmd-noperm")
    lateinit var cmdNoPerm: String
    @JsonProperty("cmd-missing-seconds")
    lateinit var cmdMissingSec: String
    @JsonProperty("cmd-bad-max")
    lateinit var cmdMaxMessage: String
    @JsonProperty("cmd-bad-min")
    lateinit var cmdMinMessage: String
    @JsonProperty("cmd-busy")
    lateinit var cmdBusy: String
}

object LangManager {
    private val pl = MischiefCountdowns.plugin
    private val map = HashMap<String, TLConfig>()

    fun init() {
        val mapper = ObjectMapper(YAMLFactory())

        pl.config.languages.forEach {
            val path = "lang${File.separator}$it.yml"
            Utils.saveResource(path)
            map[it] = mapper.readValue(File(pl.dataFolder, path), TLConfig::class.java)
        }
    }

    fun tl(language: String) = map[language]
    fun tl() = map[pl.config.language]
}
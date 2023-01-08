package com.mischiefsmp.countdowns

import java.io.File

object Utils {
    private val pl = MischiefCountdowns.plugin

    //This avoids having to check every time if the resource exists, to avoid the annoying "Cant copy, exists!" message even if overwrite = false
    fun saveResource(path: String, overwrite: Boolean = false) {
        if(!File(pl.dataFolder, path).exists() || overwrite)
            pl.saveResource(path, true)
    }
}
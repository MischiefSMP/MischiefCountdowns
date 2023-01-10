package com.mischiefsmp.countdowns

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

class PlaceholderExtension: PlaceholderExpansion() {
    private val plugin = MischiefCountdowns.plugin

    override fun getIdentifier() = "mischiefcountdowns"
    override fun getAuthor() = plugin.description.authors.toTypedArray().contentToString()
    override fun getVersion() = plugin.description.version
    override fun persist() = true

    //  Placeholders:
    //      countdown.id.<id>
    //      countdown.number.<id>
    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        val list = params.split("_")
        val type = list.getOrNull(0) ?: return null
        val id = list.getOrNull(1) ?: return null
        return when(type) {
            "id" -> CountdownManager.getPrettyId(id)
            "number" -> CountdownManager.getCountdown(id).toString()
            else -> null
        }
    }
}
package com.mischiefsmp.countdowns

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CountdownCommand: CommandExecutor {
    private val pl = MischiefCountdowns.plugin

    private fun send(sender: CommandSender, msg: String) {
        sender.sendMessage("${pl.config.prefix} $msg")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val tl = LangManager.tl()!!

        if(args.isEmpty() || args[0].toIntOrNull() == null) {
            send(sender, tl.cmdMinMessage)
            return true
        }

        val time = args[0].toInt()
        val maxTime = pl.config.maxTime

        if(time > maxTime && maxTime != -1) {
            send(sender, String.format(tl.cmdMaxMessage, maxTime))
            return true
        }

        if(time < 1) {
            send(sender, tl.cmdMinMessage)
            return true
        }

        if(CountdownManager.isBusy()) {
            send(sender, tl.cmdBusy)
            return true
        }

        CountdownManager.create(time)
        return true
    }
}
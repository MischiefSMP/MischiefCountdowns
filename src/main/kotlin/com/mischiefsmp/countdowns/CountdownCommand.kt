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

        if(args.size < 2) {
            send(sender, tl.cmdUsage)
            send(sender, pl.server.getPluginCommand("countdown")!!.usage)
            return true
        }

        val id = args[1]

        when(args[0]) {
            "start" -> {
                val time = args[2].toInt()
                val maxTime = pl.config.maxTime
                val color = args.getOrNull(3)?.uppercase() ?: pl.config.barColor

                //Min check
                if(time < 1) {
                    send(sender, tl.cmdMinMessage)
                    return true
                }

                //Max time
                if(time > maxTime && maxTime != -1) {
                    send(sender, String.format(tl.cmdMaxMessage, maxTime))
                    return true
                }

                if(!CountdownManager.create(id, time, color)) {
                    send(sender, tl.cmdBusy)
                    return true
                }
            }
            "stop" -> {

            }
        }

        return true
    }
}
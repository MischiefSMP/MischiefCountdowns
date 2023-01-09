package com.mischiefsmp.countdowns

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter

class CountdownCommand: CommandExecutor, TabCompleter {
    private val pl = MischiefCountdowns.plugin

    private fun send(sender: CommandSender, msg: String) {
        sender.sendMessage("${pl.config.prefix} $msg")
    }

    private fun permCheck(sender: CommandSender, perm: String) = sender is ConsoleCommandSender || sender.isOp || sender.hasPermission(perm)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val tl = LangManager.tl()!!

        if(args.size < 2) {
            badUsage(sender, tl)
            return true
        }

        val id = args[1]

        when(args[0]) {
            "start" -> {
                if(!permCheck(sender, "countdowns.start")) {
                    send(sender, tl.cmdNoPerm)
                    return true
                }

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

                if(CountdownManager.start(id, time, color)) send(sender, tl.cmdStarted)
                else send(sender, tl.cmdBusy)
                return true
            }
            "stop" -> {
                if(!permCheck(sender, "countdowns.stop")) {
                    send(sender, tl.cmdNoPerm)
                    return true
                }

                if(CountdownManager.stop(id)) send(sender, tl.cmdStoppedYes)
                else send(sender, tl.cmdStoppedNo)
                return true
            }
        }

        badUsage(sender, tl)
        return true
    }

    private fun badUsage(sender: CommandSender, tl: TLConfig) {
        send(sender, tl.cmdBadUsage)
        send(sender, pl.server.getPluginCommand("countdown")!!.usage)
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): MutableList<String> {
        val list = MutableList(0) { "" }
        when(args.size) {
            1 -> {
                if(permCheck(sender, "countdowns.start") && "start".startsWith(args[0]))
                    list.add("start")
                if(permCheck(sender, "countdowns.stop") && "stop".startsWith(args[0]))
                    list.add("stop")
            }
            2 -> {
                if(args[0] == "start") {
                    list.add("<id>")
                } else if(args[0] == "stop") {
                    CountdownManager.getBarNames().forEach {
                        if(it.startsWith(args[1]))
                            list.add(it)
                    }
                }
            }
            3 -> {
                if(args[0] == "start")
                    list.add("<seconds>")
            }
            4 -> {
                if(args[0] == "start") {
                    listOf("pink", "blue", "red", "green", "yellow", "purple", "white").forEach {
                        if(it.startsWith(args[3]))
                            list.add(it)
                    }
                }
            }
        }
        return list
    }
}
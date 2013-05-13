package com.udonya.signfix.command.sf;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.udonya.signfix.SignFix;
import com.udonya.signfix.command.AbstractCommand;
import com.udonya.signfix.command.CmdOwner;

public class HelpCommand extends AbstractCommand {

    public HelpCommand(String name, SignFix plugin) {
        super(name, plugin);
        owner = CmdOwner.valueOf(true, true);
        setDescription("Help Command");
        setUsage("/sf help");
    }

    @Override
    public boolean areCompatibleParameters(CommandSender sender, Command command, String s, String[] args) {
        if (args == null) return false;
        if (args.length != 1) return false;
        if (!args[0].equalsIgnoreCase("help")) return false;
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return true;
        sender.sendMessage("How to input text to the sign.");
        sender.sendMessage("  1. You punch the Sign.");
        sender.sendMessage("  2. Input: /sf set <Line-1> <Line-2> <Line-3> <Line-4>");
        sender.sendMessage("  ex. /sf set FirstLine \"\" \"3rd Line\" 4thLine");
        sender.sendMessage("How to toggle Enable/Disable this plugin at each player.");
        sender.sendMessage("  ex. /sf toggle");
        return true;
    }
}

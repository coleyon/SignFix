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
        setDescription("Help command");
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
        sender.sendMessage(clrCmd + "How to input text to the sign.");
        sender.sendMessage(clrCmd + "  1. Punch the Sign.");
        sender.sendMessage(clrCmd + "  2. Input: /sf set " + clrReq + "<Line-1> <Line-2> <Line-3> <Line-4>");
        sender.sendMessage(clrCmd + "  ex. /sf set " + clrReq + "FirstLine \"\" \"3rd Line\" 4thLine");
        sender.sendMessage(clrCmd + "How to toggle Enable/Disable this plugin at each player.");
        sender.sendMessage(clrCmd + "  ex. /sf toggle");
        return true;
    }
}

package com.udonya.signfix.command.sf;

import java.util.Map;

import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.udonya.signfix.SignFix;
import com.udonya.signfix.command.AbstractCommand;
import com.udonya.signfix.command.CmdOwner;

public class SetCommand  extends AbstractCommand {

    public SetCommand(String name, SignFix plugin) {
        super(name, plugin);
        owner = CmdOwner.valueOf(true, true);
        setDescription("");
        setPermission("signfix.set");
        setUsage("/sf set <line1> <line2> <line3> <line4>");
    }

    @Override
    public boolean areCompatibleParameters(CommandSender sender, Command command, String s, String[] args) {
        if(args == null) return false;
        if (args.length < 2 || 5 < args.length) return false;
        if (!args[0].equalsIgnoreCase("set")) return false;
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        Map<String, Sign> clicked = this.plugin.getClicked();
        if(!clicked.containsKey(sender.getName())) return false;
        Sign sign = clicked.get(sender.getName());
        try {
            sign.setLine(0, args[1]);
            sign.setLine(1, args[2]);
            sign.setLine(2, args[3]);
            sign.setLine(3, args[4]);
        } catch (Exception e) {
            sender.sendMessage("Could not set your input character to the sign.");
            return false;
        }
        return sign.update();
    }
}

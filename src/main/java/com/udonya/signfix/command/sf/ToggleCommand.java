package com.udonya.signfix.command.sf;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.udonya.signfix.SignFix;
import com.udonya.signfix.command.AbstractCommand;
import com.udonya.signfix.command.CmdOwner;

public class ToggleCommand extends AbstractCommand {

    public ToggleCommand(String name, SignFix plugin) {
        super(name, plugin);
        owner = CmdOwner.valueOf(true, true);
        setDescription("Toggle Enable Disable this plugin's function");
        setPermission("signfix.toggle");
        setUsage("/sf toggle");
    }

    @Override
    public boolean areCompatibleParameters(CommandSender sender, Command command, String s, String[] args) {
        if(args == null) return false;
        if (args.length != 1) return false;
        if (!args[0].equalsIgnoreCase("toggle")) return false;
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) return false;
        Map<String, Boolean> enables = this.plugin.getEnables();
        if(!enables.containsKey(sender.getName())) return false;
        Boolean flg = enables.get(sender.getName());
        if(flg){
            enables.put(sender.getName(), false);
            sender.sendMessage("Disabled SignFix.");
        }else{
            enables.put(sender.getName(), true);
            sender.sendMessage("Enabled SignFix.");
        }
        this.plugin.setEnables(enables);
        return true;
    }
}

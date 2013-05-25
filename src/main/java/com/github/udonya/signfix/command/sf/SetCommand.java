package com.github.udonya.signfix.command.sf;

import java.util.Map;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import com.github.udonya.signfix.SignFix;
import com.github.udonya.signfix.command.AbstractCommand;
import com.github.udonya.signfix.command.CmdOwner;

public class SetCommand  extends AbstractCommand {

    public SetCommand(String name, SignFix plugin) {
        super(name, plugin);
        owner = CmdOwner.valueOf(true, true);
        setDescription("Set specified text to the Sign.");
        setPermission("signfix.enable");
        setUsage("/sf set <line1> <line2> <line3> <line4>");
    }

    @Override
    public boolean areCompatibleParameters(CommandSender sender, Command command, String s, String[] args) {
        if(args == null) return false;
        if(args.length < 2) return false;
        if(!args[0].equalsIgnoreCase("set")) return false;
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(this.plugin.getDisabled().contains(sender.getName())) return false;
        Map<String, Sign> clicked = this.plugin.getClicked();
        if(!clicked.containsKey(sender.getName())) return false;
        Sign sign = clicked.get(sender.getName());
        this.plugin.getSignLines().put(sender.getName(), args);
        this.plugin.getServer().getPluginManager().callEvent(new BlockBreakEvent(sign.getBlock(), (Player)sender));
        return true;
    }

}

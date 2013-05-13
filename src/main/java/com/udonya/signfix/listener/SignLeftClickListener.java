package com.udonya.signfix.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.udonya.signfix.SignFix;

public class SignLeftClickListener implements Listener {
    /**
     * Refs of plugin instance
     */
    private final SignFix plugin;

    /**
     * Constructor
     * @param plugin
     */
    public SignLeftClickListener(SignFix plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Record the right clicking a sign.
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (this.plugin.getDisabled().contains(event.getPlayer().getName())) return;
        if(event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        if(!event.getClickedBlock().getType().equals(Material.SIGN) && !event.getClickedBlock().getType().equals(Material.SIGN_POST)) return;
        this.plugin.getClicked().put(event.getPlayer().getName(), (Sign) event.getClickedBlock().getState());
        event.getPlayer().sendMessage(ChatColor.AQUA.toString() + "[SignFix] Selected the sign you've hit.");
    }
}

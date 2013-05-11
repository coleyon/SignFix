package com.udonya.signfix.listener;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.udonya.signfix.SignFix;

public class SignBreakableCheckListener implements Listener {
    /**
     * Refs of plugin instance
     */
    private final SignFix plugin;

    /**
     * Constructor
     * @param plugin
     */
    public SignBreakableCheckListener(SignFix plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSignBreakableCheck(BlockBreakEvent event){
        event.getPlayer().sendMessage("CheckStarting...");
        if (event.isCancelled()) return;						// Breaking the block is cancelled by other plugins.
        if (event.getPlayer() == null) return;					// Breaking the block is no related to this plugin.
        if (!this.plugin.getClicked().containsKey(event.getPlayer().getName())) return;
        Sign sign = this.plugin.getClicked().get(event.getPlayer().getName());
        if (!isSameLocation(event.getBlock(), sign)) return;	// The block is not same sign.

        event.getBlock().getDrops().clear();
        event.setCancelled(true);
        event.getPlayer().sendMessage("drop item Canceled!");

        if(!this.plugin.getSignLines().containsKey(event.getPlayer().getName())) return;
        try {
            String[] args = this.plugin.getSignLines().get(event.getPlayer().getName());
            sign.setLine(0, args[1]);
            sign.setLine(1, args[2]);
            sign.setLine(2, args[3]);
            sign.setLine(3, args[4]);
        } catch (Exception e) {
            event.getPlayer().sendMessage("Could not set your input character to the sign.");
            return;
        }
        sign.update();
    }

    /**
     * Check the block and the sign is same block.
     * @param block
     * @param sign
     * @return
     */
    private boolean isSameLocation(Block block, Sign sign){
        if (!block.getLocation().getWorld().getName().equals(sign.getLocation().getWorld().getName())) return false;
        if (!block.getType().equals(sign.getType())) return false;
        if (block.getLocation().getBlockX() != sign.getLocation().getBlockX()) return false;
        if (block.getLocation().getBlockY() != sign.getLocation().getBlockY()) return false;
        if (block.getLocation().getBlockZ() != sign.getLocation().getBlockZ()) return false;
        if (block.getLocation().getPitch() != sign.getLocation().getPitch()) return false;
        if (block.getLocation().getYaw() != sign.getLocation().getYaw()) return false;
        return true;
    }
}

package com.udonya.signfix.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.udonya.signfix.SignFix;

public class SignBreakableCheckListener implements Listener {
    private static final char SPACE = ' ';
    private static final char DQUOT = '"';

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
        String[] args = getArgs(this.plugin.getSignLines().get(event.getPlayer().getName()), 1);
        for (int i = 0; i < args.length; i++) {
            event.getPlayer().sendMessage(Integer.toString(i) + ": " + args[i]);
            sign.setLine(i, args[i]);
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

    private String[] getArgs(String[] args, int excludeIdx){
        char[] chars = getCharArray(args);
        List<String> newArgs = getFixedArgs(chars);
        for (int i = 0; i < excludeIdx; i++) {
            newArgs.remove(0);
        }
        return newArgs.toArray(new String[newArgs.size()]);
    }

    private char[] getCharArray(String[] args){
        StringBuilder sb = new StringBuilder();
        for (String string : args) {
            sb.append(string);
            sb.append(SPACE);
        }
        return sb.toString().toCharArray();
    }

    private List<String> getFixedArgs(char[] chars){
        boolean quoting = false;
        boolean separating = false;
        List<String> newArgs = new ArrayList<String>();
        StringBuffer buff = new StringBuffer();
        for (char c : chars) {
            // check space separation
            if(c == SPACE && !quoting){
                // ignore. (quotation separeted just before)
                if(buff.length() == 0) continue;
                // no quoting separation
                separating = true;
            }
            // check quote separation
            if(c == DQUOT){
                if(!quoting){
                    // start quotation
                    quoting = true;
                    separating = false;
                    continue;
                }else{
                    // end quotation
                    quoting = false;
                    separating = true;
                }
            }

            if(separating){
                // separate buffer
                newArgs.add(buff.toString());
                buff = new StringBuffer();
                separating = false;
                quoting = false;
            }else{
                // add character
                buff.append(c);
            }
        }
        return newArgs;
    }
}

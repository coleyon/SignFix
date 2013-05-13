package com.udonya.signfix.listener;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.udonya.signfix.SignFix;

public class CheckSignBreakableListener implements Listener {
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
    public CheckSignBreakableListener(SignFix plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCheckSignBreakable(BlockBreakEvent event){
        if (event.isCancelled()) return;						// Breaking the block is cancelled by other plugins.
        if (event.getPlayer() == null) return;					// Breaking the block is no related to this plugin.
        String playerName = event.getPlayer().getName();
        if (!this.plugin.getClicked().containsKey(playerName)) return;
        Sign sign = this.plugin.getClicked().get(playerName);
        if (!isSameLocation(event.getBlock(), sign)) return;	// The block is not same sign.
        //event.getBlock().getDrops().clear();
        if(!this.plugin.getSignLines().containsKey(playerName)) return;
        String[] args = getArgs(this.plugin.getSignLines().get(playerName), 1);
        for (int i = 0; i < args.length; i++) {
            sign.setLine(i, args[i]);
        }
        if(sign.update()) this.plugin.getSignLines().remove(playerName);
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

    /**
     *
     * @param args
     * @param excludeIdx
     * @return
     */
    private String[] getArgs(String[] args, int excludeIdx){
        char[] chars = getCharArray(args);
        List<String> newArgs = getFixedArgs(chars);
        for (int i = 0; i < excludeIdx; i++) {
            newArgs.remove(0);
        }
        return newArgs.toArray(new String[newArgs.size()]);
    }

    /**
     *
     * @param args
     * @return
     */
    private char[] getCharArray(String[] args){
        StringBuilder sb = new StringBuilder();
        for (String string : args) {
            sb.append(string);
            sb.append(SPACE);
        }
        return sb.toString().toCharArray();
    }

    /**
     * create fixed args.
     * @param chars
     * @return
     */
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

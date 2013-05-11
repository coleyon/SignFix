package com.udonya.signfix;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;
import com.udonya.signfix.command.AbstractCommandHandler;
import com.udonya.signfix.command.sf.CommandHandler;
import com.udonya.signfix.listener.PlayerQuitListener;
import com.udonya.signfix.listener.SignRightClickListener;

public class SignFix extends JavaPlugin {
    /**
     * Sign clicking records.
     */
    private Map<String, Sign> clicked;
    private Map<String, Boolean> enables;

    private AbstractCommandHandler cmdExecutor;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        clicked = new HashMap<String, Sign>();
        enables = new HashMap<String, Boolean>();

        // register events
        new PlayerQuitListener(this);
        new SignRightClickListener(this);

        // register commands
        this.cmdExecutor = new CommandHandler(this);
        getCommand(this.cmdExecutor.getCmdName()).setExecutor(this.cmdExecutor);
    }

    /**
     * Get Sign clicking records.
     * @return
     */
    public Map<String, Sign> getClicked() {
        return clicked;
    }

    /**
     * Set Sign clicking records.
     * @param clicked
     */
    public void setClicked(Map<String, Sign> clicked) {
        this.clicked = clicked;
    }

    public Map<String, Boolean> getEnables() {
        return enables;
    }

    public void setEnables(Map<String, Boolean> enables) {
        this.enables = enables;
    }

}

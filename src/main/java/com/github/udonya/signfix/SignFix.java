package com.github.udonya.signfix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.block.Sign;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.udonya.signfix.command.AbstractCommandHandler;
import com.github.udonya.signfix.command.sf.CommandHandler;
import com.github.udonya.signfix.listener.CheckSignBreakableListener;
import com.github.udonya.signfix.listener.PlayerQuitListener;
import com.github.udonya.signfix.listener.SignLeftClickListener;

public class SignFix extends JavaPlugin {
    /**
     * Sign clicking records.
     */
    private Map<String, Sign> clicked;
    /**
     * Each player's plugin function disabled status.
     */
    private Set<String> disabled;
    /**
     * Sign's each Lines.
     */
    private Map<String, String[]> signLines;

    private AbstractCommandHandler cmdExecutor;

    /**
     * Vault permission manager
     */
    public static Permission permission = null;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        clicked = new HashMap<String, Sign>();
        disabled = new HashSet<String>();
        signLines = new HashMap<String, String[]>();

        // register events
        new PlayerQuitListener(this);
        new SignLeftClickListener(this);
        new CheckSignBreakableListener(this);

        // register commands
        this.cmdExecutor = new CommandHandler(this);
        getCommand(this.cmdExecutor.getCmdName()).setExecutor(this.cmdExecutor);

        setupPermissions();
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

    public Set<String> getDisabled() {
        return disabled;
    }

    public void setDisabled(Set<String> disabled) {
        this.disabled = disabled;
    }

    public Map<String, String[]> getSignLines() {
        return signLines;
    }

    public void setSignLines(Map<String, String[]> signLines) {
        this.signLines = signLines;
    }

    private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

}

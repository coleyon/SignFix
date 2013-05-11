package com.udonya.signfix.command.sf;

import com.udonya.signfix.SignFix;
import com.udonya.signfix.command.AbstractCommandHandler;

public class CommandHandler extends AbstractCommandHandler {

    /**
     * The BAT commands handler
     *
     * @param plugin
     */
    public CommandHandler(SignFix plugin) {
        // set root command name
        cmdName = "signfix";
        providedCmds.add(new SetCommand(cmdName, plugin));
        providedCmds.add(new ToggleCommand(cmdName, plugin));
    }
}

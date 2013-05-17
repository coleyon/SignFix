package com.github.udonya.signfix.command.sf;

import com.github.udonya.signfix.SignFix;
import com.github.udonya.signfix.command.AbstractCommandHandler;

public class CommandHandler extends AbstractCommandHandler {

    /**
     * The BAT commands handler
     *
     * @param plugin
     */
    public CommandHandler(SignFix plugin) {
        cmdName = "signfix";
        providedCmds.add(new SetCommand(cmdName, plugin));
        providedCmds.add(new ToggleCommand(cmdName, plugin));
        providedCmds.add(new HelpCommand(cmdName, plugin));
    }
}

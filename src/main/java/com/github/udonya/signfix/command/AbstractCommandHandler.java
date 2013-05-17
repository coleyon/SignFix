package com.github.udonya.signfix.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 抽象コマンドハンドラ
 *
 * @author udonya
 *
 */
public abstract class AbstractCommandHandler implements CommandExecutor {
    protected static final String clrCmd = ChatColor.AQUA.toString();			// main commands
    protected static final String clrReq = ChatColor.GREEN.toString();			// parameters
    protected static final String clrDesc = ChatColor.WHITE.toString();			// command descriptions
    protected static final String clrErr = ChatColor.RED.toString();			// errors / notices

    /**
     * All provided plugin commands
     */
    protected final List<AbstractCommand> providedCmds = new ArrayList<AbstractCommand>();
    /**
     * root command's name
     */
    protected String cmdName;

    /**
     * Executing command with checking syntax, executable, permissible.
     */
    public boolean onCommand(CommandSender commandSender, Command command, String commandLabel, String[] args) {
        AbstractCommand tgtCmd = syntaxMatching(commandSender, command, commandLabel, args);
        if (tgtCmd == null) {
            commandSender.sendMessage(clrErr + "Specified pattern is not exists this plugin.");
            return false;
        }
        if (!isExecutable(commandSender, tgtCmd, args)) {
            return false;
        }
        return tgtCmd.execute(commandSender, commandLabel, args);
    }

    /**
     * Throwed command's syntax matching
     *
     * @return AbstractCommand Command instance matching to inputed command
     *         line.
     */
    protected AbstractCommand syntaxMatching(CommandSender commandSender, Command command, String commandLabel, String[] args) {
        for (AbstractCommand cmd : this.providedCmds) {
            if (cmd.areCompatibleParameters(commandSender, cmd, commandLabel, args)) {
                return cmd;
            }
        }
        commandSender.sendMessage(clrErr + "Syntax unmatched.");
        return null;
    }

    /**
     * Checking command executable
     *
     * @param commandSender
     * @param command
     * @param args
     * @return
     */
    private boolean isExecutable(CommandSender commandSender, AbstractCommand command, String[] args) {
        if (commandSender instanceof Player) {
            // player command line
            if (!command.owner.isPlayerExecutable()) {
                commandSender
                        .sendMessage(clrErr + "This command is not player executable.");
                return false;
            }
            if (!isExecutablePerm(commandSender, command)) {
                return false;
            }
        } else {
            // console command line
            if (!command.owner.isConsoleExecutable()) {
                commandSender
                        .sendMessage(clrErr + "This command is not console executable.");
                return false;
            }
        }
        return true;
    }

    /**
     * Checking perm using Vault
     *
     * @param commandSender
     * @param command
     * @return
     */
    private boolean isExecutablePerm(CommandSender commandSender,
            AbstractCommand command) {
        return true;
    }

    public String getCmdName() {
        return cmdName;
    }
}

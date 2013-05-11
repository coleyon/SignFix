package com.udonya.signfix.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.udonya.signfix.SignFix;

/**
 * 抽象コマンド
 *
 * @author udonya
 */
public abstract class AbstractCommand extends Command {

    /**
     * オーナー情報
     */
    protected CmdOwner owner;

    protected final SignFix plugin;

    /**
     * コンストラクタ
     *
     * @param name
     */
    public AbstractCommand(String name, SignFix plugin) {
        super(name);
        this.plugin = plugin;
    }

    /**
     * 投入コマンドの構文適合チェック
     *
     * @param sender
     * @param command
     * @param s
     * @param args
     * @return
     */
    public abstract boolean areCompatibleParameters(
            CommandSender sender, Command command, String s,
            String[] args);

    @Override
    public abstract boolean execute(CommandSender sender, String commandLabel, String[] args);
}

package com.github.udonya.signfix.command;

/**
 * Owner of commands
 *
 * @author udonya
 *
 */
public class CmdOwner {
    /**
     * コンソール経由の実行可否
     */
    private final boolean console;
    /**
     * プレイヤーによる実行可否
     */
    private final boolean player;

    /**
     * コンストラクタ
     *
     * @param console
     * @param player
     */
    private CmdOwner(boolean console, boolean player) {
        this.console = console;
        this.player = player;
    }

    public static CmdOwner valueOf(boolean console, boolean player) {
        return new CmdOwner(console, player);
    }

    /**
     * コンソールからの実行が可能か
     *
     * @return
     */
    public boolean isConsoleExecutable() {
        return console;
    }

    /**
     * プレイヤーが実行可能か
     *
     * @return
     */
    public boolean isPlayerExecutable() {
        return player;
    }
}

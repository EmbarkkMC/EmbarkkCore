package me.outspending.embarkkcore;

import me.outspending.embarkkcore.Misc.Scoreboard.Board;
import me.outspending.embarkkcore.Storage.DataEvents;
import me.outspending.embarkkcore.Storage.PlayerData;
import me.outspending.embarkkcore.Storage.PlayerDatabase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmbarkkCore extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(new Board(), plugin);
        Bukkit.getPluginManager().registerEvents(new DataEvents(), plugin);
        new PlayerDatabase();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Board.createBoard(player);
        }
        PlayerDatabase.checkAllPlayers();
    }

    @Override
    public void onDisable() {
        PlayerDatabase.saveAllPlayers(false);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}

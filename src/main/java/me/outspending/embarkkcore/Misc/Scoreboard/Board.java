package me.outspending.embarkkcore.Misc.Scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.outspending.embarkkcore.EmbarkkCore;
import me.outspending.embarkkcore.Storage.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.outspending.embarkkcore.Storage.PlayerDatabase;

import java.util.*;

public class Board  implements Listener {

    public static Map<UUID, FastBoard> boards = new HashMap<>();

    public Board() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (UUID uuid : boards.keySet()) {
                    refreshScoreboard(boards.get(uuid));
                }
            }
        }.runTaskTimerAsynchronously(EmbarkkCore.getPlugin(), 20, 20);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        createBoard(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        removeBoard(player);
    }

    public static void removeBoard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    public static void createBoard(Player player) {
        FastBoard board = new FastBoard(player);
        boards.put(player.getUniqueId(), board);
    }

    private void refreshScoreboard(FastBoard board) {
        Player plr = board.getPlayer();
        PlayerData data = PlayerDatabase.dataMap.get(plr.getUniqueId());
        board.updateTitle(Colors.translateColor("#e0bd07&lE#e4cb2a&lM#e7d84e&lB#ebe671&lA#e7d84e&lR#e4cb2a&lK#e0bd07&lK"));
        board.updateLines(
                Colors.translateColor(""),
                Colors.translateColor("&6&l" + Colors.toFancyText("level")),
                Colors.translateColor("&8&l● &7Level: #66a3ff" + data.getLevel()),
                Colors.translateColor("&8&l● &7Prestige: #005ce6" + data.getPrestige()),
                Colors.translateColor(""),
                Colors.translateColor("&6&l" + Colors.toFancyText("information")),
                Colors.translateColor("&8&l● &7Balance: #00e600$" + data.getBalance()),
                Colors.translateColor("&8&l● &7Tokens: #ffff00⛃" + data.getTokens()),
                Colors.translateColor("&8&l● &7Blocks Broken: #c68c53⛏" + data.getBlocks_broken()),
                Colors.translateColor("&8&l● &7Multiplier: #ff4d4dx" + data.getMultiplier()),
                Colors.translateColor(""),
                Colors.translateColor("&7&nEmbarkk&7.Minehut.gg")
        );
    }
}

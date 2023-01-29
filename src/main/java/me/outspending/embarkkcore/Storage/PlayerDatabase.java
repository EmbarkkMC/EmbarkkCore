package me.outspending.embarkkcore.Storage;

import me.outspending.embarkkcore.EmbarkkCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDatabase {

    public static String STORAGE_FILE = EmbarkkCore.getPlugin().getDataFolder().getAbsolutePath() + "/playerDatabase.db".replace("/", "//");
    public static Map<UUID, PlayerData> dataMap = new HashMap<>();

    static {
        File file = EmbarkkCore.getPlugin().getDataFolder();
        if (!file.exists()) {
            file.mkdir();
            createTable();
        }
    }

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + STORAGE_FILE);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS playerData (uuid VARCHAR(100), balance DOUBLE, tokens DOUBLE, level INT, xp INT, xp_max INT, prestige INT, blocks_broken DOUBLE, multiplier INT)";
        try {
            Connection connection = getConnection();
            connection.createStatement().execute(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createPlayer(UUID uuid) {
        String sql = "INSERT INTO playerData (uuid, balance, tokens, level, xp, xp_max, prestige, blocks_broken, multiplier) VALUES ('" + uuid.toString() + "', 0, 0, 0, 0, 250, 0, 0, 1)";
        try {
            Connection connection = getConnection();
            connection.createStatement().execute(sql);
            dataMap.put(uuid, new PlayerData(uuid));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grabPlayerData(UUID uuid) {
        String sql = "SELECT * FROM playerData WHERE uuid='" + uuid.toString() + "'";
        try {
            Connection connection = getConnection();
            ResultSet set = connection.createStatement().executeQuery(sql);
            while (set.next()) {
                dataMap.put(uuid, new PlayerData(uuid, set.getDouble("balance"), set.getDouble("tokens"), set.getInt("level"), set.getInt("xp"), set.getInt("xp_max"), set.getInt("prestige"), set.getDouble("blocks_broken"), set.getInt("multiplier")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayer(UUID uuid, boolean delete) {
        PlayerData data = dataMap.get(uuid);
        String sql = "UPDATE playerData SET balance=?, tokens=?, level=?, xp=?, xp_max=?, prestige=?, blocks_broken=?, multiplier=? WHERE uuid=?";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, uuid.toString());
            statement.setDouble(2, data.getBalance());
            statement.setDouble(3, data.getTokens());
            statement.setInt(4, data.getLevel());
            statement.setInt(5, data.getXp());
            statement.setInt(6, data.getXp_max());
            statement.setInt(7, data.getPrestige());
            statement.setDouble(8, data.getBlocks_broken());
            statement.setInt(9, data.getMultiplier());
            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (delete) dataMap.remove(uuid);
    }

    public static void deletePlayer(UUID uuid) {
        String sql = "DELETE FROM playerData WHERE uuid='" + uuid.toString() + "'";

        try {
            Connection connection = getConnection();
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkPlayer(UUID uuid) {
        String sql = "SELECT uuid FROM playerDATA WHERE uuid='" + uuid.toString() + "'";
        try {
            Connection connection = getConnection();
            ResultSet set = connection.createStatement().executeQuery(sql);
            if (!set.next()) {
                Bukkit.broadcastMessage("1");
                createPlayer(uuid);
                return;
            }
            Bukkit.broadcastMessage("2");
            grabPlayerData(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveAllPlayers(boolean delete) {
        String sql = "UPDATE playerData SET balance=?, tokens=?, level=?, xp=?, xp_max=?, prestige=?, blocks_broken=?, multiplier=? WHERE uuid=?";
        try {
            Connection connection = getConnection();

            for (Player player : Bukkit.getOnlinePlayers()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                PlayerData data = dataMap.get(player.getUniqueId());

                statement.setString(1, player.getUniqueId().toString());
                statement.setDouble(2, data.getBalance());
                statement.setDouble(3, data.getTokens());
                statement.setInt(4, data.getLevel());
                statement.setInt(5, data.getXp());
                statement.setInt(6, data.getXp_max());
                statement.setInt(7, data.getPrestige());
                statement.setDouble(8, data.getBlocks_broken());
                statement.setInt(9, data.getMultiplier());
                statement.executeUpdate();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (delete) dataMap.clear();
    }

    public static void checkAllPlayers() {
        String sql = "SELECT uuid FROM playerData WHERE uuid=?";
        try {
            Connection connection = getConnection();

            for (Player player : Bukkit.getOnlinePlayers()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, player.getUniqueId().toString());
                ResultSet set = statement.executeQuery();
                if (!set.next()) {
                    createPlayer(player.getUniqueId());
                    continue;
                }
                grabPlayerData(player.getUniqueId());
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

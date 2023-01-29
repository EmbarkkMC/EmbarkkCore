package me.outspending.embarkkcore.Pmines.Storage;

import me.outspending.embarkkcore.EmbarkkCore;
import me.outspending.embarkkcore.Pmines.Main.Pmine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PmineStorage {

    public static String STORAGE_FILE = EmbarkkCore.getPlugin().getDataFolder().getAbsolutePath() + "/pmines.db".replace("/", "//");

    static {

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
        String sql = "CREATE TABLE IF NOT EXISTS pmines (name TEXT, owner TEXT, mineLoc1 TEXT, mineLoc2 TEXT, level INT, members TEXT)";
        try {
            Connection connection = getConnection();
            connection.createStatement().execute(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

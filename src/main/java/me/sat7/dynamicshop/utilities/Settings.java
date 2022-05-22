package me.sat7.dynamicshop.utilities;
import me.sat7.dynamicshop.DynamicShop;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Settings {

    private final Plugin plugin;

    private String playerDataTable;

    // Connection settings for MySQL
    private int mySQLPort;
    private String mySQLHost;
    private String mySQLDatabase;
    private String mySQLUsername;
    private String mySQLPassword;
    private String mySQLParams;

    public Settings(Plugin plugin) {
        this.plugin = plugin;
    }

    // (Re-)Load the config file
    public void reload() {
        plugin.reloadConfig();
        reloadFromFile(plugin.getConfig());
    }

    public void reloadFromFile(FileConfiguration config) {
        try {
            this.playerDataTable = config.getString("data_storage_options.table_names.player_data", "dynamics");
           // this.mySQLHost = config.getString("data_storage_options.mysql_credentials.host", "148.251.135.133");
           // this.mySQLDatabase = config.getString("data_storage_options.mysql_credentials.database", "s1_proxy");
           // this.mySQLUsername = config.getString("data_storage_options.mysql_credentials.username", "u1_O0JPKXOic8");
           // this.mySQLPassword = config.getString("data_storage_options.mysql_credentials.password", "=BKGtQZTgHNT0rl@UcADoik8");

            this.mySQLHost = config.getString("data_storage_options.mysql_credentials.host", "136.243.41.180");
            this.mySQLDatabase = config.getString("data_storage_options.mysql_credentials.database", "s1_shops");
            this.mySQLUsername = config.getString("data_storage_options.mysql_credentials.username", "u1_qEyeV4k8Be");
            this.mySQLPassword = config.getString("data_storage_options.mysql_credentials.password", "aMiKjtgN.x^LUcmLU1v.IgsI");

            this.mySQLPort = config.getInt("data_storage_options.mysql_credentials.port", 3306);
            this.mySQLParams = config.getString("data_storage_options.mysql_credentials.params", "?autoReconnect=true&useSSL=false");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getPlayerDataTable() {
        return playerDataTable;
    }


    public int getMySQLPort() {
        return mySQLPort;
    }

    public String getMySQLHost() {
        return mySQLHost;
    }

    public String getMySQLDatabase() {
        return mySQLDatabase;
    }

    public String getMySQLUsername() {
        return mySQLUsername;
    }

    public String getMySQLPassword() {
        return mySQLPassword;
    }

    public String getMySQLParams() {
        return mySQLParams;
    }
}
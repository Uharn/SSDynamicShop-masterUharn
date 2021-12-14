package me.sat7.dynamicshop.utilities;

import me.sat7.dynamicshop.DynamicShop;
import me.sat7.dynamicshop.constants.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class MySQL extends Database {
    final static String[] SQL_SETUP_STATEMENTS = {
            "CREATE TABLE IF NOT EXISTS " + DynamicShop.getSettings().getPlayerDataTable() + " (" +
                    "`id` integer AUTO_INCREMENT," +
                    "`user_uuid` char(36) NOT NULL," +
                    "`itemname` text NOT NULL," +
                    "`qty_Sold` int NOT NULL," +
                            "`value_Sold` DOUBLE(30,2) NOT NULL," +
                            "`last_update` text NOT NULL," +

                    "PRIMARY KEY (`id`)" +
                    ");"
    };

    final String host = DynamicShop.getSettings().getMySQLHost();
    final Integer port = DynamicShop.getSettings().getMySQLPort();
    final String database = DynamicShop.getSettings().getMySQLDatabase();
    final String username = DynamicShop.getSettings().getMySQLUsername();
    final String password = DynamicShop.getSettings().getMySQLPassword();
    final String params = DynamicShop.getSettings().getMySQLParams();

    private Connection connection;

    public MySQL(DynamicShop instance) {
        super(instance);
    }


    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {

                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = (DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + params, username, password));

                } catch (SQLException ex) {
                    plugin.getLogger().log(Level.SEVERE, "An exception occurred initialising the mySQL database: ", ex);
                } catch (ClassNotFoundException ex) {
                    plugin.getLogger().log(Level.SEVERE, "The mySQL JBDC library is missing! Please download and place this in the /lib folder.");
                }
            }
        } catch (SQLException exception) {
            plugin.getLogger().log(Level.WARNING, "An error occurred checking the status of the SQL connection: ", exception);
        }
        return connection;
    }

    @Override
    public void load() {
        connection = getConnection();
        try(Statement statement = connection.createStatement()) {
            for (String tableCreationStatement : SQL_SETUP_STATEMENTS) {
                statement.execute(tableCreationStatement);
            }
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred creating tables: ", e);
        }
        initialize();
    }

    @Override
    public void backup() {
        plugin.getLogger().info("Remember to make backups!");
    }
}
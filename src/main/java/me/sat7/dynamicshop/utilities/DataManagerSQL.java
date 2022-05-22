package me.sat7.dynamicshop.utilities;
import me.sat7.dynamicshop.DynamicShop;
import me.sat7.dynamicshop.constants.Constants;
import me.sat7.dynamicshop.utilities.Database;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.*;
import java.time.Instant;
import java.util.*;


public class DataManagerSQL {



    //return the total number of item sold from UUID + ITEM
    public static Integer getTotalPlayerItemQty(UUID uuid, String itemname, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM dynamics WHERE user_uuid=? AND itemname=?;")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, itemname);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final int totalPlayerItemQty = resultSet.getInt("id");
                statement.close();
                return totalPlayerItemQty;
            }

        }
        return null;
    }
    //is the item table populated for that user?
    public static Integer getCountPlayerids(UUID uuid,  Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT count(id) AS ids FROM dynamics WHERE user_uuid=? ;")) {
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final int totalPlayerItemValue = resultSet.getInt("ids");
                statement.close();
                return totalPlayerItemValue;
            }

        }
        return null;
    }


    //return the total value of item sold from UUID + ITEM
    public static Integer getTotalPlayerItemValue(UUID uuid, String itemname, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM dynamics WHERE user_uuid=? AND itemname=?;")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, itemname);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final int totalPlayerItemValue = resultSet.getInt("value_Sold");
                statement.close();
                return totalPlayerItemValue;
            }

        }
        return null;
    }
    //return the top 10 item to sell for a player
    public static Map<String,Double> getTotalPlayerTopItemValue(UUID uuid, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM dynamics WHERE user_uuid=? Order by value_Sold DESC limit 10;")) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            final Map<String,Double> totalPlayerTopItemValue = new HashMap<>();
            while (resultSet.next()) {

                totalPlayerTopItemValue.put(resultSet.getString("itemname"),resultSet.getDouble("value_Sold"));

            }
            statement.close();
            return totalPlayerTopItemValue;
        }
    }

    //return the top 10 item to sell for a player
    public static Map<String,Double> getTotalPlayerBottomItemValue(UUID uuid, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM dynamics WHERE user_uuid=? Order by value_Sold ASC limit 10;")) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            final Map<String,Double> totalPlayerBottomItemValue = new HashMap<>();
            while (resultSet.next()) {

                totalPlayerBottomItemValue.put(resultSet.getString("itemname"),resultSet.getDouble("value_Sold"));

            }
            statement.close();
            return totalPlayerBottomItemValue;

        }
    }


    //Insert player/item into the database
    public static void addPlayeritem(UUID playerUUID, String itemname, Integer qty_Sold, Double value_Sold, String last_update, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO dynamics (user_uuid,itemname,qty_Sold,value_Sold,last_update) VALUES(?,?,?,?,?);")) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, itemname);
            statement.setInt(3, 0 - qty_Sold);
            statement.setDouble(4, value_Sold);
            statement.setString(5, last_update);
            statement.executeUpdate();
        }
    }

    //Update player/item to increment value and qty into the database
    public static void setPlayerItemQtyValue(UUID playerUUID, String itemname, Integer qty_Sold, Double value_Sold, String last_update, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE dynamics SET qty_Sold=qty_Sold + ? , value_Sold=value_Sold + ? , last_update = ?  WHERE user_uuid = ? AND itemname = ?;")) {
            statement.setInt(1, 0 - qty_Sold);
            statement.setDouble(2, value_Sold);
            statement.setString(3, last_update);
            statement.setString(4, playerUUID.toString());
            statement.setString(5, itemname);
            statement.executeUpdate();
        }


    }

    //Calculate the karma for a player UUID
    public static double getPlayerKarma(UUID playerUUID, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select VAR_SAMP(value_Sold)/avg(value_sold)/2000 AS karma FROM dynamics WHERE user_uuid = ?;")) {
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final double playerKarma = resultSet.getDouble("karma");
                statement.close();
                return playerKarma;
            }
        }
        return 0;
    }
}
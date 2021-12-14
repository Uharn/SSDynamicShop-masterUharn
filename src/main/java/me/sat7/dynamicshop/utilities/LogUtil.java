package me.sat7.dynamicshop.utilities;

import me.sat7.dynamicshop.utilities.DataManagerSQL;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import me.sat7.dynamicshop.DynamicShop;
import me.sat7.dynamicshop.constants.Constants;
import me.sat7.dynamicshop.files.CustomConfig;
import org.bukkit.entity.Player;
import me.sat7.dynamicshop.utilities.LuckPermsUtils;


import static me.sat7.dynamicshop.utilities.DataManagerSQL.*;


public final class LogUtil {
    public static CustomConfig ccLog;
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x < upper;
    }
    private LogUtil() {

    }

    public static void setupLogFile() {
        if(DynamicShop.plugin.getConfig().getBoolean("SaveLogs")) {
            SimpleDateFormat sdf = new SimpleDateFormat ( "MM-dd-yyyy-HH:mm:ss");
            String timeStr = sdf.format (System.currentTimeMillis());
            ccLog.setup("Log_"+timeStr,"Log");
            ccLog.get().options().copyDefaults(true);
            ccLog.save();
        }
    }

    // 거래 로그 기록
    public static void addLog(String shopName, String itemName, int amount, double value, String curr, String player) {
        if(DynamicShop.plugin.getConfig().getBoolean("SaveLogs")) {
            if(ShopUtil.ccShop.get().contains(shopName+".Options.log") && ShopUtil.ccShop.get().getBoolean(shopName+".Options.log")) {
                SimpleDateFormat sdf = new SimpleDateFormat ( "MM-dd-yyyy-HH:mm:ss");
                String timeStr = sdf.format (System.currentTimeMillis());

                int i = 0;
                if(ccLog.get().contains(shopName)) i = ccLog.get().getConfigurationSection(shopName).getKeys(false).size();

                ccLog.get().set(shopName+"."+i,timeStr +","+itemName + "," + amount + "," + Math.round(value*100)/100.0 + "," + curr+","+player);
                ccLog.save();
            }

            if(ccLog.get().getKeys(true).size() > 500) {
                setupLogFile();
            }
        }
    }
    //LOGSQL
    public static void addsqlLog(String shopName, String itemName, int amount, double value, String curr, Player player) throws SQLException {
        if(DynamicShop.plugin.getConfig().getBoolean("SaveLogs")) {
            Connection connection = DynamicShop.getConnection();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss");
            String timeStr = sdf.format(System.currentTimeMillis());
            UUID playerUUID = player.getUniqueId();
            //ce couple user/item existe?
            if(getTotalPlayerItemQty(playerUUID,itemName, connection) == null) {
            //si oui :créer une ligne avec les données

            //check si la base est alimentée pour cet utilisateur
             if(getCountPlayerids(playerUUID, connection) <70) {
                 //si oui populer sa table
                 addPlayeritem(playerUUID, "COBBLESTONE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "COAL", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "IRON_INGOT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "GOLD_INGOT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "LAPIS_LAZULI", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "REDSTONE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "EMERALD", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "QUARTZ", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "AMETHYST_CLUSTER", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "COPPER_INGOT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "DEEPSLATE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "CALCITE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "AMETHYST_SHARD", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "ROTTEN_FLESH", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SPIDER_EYE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "RABBIT_HIDE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "LEATHER", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "FEATHER", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "STRING", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BONE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "ENDER_PEARL", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "MAGMA_CREAM", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SLIME_BALL", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BLAZE_ROD", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "GUNPOWDER", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "ARROW", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "PRISMARINE_SHARD", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "PRISMARINE_CRYSTALS", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "TURTLE_EGG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SCUTE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "GHAST_TEAR", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "PHANTOM_MEMBRANE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SHULKER_SHELL", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "OAK_LOG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SPRUCE_LOG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "JUNGLE_LOG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "ACACIA_LOG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BIRCH_LOG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "DARK_OAK_LOG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "CRIMSON_STEM", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "WARPED_STEM", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "MOSS_BLOCK", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "CARROT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "POTATO", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "APPLE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "NETHER_WART", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "WHEAT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SUGAR_CANE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "WHEAT_SEEDS", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BAMBOO", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "CACTUS", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "MELON_SLICE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BEETROOT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "PUMPKIN", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "COCOA_BEANS", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "EGG", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SWEET_BERRIES", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "HONEYCOMB", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "HONEY_BOTTLE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "CHORUS_FRUIT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BEETROOT_SEEDS", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BROWN_MUSHROOM", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "RED_MUSHROOM", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "GLOW_BERRIES", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "WHITE_WOOL", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "BEEF", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "CHICKEN", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "RABBIT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "MUTTON", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "PORKCHOP", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "RABBIT_FOOT", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "COD", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SALMON", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "PUFFERFISH", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "TROPICAL_FISH", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "INK_SAC", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "SEA_PICKLE", 0, 0.0, timeStr, connection);
                 addPlayeritem(playerUUID, "GLOW_INK_SAC", 0, 0.0, timeStr, connection);
                 //et quand même mettre à jour
                 setPlayerItemQtyValue(playerUUID, itemName, amount, value, timeStr, connection);


             }

            } else {
                //si non : incrémenter la quantité et la valeur
                setPlayerItemQtyValue(playerUUID, itemName, amount, value, timeStr, connection);
            }
            //calcul du karma
            Double playerKarma = getPlayerKarma(playerUUID, connection);
            Double playerKarma4D = Math.round(playerKarma*10000.0)/10000.0;


            int playerKarmaInt = (int) Math.round(playerKarma);
            //enregistre la note de karma dans les permissions
           // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" meta set karma "+ playerKarma4D );
            LuckPermsUtils.setKarma(player,4);
            LuckPermsUtils.setKarmaNote(player,playerKarma4D);

            if (isBetween(playerKarmaInt,0,1)) {
                //Appliquer a l'utilisateur la perm palier 4
                LuckPermsUtils.setKarma(player,4);
                //bonus +10%
                player.sendMessage(DynamicShop.dsPrefix + "§3§lShops §7»§aVotre note de diversité est de: §6" + playerKarma4D + "\n" + " §3§lBONUS de diversité +10% activé sur tout les tarifs du shop");

               // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" meta set dynshopspalier 4" );
            } else if (isBetween(playerKarmaInt,1,2)) {
                //attention bientôt plus de bonus
                LuckPermsUtils.setKarma(player,4);
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" meta set dynshopspalier 4" );
                player.sendMessage(DynamicShop.dsPrefix + "§3§lShops §7»§aVotre note de diversité est de: §6" + playerKarma4D + "\n" + " §3§lBONUS de diversité +10% activé§c qui disparaîtra si vous dépassez 2");
                //Appliquer a l'utilisateur la perm palier 4


            } else if (isBetween(playerKarmaInt,2,8)) {
                //Appliquer a l'utilisateur la perm palier 3
                LuckPermsUtils.setKarma(player,3);
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" meta set dynshopspalier 3" );
                //Tarif Normaux
                player.sendMessage(DynamicShop.dsPrefix + "§3§lShops §7»§aVotre note de diversité est de: §6" + playerKarma4D + "\n" + " §3Vous bénéficiez des tarifs normaux");

            } else if (isBetween(playerKarmaInt,8,10)) {
                //Appliquer a l'utilisateur la perm palier 3
                LuckPermsUtils.setKarma(player,3);
                //attention bientôt tarifs cassés a -50% si vous ne vous diversifiez pas
                player.sendMessage(DynamicShop.dsPrefix + "§3§lShops §7»§aVotre note de diversité est de: §6" + playerKarma4D + "\n" + " §3>Tarifs normaux, mais si vous dépassez §3§l60 §3vous vendrez §c-50%§3 moins cher: §3§lIL FAUT VOUS DIVERSIFIER");

                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" meta set dynshopspalier 3" );

            } else if (isBetween(playerKarmaInt,10,40)) {
                //Appliquer a l'utilisateur la perm palier 2
                LuckPermsUtils.setKarma(player,2);
                //Tarifs cassés
                player.sendMessage(DynamicShop.dsPrefix + "§3§lShops §7»§aVotre note de diversité est de: §6" + playerKarma4D + "\n" + " §3Prix cassés à §c-50%§3: §3§lIL FAUT VOUS DIVERSIFIER");

                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" meta set dynshopspalier 2" );
            } else if (isBetween(playerKarmaInt,40,100000)) {
                //Appliquer a l'utilisateur la perm palier 1
                LuckPermsUtils.setKarma(player,1);
                //Tarifs cassés
                player.sendMessage(DynamicShop.dsPrefix + "§3§lShops §7»§aVotre note de diversité est de: §6" + playerKarma4D + "\n" + " §3Prix cassés à §c-90%§3: §3§lVous étiez prévenu");

                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" meta set dynshopspalier 1" );
            }


        }
    }



    public static void cullLogs() {
        File[] logs = new File(DynamicShop.plugin.getDataFolder() + "/Log").listFiles();
        if (logs.length > 0) {
            int deleted = 0;
            for(File l : logs) {
                int ageMins = (int) (System.currentTimeMillis() - l.lastModified())/60000;
                if(ageMins > DynamicShop.plugin.getConfig().getInt("LogCullAgeMinutes")) {
                    l.delete();
                    deleted++;
                }
            }
            if( deleted > 0 ) {
                DynamicShop.console.sendMessage(Constants.DYNAMIC_SHOP_PREFIX +
                        " Found and deleted " + deleted + " log file(s) older than " + DynamicShop.plugin.getConfig().getInt("LogCullAgeMinutes") +
                        " minutes. Checking again in " + DynamicShop.plugin.getConfig().getInt("LogCullTimeMinutes") + " minutes.");
            }
        }
    }
}

package me.sat7.dynamicshop.utilities;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.platform.PlayerAdapter;
import net.luckperms.api.track.Track;

public class LuckPermsUtils {

    private LuckPerms luckperms;

    public LuckPermsUtils(LuckPerms luckperms) {
        this.luckperms = luckperms;
    }
    public static void setKarma(Player player, int level) {
        LuckPerms luckperms = LuckPermsProvider.get();
        // obtain a User instance (by any means! see above for other ways)
        User user = luckperms.getPlayerAdapter(Player.class).getUser(player);

        // create a new MetaNode holding the level value
        // of course, this can have context/expiry/etc too!
        MetaNode node = MetaNode.builder("dynshopspalier", Integer.toString(level)).build();

        // clear any existing meta nodes with the same key - we want to override
        user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("dynshopspalier")));
        // add the new node
        user.data().add(node);

        // save!
        luckperms.getUserManager().saveUser(user);
    }
    public static void setKarmaNote(Player player, double playerKarma4D) {
        LuckPerms luckperms = LuckPermsProvider.get();
        luckperms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

        // create a new MetaNode holding the level value
        // of course, this can have context/expiry/etc too!
        MetaNode node = MetaNode.builder("karma", Double.toString(playerKarma4D)).build();

        // clear any existing meta nodes with the same key - we want to override
        user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("karma")));
        // add the new node
        user.data().add(node);

        // save!
        luckperms.getUserManager().saveUser(user);
        });
    }


    public void setGroup(final Player player, final Group group) {
        luckperms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

            // Remove all other inherited groups the user had before.
            user.data().clear(NodeType.INHERITANCE::matches);

            // Create a node to add to the player.
            Node node = InheritanceNode.builder(group).build();

            // Add the node to the user.
            user.data().add(node);
        });
    }

    /**
     *
     * @param uniqueId
     * @return a User from CompletableFuture
     * <p><i>should be called from an async task
     */
    public User getUser(UUID uniqueId) {
        UserManager userManager = luckperms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uniqueId);
        return userFuture.join();
    }

    /**
     *
     * @param uniqueId
     * @param groupName
     * <p><i>should be called from an async task
     */
    public void setGroup(UUID uniqueId, String groupName) {
        UserManager userManager = luckperms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uniqueId);
        User user = userFuture.join();
        user.setPrimaryGroup(groupName);
    }

    /**
     *
     * @param uniqueId
     * @param groupName
     * @param save
     * <p><i>should be called from an async task
     */
    public void setGroup(UUID uniqueId, String groupName, boolean save) {
        UserManager userManager = luckperms.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(uniqueId);
        User user = userFuture.join();
        user.setPrimaryGroup(groupName);
        if(save) {
            userManager.saveUser(user);
        }
    }

    public Collection<Group> getGroups(final Player player) {
        PlayerAdapter<Player> playerAdapter = luckperms.getPlayerAdapter(Player.class);

        // Get a LuckPerms user for the player.
        User user = playerAdapter.getUser(player);
        // Get all of the groups they inherit from on the current server.
        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(player));
        return groups;
    }

    public String getGroup(final Player player) {
        PlayerAdapter<Player> playerAdapter = luckperms.getPlayerAdapter(Player.class);
        User user = playerAdapter.getUser(player);
        return user.getPrimaryGroup();
    }

    public String getGroup(final UUID uuid) {
        luckperms.getUserManager().getUser(uuid);
        return null;
    }

    public boolean trackExists(final String track) {
        boolean isLoaded = luckperms.getTrackManager().isLoaded(track);
        return isLoaded;
    }

    public void setGroupOnTrack(final Player player, final Group group, final String track) {
        User user = luckperms.getUserManager().getUser(player.getUniqueId());
        Track tracc = luckperms.getTrackManager().getTrack(track);
        tracc.promote(user, group.getQueryOptions().context());
        luckperms.getTrackManager().saveTrack(tracc);
        luckperms.getUserManager().saveUser(user);
    }

}

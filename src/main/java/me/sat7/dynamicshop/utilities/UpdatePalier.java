package me.sat7.dynamicshop.utilities;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.NodeMap;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.MetaNode;
import net.luckperms.api.platform.PlayerAdapter;
import net.luckperms.api.track.Track;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdatePalier {

    public static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    public static void setPalier(Player player, int value) {
        // blah blah stuff
        singleThreadExecutor.execute(() -> setMyThing(player, value));
    }

    private synchronized static void setMyThing(Player player, int value) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);

        NodeMap data = user.data();
        data.clear(NodeMatcher.metaKey("dynshopspalier"));
        data.add(MetaNode.builder("dynshopspalier", String.valueOf(value)).build());

        luckPerms.getUserManager().saveUser(user).join();
    }
}


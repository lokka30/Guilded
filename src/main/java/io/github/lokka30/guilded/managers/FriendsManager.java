package io.github.lokka30.guilded.managers;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendsManager {

    private GuildedBukkit instance;

    public FriendsManager(final GuildedBukkit instance) {
        this.instance = instance;
    }

    public void addFriend(final String uuid1, final String uuid2) {
        addFriendIndividual(uuid1, uuid2);
        addFriendIndividual(uuid2, uuid1);
    }

    public void removeFriend(final String uuid1, final String uuid2) {
        removeFriendIndividual(uuid1, uuid2);
        removeFriendIndividual(uuid2, uuid1);
    }

    public void cancelFriendRequest(final String uuid1, final String uuid2) {
        //uuid1 = the player that sent the request and wanted to cancel it
        //uuid2 = the player who was sent a friend request

        final String path = "players." + uuid2 + ".friend-requests";
        List<String> friendRequests = instance.getData().get(path, new ArrayList<>());
        friendRequests.remove(uuid1);
        instance.getData().set(path, friendRequests);
    }

    public void acceptFriendRequest(final String uuid1, final String uuid2) {
        //uuid1 = the player that sent the friend request
        //uuid2 = the player that accepted the friend request

        //Add the friends together
        addFriend(uuid1, uuid2);

        //Remove the friend request as it is no longer required
        cancelFriendRequest(uuid1, uuid2);
    }

    public void startIgnoringFriendRequests(final String uuid1, final String uuid2) {
        //uuid1 = the player that should be ignored
        //uuid2 = the player that started ignoring the other player

        final String path = "players." + uuid2 + ".friend-requests-ignored";
        List<String> ignoredRequesters = instance.getData().get(path, new ArrayList<>());
        ignoredRequesters.add(uuid1);
        instance.getData().set(path, ignoredRequesters);
    }

    public void stopIgnoringFriendRequests(final String uuid1, final String uuid2) {
        //uuid1 = the player that had their friend requests previously ignored
        //uuid2 = the player that no longer wants to ignore the other player's friend requests

        final String path = "players." + uuid2 + ".friend-requests-ignored";
        List<String> ignoredRequesters = instance.getData().get(path, new ArrayList<>());
        ignoredRequesters.remove(uuid1);
        instance.getData().set(path, ignoredRequesters);
    }

    public List<String> getFriendsList(final String uuid) {
        return instance.getData().get("players." + uuid + ".friends", new ArrayList<>());
    }

    public void setFriendAddedTime(final String uuid1, final String uuid2) {
        //uuid2 just added uuid1 as a friend.
        Date date = new Date(System.currentTimeMillis());
        instance.getData().set("players." + uuid2 + ".friends-history." + uuid1, date);
    }

    public String getFriendAddedTime(final String uuid1, final String uuid2) {
        //uuid2 wants to know when uuid1 was added to their friends list.
        return instance.getData().get("players." + uuid2 + ".friends-history." + uuid1, "Unknown");
    }

    public String getFriendServer(final ProxiedPlayer player) {
        //Access by Bungee only!
        return player.getServer().getInfo().getName();
    }

    public String getFriendWorld(final Player player) {
        //Access by Bukkit only!
        return player.getWorld().getName();
    }

    public void setCanReceiveRequests(final String uuid, final boolean canReceiveRequests) {
        instance.getData().set("players." + uuid + ".options.canReceiveRequests", canReceiveRequests);
    }

    public boolean canReceiveRequests(final String uuid) {
        return instance.getData().get("players." + uuid + ".options.canReceiveRequests", true);
    }

    public void setHidden(final String uuid, final boolean state) {
        instance.getData().set("players." + uuid + ".options.isHidden", state);
    }

    public boolean isHidden(final String uuid) {
        return instance.getData().get("players." + uuid + ".options.isHidden", false);
    }

    private void addFriendIndividual(final String uuid1, final String uuid2) {
        final String path = "players." + uuid1 + ".friends";
        List<String> friendsList = instance.getData().get(path, new ArrayList<>()); //Get their current friends list
        friendsList.add(uuid2); //Put the new friend in their list
        instance.getData().set(path, friendsList); //Set the new friends list
        setFriendAddedTime(uuid1, uuid2);
    }

    private void removeFriendIndividual(final String uuid1, final String uuid2) {
        final String path = "players." + uuid1 + ".friends";
        List<String> friendsList = instance.getData().get(path, new ArrayList<>()); //Get their current friends list
        friendsList.remove(uuid2); //Remove the friend from their friends list
        instance.getData().set(path, friendsList); //Set the new friends list
    }

}

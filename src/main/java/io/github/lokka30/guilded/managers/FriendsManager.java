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

    public List<String> getRequesters(final String playerUUID) {
        return instance.getData().get("players." + playerUUID + ".friend-requests", new ArrayList<>());
    }

    public void cancelFriendRequest(final String requesterUUID, final String cancellerUUID) {
        //requesterUUID = the player that sent the request and wanted to cancel it
        //cancellerUUID = the player who was sent a friend request

        final String path = "players." + cancellerUUID + ".friend-requests";
        List<String> friendRequests = getRequesters(cancellerUUID);
        friendRequests.remove(requesterUUID);
        instance.getData().set(path, friendRequests);
    }

    public void acceptFriendRequest(final String requesterUUID, final String accepterUUID) {
        //Add the friends together
        addFriend(requesterUUID, accepterUUID);

        //Remove the friend request as it is no longer required
        cancelFriendRequest(requesterUUID, accepterUUID);
    }

    public void startIgnoringFriendRequests(final String targetUUID, final String playerUUID) {
        //PlayerUUID should ignore TargetUUID
        //targetUUID = the player that should be ignored
        //playerUUID = the player that started ignoring the other player

        final String path = "players." + playerUUID + ".friend-requests-ignored";
        List<String> ignoredRequesters = instance.getData().get(path, new ArrayList<>());
        ignoredRequesters.add(targetUUID);
        instance.getData().set(path, ignoredRequesters);
    }

    public void stopIgnoringFriendRequests(final String targetUUID, final String playerUUID) {
        //playerUUID ignored targetUUID before.
        //targetUUID = the player that had their friend requests previously ignored
        //playerUUID = the player that no longer wants to ignore the other player's friend requests

        final String path = "players." + playerUUID + ".friend-requests-ignored";
        List<String> ignoredRequesters = instance.getData().get(path, new ArrayList<>());
        ignoredRequesters.remove(targetUUID);
        instance.getData().set(path, ignoredRequesters);
    }

    public List<String> getIgnoredList(final String playerUUID) {
        return instance.getData().get("players." + playerUUID + ".friend-requests-ignored", new ArrayList<>());
    }

    public boolean isIgnoringFriendRequests(final String targetUUID, final String requesterUUID) {
        //Is targetUUID ignoring requesterUUID's friend requests?
        return instance.getData().get("players." + targetUUID + ".friend-requests-ignored", new ArrayList<>()).contains(requesterUUID);
    }

    public List<String> getFriendsList(final String playerUUID) {
        return instance.getData().get("players." + playerUUID + ".friends", new ArrayList<>());
    }

    public void setFriendAddedTime(final String targetUUID, final String playerUUID) {
        //playerUUID just added targetUUID as a friend.
        Date date = new Date(System.currentTimeMillis());
        instance.getData().set("players." + playerUUID + ".friends-history." + targetUUID, date);
    }

    public String getFriendAddedTime(final String targetUUID, final String playerUUID) {
        //playerUUID wants to know when targetUUID was added to their friends list.
        return instance.getData().get("players." + playerUUID + ".friends-history." + targetUUID, "Unknown");
    }

    public String getFriendServer(final ProxiedPlayer player) {
        //Access by Bungee only!
        return player.getServer().getInfo().getName();
    }

    public String getFriendWorld(final Player player) {
        //Access by Bukkit only!
        return player.getWorld().getName();
    }

    public void setCanReceiveRequests(final String playerUUID, final boolean canReceiveRequests) {
        instance.getData().set("players." + playerUUID + ".options.canReceiveRequests", canReceiveRequests);
    }

    public boolean canReceiveRequests(final String playerUUID) {
        return instance.getData().get("players." + playerUUID + ".options.canReceiveRequests", true);
    }

    public void setHidden(final String playerUUID, final boolean state) {
        instance.getData().set("players." + playerUUID + ".options.isHidden", state);
    }

    public boolean isHidden(final String playerUUID) {
        return instance.getData().get("players." + playerUUID + ".options.isHidden", false);
    }

    private void addFriendIndividual(final String playerUUID, final String targetUUID) {
        final String path = "players." + playerUUID + ".friends";
        List<String> friendsList = instance.getData().get(path, new ArrayList<>()); //Get their current friends list
        friendsList.add(targetUUID); //Put the new friend in their list
        instance.getData().set(path, friendsList); //Set the new friends list
        setFriendAddedTime(playerUUID, targetUUID);
    }

    private void removeFriendIndividual(final String playerUUID, final String targetUUID) {
        final String path = "players." + playerUUID + ".friends";
        List<String> friendsList = instance.getData().get(path, new ArrayList<>()); //Get their current friends list
        friendsList.remove(targetUUID); //Remove the friend from their friends list
        instance.getData().set(path, friendsList); //Set the new friends list
    }

    public boolean hasAlreadySentFriendRequest(final String requesterUUID, final String targetUUID) {
        //has requesterUUID already sent a friend request to targetUUID?
        return instance.getData().get("players." + targetUUID + ".friend-requests", new ArrayList<>()).contains(requesterUUID);
    }

    public void sendFriendRequest(final String requesterUUID, final String targetUUID) {
        //requesterUUID sends a friend request to targetUUID
        final String path = "players." + targetUUID + ".friend-requests";
        List<String> friendRequestList = instance.getData().get(path, new ArrayList<>());
        friendRequestList.add(requesterUUID);
        instance.getData().set(path, friendRequestList);
    }

}

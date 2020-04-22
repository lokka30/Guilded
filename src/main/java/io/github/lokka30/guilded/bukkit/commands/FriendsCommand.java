package io.github.lokka30.guilded.bukkit.commands;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FriendsCommand implements CommandExecutor {

    /*
    /friends add <player>
        - Send a friend request
    /friends cancel <player>
        - Cancel a friend request
    /friends remove <player>
        - Remove a player from your friends list
    /friends accept <player>
        - Accept a friend request
    /friends deny <player>
        - Deny a friend request
    /friends ignore <player>
        - Ignore a player from their friend requests
    /friends list
        - List all of your friends and what server/world they are in
    /friends about <player>
        - Tells you information about the friendship with that player.
        - 'You became friends with Notch on 22 April 2020'
        - 'Notch is on the server Survival'
    /friends options requests <on/off>
    /friends options hidden <on/off>
        - Doesn't tell your friend if you are online or offline
            - If you aren't hidden, such info is shown on join/quit and also /friends about
        - Doesn't show your current server in /friends about
     */
    private GuildedBukkit instance;

    public FriendsCommand(final GuildedBukkit instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        //TODO
        sender.sendMessage(instance.getUtils().prefix("This command is not implemented yet. :("));
        return true;
    }
}

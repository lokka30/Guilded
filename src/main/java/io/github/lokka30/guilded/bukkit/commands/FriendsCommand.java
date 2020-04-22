package io.github.lokka30.guilded.bukkit.commands;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FriendsCommand implements CommandExecutor {

    /*
    /friends add <player>
    /friends remove <player>
    /friends accept <player>
    /friends deny <player>
    /friends ignore <player>
    /friends list
    /friends about <player>
        - Tells you information about the friendship with that player.
        - 'You became friends with Notch on 22 April 2020'
        - 'Notch is on the server Survival'
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

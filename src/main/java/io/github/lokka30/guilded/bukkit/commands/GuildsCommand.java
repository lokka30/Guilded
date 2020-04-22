package io.github.lokka30.guilded.bukkit.commands;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GuildsCommand implements CommandExecutor {

    /*
    Commands
    /guilds create <name>
        - Create a guild with the specified name
    /guilds chat [message]
        - Toggles guild chat or sends a single message to the party chat
    /guilds invite <player>
        - Invite a player to your current guild if you are the guild leader
    /guilds remove <player>
        - Kick a player from your current guild if you are the guild leader
    /guilds leave
        - Leave your current guild
    /guilds list
        - List the members of your current guild
    /guilds toggle chat
        - Turn on/off if you can see guild chat
    /guilds toggle invites
        - Turn on/off if you can get invited to someone's guild
    /guilds disband
        - Dissolve your guild and kick all of its members
    /guilds setLeader <player>
        - Replace yourself as the leader of a guild to a guild member
     */

    private GuildedBukkit instance;

    public GuildsCommand(final GuildedBukkit instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        //TODO
        sender.sendMessage(instance.getUtils().prefix("This command is not implemented yet. :("));
        return true;
    }
}

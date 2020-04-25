package io.github.lokka30.guilded.bukkit.commands;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BukkitPartiesCommand implements CommandExecutor {

    /*
    Commands
    /parties chat [message]
        - Toggles party chat or sends a single message to the party chat
    /party invite <player>
        - Invite a player to your current party if you are the party leader
    /party remove <player>
        - Kick a player from your current party if you are the party leader
    /party leave
        - Leave your current party
    /party list
        - List the members of your current party
    /party toggle chat
        - Turn on/off if you can see party chat
    /party toggle teleports
        - Turn on/off if you get teleported to your party leader's server
    /party toggle invites
        - Turn on/off if you can get invited to someone's party
    /party disband
        - Dissolve your party and kick all of its members
    /party setLeader <player>
        - Make a party member the leader of the party
     */

    private GuildedBukkit instance;

    public BukkitPartiesCommand(final GuildedBukkit instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        //TODO
        sender.sendMessage(instance.getUtils().prefix("This command is not implemented yet. :("));
        return true;
    }
}

package io.github.lokka30.guilded.bukkit.commands;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class BukkitGuildedCommand implements CommandExecutor {

    private GuildedBukkit instance;

    public BukkitGuildedCommand(final GuildedBukkit instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, @NotNull final String label, @NotNull final String[] args) {
        sender.sendMessage(" ");
        sender.sendMessage(instance.getUtils().colorize("&7This server is running &aGuilded v" + instance.getDescription().getVersion() + " (Bukkit)&7."));
        sender.sendMessage(instance.getUtils().colorize("&7Author: &alokka30 &8-- &7Available on &aSpigotMC.org&7."));
        sender.sendMessage(" ");
        sender.sendMessage(instance.getUtils().colorize("&7Available commands: &a/friends&7, &a/guilds&7, &a/parties&7, &a/guilded"));
        sender.sendMessage(" ");
        return true;
    }
}

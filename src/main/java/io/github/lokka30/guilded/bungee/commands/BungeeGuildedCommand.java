package io.github.lokka30.guilded.bungee.commands;

import io.github.lokka30.guilded.bungee.GuildedBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BungeeGuildedCommand extends Command {

    private GuildedBungee instance;

    public BungeeGuildedCommand(GuildedBungee instance) {
        super("guilded");
        this.instance = instance;
    }

    public void execute(final CommandSender sender, final String[] args) {
        sender.sendMessage(" ");
        sender.sendMessage(instance.getUtils().colorize("&7This server is running &aGuilded v" + instance.getDescription().getVersion() + " (Bukkit)&7."));
        sender.sendMessage(instance.getUtils().colorize("&7Author: &alokka30 &8-- &7Plugin available on &aSpigotMC.org&7."));
        sender.sendMessage(" ");
        sender.sendMessage(instance.getUtils().colorize("&7Available commands: &a/friends&7, &a/guilds&7, &a/parties&7, &a/guilded"));
        sender.sendMessage(" ");
    }
}

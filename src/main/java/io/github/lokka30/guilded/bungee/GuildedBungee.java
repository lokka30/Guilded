package io.github.lokka30.guilded.bungee;

import io.github.lokka30.guilded.bungee.commands.BungeeGuildedCommand;
import io.github.lokka30.guilded.bungee.utils.Utils;
import net.md_5.bungee.api.plugin.Plugin;

public class GuildedBungee extends Plugin {

    private Utils utils;

    //TODO Metrics id is 7268
    @Override
    public void onEnable() {
        utils = new Utils(this);
        registerCommands();
        getLogger().severe("Guilded (Bungee) is in alpha stage.");
    }

    public void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new BungeeGuildedCommand(this));
    }

    public Utils getUtils() {
        return utils;
    }
}

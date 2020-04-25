package io.github.lokka30.guilded.bungee.utils;

import io.github.lokka30.guilded.bungee.GuildedBungee;
import net.md_5.bungee.api.ChatColor;

public class Utils {

    private GuildedBungee instance;

    public Utils(final GuildedBungee instance) {
        this.instance = instance;
    }

    public String colorize(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}

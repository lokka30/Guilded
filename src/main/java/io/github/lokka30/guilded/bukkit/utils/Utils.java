package io.github.lokka30.guilded.bukkit.utils;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import io.github.lokka30.guilded.commons.LogLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Utils {

    private GuildedBukkit instance;

    public Utils(final GuildedBukkit instance) {
        this.instance = instance;
    }

    //This will colorize a message. It will not replace the prefix.
    public String colorize(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    //This will colorize a message but also include a prefix with it.
    //ALERT: Do NOT use this method unless you are 100% sure that it will only be ran after the messages file is loaded properly.
    public String prefix(final String msg) {
        final String prefix = instance.getMessages().get("prefix", "&a&lGuilded: &7");
        return colorize(msg.replaceFirst("%prefix%", prefix));
    }

    //This will use the prefix method but also retrieve a message and have its default. shorter way to get messages and prefix them
    public String prefixFromMessages(final String path, final String def) {
        return prefix(instance.getMessages().get(path, def));
    }

    //This will log a message to the console with such log level
    public void log(final LogLevel logLevel, String msg) {
        final Logger logger = Bukkit.getLogger();
        msg = colorize("&aGuilded: &7" + msg);
        switch (logLevel) {
            case INFO:
                logger.info(msg);
                break;
            case WARNING:
                logger.warning(msg);
                break;
            case SEVERE:
                logger.severe(msg);
                break;
            default:
                throw new IllegalStateException("Tried to log to console, but LogLevel '" + logLevel.toString() + "' is unmanaged. Intended message: '" + msg + "'.");
        }
    }

    //This is the list of supported server versions. Users who are not running one of these base versions will be notified that they will not receive support.
    public List<String> getSupportedServerVersions() {
        return Arrays.asList("1.15", "1.14", "1.13", "1.12", "1.11", "1.10", "1.9", "1.8", "1.7");
    }

    //This is the latest settings file version.
    //If the current settings file version installed by the user does not match this, then such is reported to the console.
    public int getLatestSettingsFileVersion() {
        return 2;
    }

    //This is the latest messages file version.
    //If the current messages file version installed by the user does not match this, then such is reported to the console.
    public int getLatestMessagesFileVersion() {
        return 4;
    }

    //This is the latest data file version.
    //If the current data file version installed by the user does not match this, then such is reported to the console.
    public int getLatestDataFileVersion() {
        return 1;
    }
}

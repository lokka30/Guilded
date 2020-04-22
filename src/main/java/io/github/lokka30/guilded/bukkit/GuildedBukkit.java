package io.github.lokka30.guilded.bukkit;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.FlatFile;
import io.github.lokka30.guilded.bukkit.commands.FriendsCommand;
import io.github.lokka30.guilded.bukkit.commands.GuildedCommand;
import io.github.lokka30.guilded.bukkit.commands.GuildsCommand;
import io.github.lokka30.guilded.bukkit.commands.PartiesCommand;
import io.github.lokka30.guilded.bukkit.listeners.JoinListener;
import io.github.lokka30.guilded.bukkit.listeners.QuitListener;
import io.github.lokka30.guilded.bukkit.managers.FriendsManager;
import io.github.lokka30.guilded.bukkit.managers.GuildsManager;
import io.github.lokka30.guilded.bukkit.managers.PartiesManager;
import io.github.lokka30.guilded.bukkit.utils.Utils;
import io.github.lokka30.guilded.commons.LogLevel;
import io.github.lokka30.guilded.commons.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GuildedBukkit extends JavaPlugin {

    private FriendsManager friendsManager;
    private GuildsManager guildsManager;
    private PartiesManager partiesManager;
    private Utils utils;

    private FlatFile settings;
    private FlatFile messages;
    private FlatFile data;

    private PluginManager pluginManager;

    @Override
    public void onLoad() {
        friendsManager = new FriendsManager(this);
        guildsManager = new GuildsManager(this);
        partiesManager = new PartiesManager(this);
        utils = new Utils(this);
        pluginManager = getServer().getPluginManager();
    }

    @Override
    public void onEnable() {
        utils.log(LogLevel.INFO, "&8+---+ &fEnable Started &8+---+");
        final long startTime = System.currentTimeMillis();

        utils.log(LogLevel.INFO, "&8(&2 1/5 &8)&7 Checking compatibility...");
        checkCompatibility();

        utils.log(LogLevel.INFO, "&8(&2 2/5 &8)&7 Loading files...");
        loadFiles();

        utils.log(LogLevel.INFO, "&8(&2 3/5 &8)&7 Registering events...");
        registerEvents();

        utils.log(LogLevel.INFO, "&8(&2 4/5 &8)&7 Registering commands...");
        registerCommands();

        utils.log(LogLevel.INFO, "&8(&2 5/5 &8)&7 Starting metrics...");
        new Metrics(this, 7267);

        final long totalTime = System.currentTimeMillis() - startTime;
        utils.log(LogLevel.INFO, "&8+---+ &fEnable Finished (took " + totalTime + "ms) &8+---+");

        utils.log(LogLevel.INFO, "&8(&2Update Checker&8)&7 Checking for updates...");
        checkForUpdates();
    }

    private void checkCompatibility() {
        //Check version.
        boolean isSupportedVersion = false;
        for (String supportedVersion : utils.getSupportedServerVersions()) {
            if (getServer().getVersion().contains(supportedVersion)) {
                isSupportedVersion = true;
            }
        }
        if (!isSupportedVersion) {
            utils.log(LogLevel.INFO, "You are running an &cunsupported server version&7! Support will not be given if you run outside of the supported specifications.");
        }
    }

    private void loadFiles() {
        final String path = "plugins/Guilded/";
        settings = LightningBuilder
                .fromFile(new File(path + "settings"))
                .addInputStreamFromResource("settings.yml")
                .createYaml();
        messages = LightningBuilder
                .fromFile(new File(path + "messages"))
                .addInputStreamFromResource("messages.yml")
                .createYaml();
        data = LightningBuilder
                .fromFile(new File(path + "data"))
                .addInputStreamFromResource("data.json")
                .createJson();

        //Check if they exist
        final File settingsFile = new File(path + "settings.yml");
        final File messagesFile = new File(path + "messages.yml");
        final File dataFile = new File(path + "data.json");

        if (!(settingsFile.exists() && !settingsFile.isDirectory())) {
            utils.log(LogLevel.INFO, "File &asettings.yml&7 doesn't exist. Creating it now.");
            saveResource("settings.yml", false);
        }

        if (!(messagesFile.exists() && !messagesFile.isDirectory())) {
            utils.log(LogLevel.INFO, "File &amessages.yml&7 doesn't exist. Creating it now.");
            saveResource("messages.yml", false);
        }

        if (!(dataFile.exists() && !dataFile.isDirectory())) {
            utils.log(LogLevel.INFO, "File &adata.json&7 doesn't exist. Creating it now.");
            saveResource("data.json", false);
        }

        //Check their versions
        if (settings.get("file-version", 0) != utils.getLatestSettingsFileVersion()) {
            utils.log(LogLevel.SEVERE, "File &asettings.yml&7 is out of date! Errors are likely to occur! Reset it or merge the old values to the new file.");
        }

        if (messages.get("file-version", 0) != utils.getLatestMessagesFileVersion()) {
            utils.log(LogLevel.SEVERE, "File &amessages.yml&7 is out of date! Errors are likely to occur! Reset it or merge the old values to the new file.");
        }

        if (data.get("file-version", 0) != utils.getLatestDataFileVersion()) {
            utils.log(LogLevel.SEVERE, "File &adata.yml&7 is out of date! Errors are likely to occur! Reset it or merge the old values to the new file.");
        }
    }

    private void registerEvents() {
        pluginManager.registerEvents(new JoinListener(this), this);
        pluginManager.registerEvents(new QuitListener(this), this);
    }

    private void registerCommands() {
        getCommand("friends").setExecutor(new FriendsCommand(this));
        getCommand("guilded").setExecutor(new GuildedCommand(this));
        getCommand("guilds").setExecutor(new GuildsCommand(this));
        getCommand("parties").setExecutor(new PartiesCommand(this));
    }

    private void checkForUpdates() {
        //TODO Change Resource ID and remove "update-checker-bypass"
        if (settings.get("update-checker-unimplemented-bypass", false)) {
            utils.log(LogLevel.INFO, "The update checker isn't fully implemented yet, sorry!");
            return;
        }
        if (settings.get("use-update-checker", true)) {
            utils.log(LogLevel.INFO, "&8(&2Update Checker&8) &7Checking for updates...");
            new UpdateChecker(this, 12345).getVersion(version -> {
                if (getDescription().getVersion().equalsIgnoreCase(version)) {
                    utils.log(LogLevel.INFO, "&8(&2Update Checker&8) &7Thanks, you're running the latest version.");
                } else {
                    utils.log(LogLevel.WARNING, "&8(&2Update Checker&8) &7There's a new update available, '&a" + version + "&7'. You're running '&a" + getDescription().getVersion() + "&7'.");
                }
            });
        }
    }

    @Override
    public void onDisable() {
        utils.log(LogLevel.INFO, "&8+---+ &fDisable Started &8+---+");
        final long startTime = System.currentTimeMillis();

        //If any disable methods need to be added, put them here

        final long totalTime = System.currentTimeMillis() - startTime;
        utils.log(LogLevel.INFO, "&8+---+ &fDisable Finished (took " + totalTime + "ms) &8+---+");
    }

    public FlatFile getSettings() {
        return settings;
    }

    public FlatFile getMessages() {
        return messages;
    }

    public FlatFile getData() {
        return data;
    }

    public Utils getUtils() {
        return utils;
    }

    public FriendsManager getFriendsManager() {
        return friendsManager;
    }

    public GuildsManager getGuildsManager() {
        return guildsManager;
    }

    public PartiesManager getPartiesManager() {
        return partiesManager;
    }
}

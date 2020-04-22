package io.github.lokka30.guilded.bukkit.listeners;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private GuildedBukkit instance;

    public JoinListener(final GuildedBukkit instance) {
        this.instance = instance;
    }

    public void onJoin(final PlayerJoinEvent event) {
        //TODO
    }
}

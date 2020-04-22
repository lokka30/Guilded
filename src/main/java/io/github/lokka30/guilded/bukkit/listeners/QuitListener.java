package io.github.lokka30.guilded.bukkit.listeners;

import io.github.lokka30.guilded.bukkit.GuildedBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private GuildedBukkit instance;

    public QuitListener(final GuildedBukkit instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        //TODO
    }
}

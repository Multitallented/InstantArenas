package org.redcastlemedia.multitallented.instantarenas.util;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;

/**
 *
 * @author Multitallented
 */
public abstract class BukkitEventListener implements Listener {
    public BukkitEventListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, InstantArenas.getInstance());
    }
}

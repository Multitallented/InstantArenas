/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parts.redcastlemedia.multitallented.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.parts.redcastlemedia.multitallented.controllers.Controller;

/**
 *
 * @author Multitallented
 */
public abstract class BukkitEventListener implements Listener {
    public BukkitEventListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, (JavaPlugin) Controller.getInstance("plugin"));
    }
}

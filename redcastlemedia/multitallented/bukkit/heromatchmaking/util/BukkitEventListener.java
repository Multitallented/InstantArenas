package redcastlemedia.multitallented.bukkit.heromatchmaking.util;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;

/**
 *
 * @author Multitallented
 */
public abstract class BukkitEventListener implements Listener {
    public BukkitEventListener(HeroMatchMaking plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
}

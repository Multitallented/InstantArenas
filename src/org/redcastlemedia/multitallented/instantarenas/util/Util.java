package org.redcastlemedia.multitallented.instantarenas.util;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.manager.ConfigManager;

/**
 *
 * @author Multitallented
 */
public class Util {
    public static String joinString(String[] parts, int start, int end) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
        builder.append(parts[i]).append(" ");
        }
        return builder.toString().trim();
    }
    
    public static boolean checkValidWorld() {
        Logger logger = Logger.getLogger("Minecraft");
        String world = ConfigManager.getInstance().getWorld();
        String message;
        if (Bukkit.getWorld(world) == null) {
            message = InstantArenas.NAME + " Invalid world " + world + ". Disabling HMM";
            logger.severe(message);
            Bukkit.getPluginManager().disablePlugin(InstantArenas.getInstance());
            return false;
        }
        return true;
    }
}

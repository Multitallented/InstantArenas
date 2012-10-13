package redcastlemedia.multitallented.bukkit.heromatchmaking.util;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;

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
    
    public static boolean checkValidWorld(HeroMatchMaking controller) {
        Logger logger = Logger.getLogger("Minecraft");
        String world = controller.getConfigManager().getWorld();
        String message = "";
        if (Bukkit.getWorld(world) == null) {
            message = HeroMatchMaking.NAME + " Invalid world " + world + ". Disabling HMM";
            logger.severe(message);
            Bukkit.getPluginManager().disablePlugin(controller);
            return false;
        }
        return true;
    }
}

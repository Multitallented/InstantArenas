package redcastlemedia.multitallented.bukkit.heromatchmaking.manager;

import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Multitallented
 */
public class ConfigManager {
    private final String world;
    private final String normalizedClassName;
    private final double winnings;
    
    public ConfigManager(FileConfiguration config) {
        world =  config.getString("world", "matchmaking");
        normalizedClassName = config.getString("normalized-class-name", "vanilla");
        winnings = config.getDouble("winnings", 0.0);
    }
    
    public double getWinnings() {
        return winnings;
    }
    
    public String getWorld() {
        return world;
    }
    
    public String getNormalizedClassName() {
        return this.normalizedClassName;
    }
}

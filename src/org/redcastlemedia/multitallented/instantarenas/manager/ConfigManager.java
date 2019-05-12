package org.redcastlemedia.multitallented.instantarenas.manager;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.builder.ConfigBuilder;

/**
 *
 * @author Multitallented
 */
public class ConfigManager {
    private final String world;
    private final String normalizedClassName;
    private final double winnings;

    private static ConfigManager instance = null;

    public static ConfigManager getInstance() {
        if (instance == null) {
            new ConfigManager();
        }
        return instance;
    }
    
    public ConfigManager() {
        instance = this;
        FileConfiguration config = loadConfig();
        world =  config.getString("world", "matchmaking");
        normalizedClassName = config.getString("normalized-class-name", "vanilla");
        winnings = config.getDouble("winnings", 0.0);
    }

    private FileConfiguration loadConfig() {
        File configFile = new File(InstantArenas.getInstance().getDataFolder(), "config.yml");
        FileConfiguration config;
        if (!configFile.exists()) {
            config = ConfigBuilder.createNewConfigFile(configFile);
        } else {
            try {
                config = new YamlConfiguration();
                config.load(configFile);
            } catch (Exception e) {
                System.out.println(InstantArenas.NAME + " failed to load existing config.yml");
                return null;
            }
        }
        return config;
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

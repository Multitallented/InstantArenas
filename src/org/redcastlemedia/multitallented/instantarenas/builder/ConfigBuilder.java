package org.redcastlemedia.multitallented.instantarenas.builder;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;

/**
 *
 * @author Multitallented
 */
public class ConfigBuilder {
    public static FileConfiguration createNewConfigFile(File file) {
        File dataFolder = InstantArenas.getInstance().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        try {
            file.createNewFile();
        } catch (IOException ioe) {
            System.out.println("[" + InstantArenas.NAME + "] failed to create new config.yml");
        }
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
            config.set("world", "matchmaking");
            config.set("normalized-class-name", "vanilla");
            config.save(file);
        } catch (Exception e) {
            System.out.println("[" + InstantArenas.NAME + "] failed to save new config.yml");
        }
        return config;
    }
}

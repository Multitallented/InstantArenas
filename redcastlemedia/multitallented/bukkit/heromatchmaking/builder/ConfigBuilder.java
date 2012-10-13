package redcastlemedia.multitallented.bukkit.heromatchmaking.builder;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Multitallented
 */
public class ConfigBuilder {
    public static FileConfiguration createNewConfigFile(File file) {
        try {
            file.mkdirs();
            file.createNewFile();
        } catch (IOException ioe) {
            System.out.println("[HeroMatchMaking] failed to create new config.yml");
        }
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
            config.set("world", "matchmaking");
            config.set("normalized-class-name", "vanilla");
            config.save(file);
        } catch (Exception e) {
            System.out.println("[HeroMatchMaking] failed to save new config.yml");
        }
        return config;
    }
}

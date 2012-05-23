/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.builders;

import java.io.File;
import org.parts.redcastlemedia.multitallented.models.YMLProxy;

/**
 *
 * @author Multitallented
 */
public class ProxyBuilder {
    public static YMLProxy buildDefaultConfigProxy() {
        YMLProxy p = new YMLProxy() {

            @Override
            public void createFile(File file) {
                try {
                    if (!file.exists()) {
                        return;
                    }
                    
                    load(file);
                    set("heroes.use-default-class", "false");
                    set("heroes.default-class", "Citizen");
                    set("world", "matchmaking");
                    set("game-types.rts", true);
                    set("game-types.spleef", true);
                    set("team-types.1v1", true);
                    save(file);
                    //TODO write default config values here
                } catch (Exception e) {
                    System.out.println("[HeroMatchMaking] failed to create default config.yml");
                }
            }
            
        };
        return p;
    }
}

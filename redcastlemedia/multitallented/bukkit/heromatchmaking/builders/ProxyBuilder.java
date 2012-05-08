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
                    set("default-class", "Citizen");
                    set("world", "matchmaking");
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

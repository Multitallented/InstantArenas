package redcastlemedia.multitallented.bukkit.heromatchmaking;

import com.herocraftonline.heroes.Heroes;
import java.io.File;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import redcastlemedia.multitallented.bukkit.heromatchmaking.builder.ConfigBuilder;
import redcastlemedia.multitallented.bukkit.heromatchmaking.listener.HMMListener;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.ArenaManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.ConfigManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.MatchManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.UserManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.util.BukkitEventListener;

/**
 *
 * @author Multitallented
 */
public class InitOrder {
    private final HeroMatchMaking controller;
    private final PluginManager pm;
    public InitOrder(HeroMatchMaking controller) {
        Logger logger = Logger.getLogger("Minecraft");
        this.controller = controller;
        this.pm = Bukkit.getServer().getPluginManager();
        
        FileConfiguration config = loadConfig();
        if (config == null) {
            Bukkit.getPluginManager().disablePlugin(controller);
            return;
        }
        initManagers(config);
        registerDependencies();
        setupListener();
    }
    
    private FileConfiguration loadConfig() {
        File configFile = new File(controller.getDataFolder(), "config.yml");
        if (!controller.getDataFolder().exists()) {
            controller.getDataFolder().mkdir();
        }
        FileConfiguration config;
        if (!configFile.exists()) {
            config = ConfigBuilder.createNewConfigFile(configFile);
        } else {
            try {
                config = new YamlConfiguration();
                config.load(configFile);
            } catch (Exception e) {
                System.out.println(HeroMatchMaking.NAME + " failed to load existing config.yml");
                return null;
            }
        }
        return config;
    }
    
    private void registerDependencies() {
        registerHeroes();
        registerVault();
    }
    
    private void initManagers(FileConfiguration config) {
        controller.setConfigManager(new ConfigManager(config));
        controller.setUserManager(new UserManager(controller));
        controller.setMatchManager(new MatchManager(controller));
        controller.setArenaManager(new ArenaManager(controller));
    }
    
    private void setupListener() {
        HMMListener listener = new HMMListener(controller);
        controller.setListener(listener);
        Bukkit.getPluginManager().registerEvents(listener, controller);
    }
    
    private void registerHeroes() {
        if (pm.isPluginEnabled("Heroes")) {
            HeroMatchMaking.heroes = (Heroes) pm.getPlugin("Heroes");
            System.out.println(HeroMatchMaking.NAME + " Hooked into Heroes");
        } else {
                new BukkitEventListener(controller) {
                @EventHandler
                public void onPluginEnable(PluginEnableEvent event) {
                    if (event.getPlugin().getDescription().getName().equals("Heroes")) {
                        HeroMatchMaking.heroes = (Heroes) event.getPlugin();
                        System.out.println(HeroMatchMaking.NAME + " Hooked into Heroes");
                    }
                }
            };
        }
    }
    
    private void registerVault() {
        if (pm.isPluginEnabled("Vault")) {
            //Setup econ and perm providers
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            Economy econ = null;
            if (rsp != null) {
                econ = rsp.getProvider();
                if (econ != null) {
                    System.out.println(HeroMatchMaking.NAME + " Hooked into " + econ.getName());
                    HeroMatchMaking.econ = econ;
                }
            }

            Permission perm = null;
            RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
            if (permissionProvider != null) {
                perm = permissionProvider.getProvider();
                if (perm != null) {
                    System.out.println(HeroMatchMaking.NAME + " Hooked into " + perm.getName());
                    HeroMatchMaking.perm = perm;
                }
            }
        } else {
            new BukkitEventListener(controller) {
                @EventHandler
                public void onPluginEnable(PluginEnableEvent event) {
                    if (event.getPlugin().getDescription().getName().equals("Vault")) {
                        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
                        Economy econ = null;
                        if (rsp != null) {
                            econ = rsp.getProvider();
                            if (econ != null) {
                                System.out.println(HeroMatchMaking.NAME + " Hooked into " + econ.getName());
                                HeroMatchMaking.econ = econ;
                            }
                        }

                        Permission perm = null;
                        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
                        if (permissionProvider != null) {
                            perm = permissionProvider.getProvider();
                            if (perm != null) {
                                System.out.println(HeroMatchMaking.NAME + " Hooked into " + perm.getName());
                                HeroMatchMaking.perm = perm;
                            }
                        }
                    }
                }
            };
        }
    }
}

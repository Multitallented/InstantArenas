package org.redcastlemedia.multitallented.instantarenas.order;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.listener.PlayerListener;
import org.redcastlemedia.multitallented.instantarenas.manager.ArenaManager;
import org.redcastlemedia.multitallented.instantarenas.manager.ConfigManager;
import org.redcastlemedia.multitallented.instantarenas.manager.MatchManager;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.util.BukkitEventListener;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

/**
 *
 * @author Multitallented
 */
public class InitOrder {
    public InitOrder() {
        initManagers();
        registerDependencies();
        setupListener();
    }
    
    private void registerDependencies() {
        registerVault();
    }

    private void initManagers() {
        new ConfigManager();
        new UserManager();
        new MatchManager();
        new ArenaManager();
    }
    
    private void setupListener() {
        new PlayerListener();
    }
    
    private void registerVault() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            //Setup econ and perm providers
            setVaultHooks();
        } else {
            new BukkitEventListener() {
                @EventHandler
                public void onPluginEnable(PluginEnableEvent event) {
                    if (event.getPlugin().getDescription().getName().equals("Vault")) {
                        setVaultHooks();
                    }
                }
            };
        }
    }

    private void setVaultHooks() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        Economy econ = null;
        if (rsp != null) {
            econ = rsp.getProvider();
            if (econ != null) {
                System.out.println(InstantArenas.NAME + " Hooked into " + econ.getName());
                InstantArenas.econ = econ;
            }
        }

        Permission perm = null;
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            perm = permissionProvider.getProvider();
            if (perm != null) {
                System.out.println(InstantArenas.NAME + " Hooked into " + perm.getName());
                InstantArenas.perm = perm;
            }
        }
    }
}

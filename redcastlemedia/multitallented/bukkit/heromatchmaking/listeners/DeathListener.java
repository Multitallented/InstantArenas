/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redcastlemedia.multitallented.bukkit.heromatchmaking.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.parts.redcastlemedia.multitallented.controllers.Controller;
import redcastlemedia.multitallented.bukkit.heromatchmaking.managers.ArenaManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.managers.PlayerManager;

/**
 *
 * @author Multitallented
 */
public class DeathListener implements Listener {
    
    /*
     * Listens for 
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        PlayerManager pm = (PlayerManager) Controller.getInstance("playermanager");
        if (!pm.hasFightingPlayer(p)) {
            return;
        }
        ArenaManager am = (ArenaManager) Controller.getInstance("arenamanager");
        am.checkEndMatch(p);
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        PlayerManager pm = (PlayerManager) Controller.getInstance("playermanager");
        if (!pm.hasRespawningPlayer(event.getPlayer())) {
            return;
        }
        ArenaManager am = (ArenaManager) Controller.getInstance("arenamanager");
        am.playerRespawned(event);
    }
    
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        PlayerManager pm = (PlayerManager) Controller.getInstance("playermanager");
        ArenaManager am = (ArenaManager) Controller.getInstance("arenamanager");
        if (pm.hasRespawningPlayer(p)) {
            
        } else if (pm.containsQueuingPlayer(p)) {
            pm.removeQueuingPlayer(p);
        } else if (pm.hasFightingPlayer(p)) {
            am.checkEndMatch(p);
        }
    }
    
    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        PlayerManager pm = (PlayerManager) Controller.getInstance("playermanager");
        String label = event.getMessage().replace("/", "").split(" ")[0];
        if (pm.hasFightingPlayer(event.getPlayer()) && !label.equalsIgnoreCase("suicide")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.GRAY + "[HeroMatchMaking] You may only use /suicide while in the arena");
        }
    }

}

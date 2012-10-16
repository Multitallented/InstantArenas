package redcastlemedia.multitallented.bukkit.heromatchmaking.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import redcastlemedia.multitallented.bukkit.heromatchmaking.*;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class HMMListener implements Listener {
    private final HeroMatchMaking controller;
    public HMMListener(HeroMatchMaking controller) {
        this.controller = controller;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new JoinOrder(controller, event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        new QuitOrder(controller, event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        new DeathOrder(controller, event.getEntity());
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        new RespawnOrder(controller, event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equals("/suicide")) {
            return;
        }
        User u = controller.getUserManager().getUser(event.getPlayer().getName());
        if (u.getMatch() != null) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + HeroMatchMaking.NAME + " You can't use commands while in a match!");
        }
    }
    
    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        final User user = controller.getUserManager().getUser(event.getPlayer().getName());
        if (user.getMatch() != null) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + HeroMatchMaking.NAME + " You can't teleport while in a match!");
        }
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        
    }
}

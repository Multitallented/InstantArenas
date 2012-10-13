package redcastlemedia.multitallented.bukkit.heromatchmaking.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import redcastlemedia.multitallented.bukkit.heromatchmaking.*;

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
}

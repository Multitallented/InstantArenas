package org.redcastlemedia.multitallented.instantarenas.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.redcastlemedia.multitallented.instantarenas.order.DamageOrder;
import org.redcastlemedia.multitallented.instantarenas.order.DeathOrder;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.order.JoinOrder;
import org.redcastlemedia.multitallented.instantarenas.order.PreferenceOrder;
import org.redcastlemedia.multitallented.instantarenas.order.QuitOrder;
import org.redcastlemedia.multitallented.instantarenas.order.RespawnOrder;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new JoinOrder(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        new QuitOrder(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        new DeathOrder(event.getEntity());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        new RespawnOrder(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equals("/suicide")) {
            return;
        }
        User u = UserManager.getInstance().getUser(event.getPlayer().getUniqueId());
        if (u.getMatch() != null) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + InstantArenas.NAME + " You can't use commands while in a match!");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        final User user = UserManager.getInstance().getUser(event.getPlayer().getUniqueId());
        if (user.getMatch() != null) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + InstantArenas.NAME + " You can't teleport while in a match!");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        new DamageOrder(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        User u = UserManager.getInstance().getUser(event.getPlayer().getUniqueId());
        if (u.getMatch() != null && !u.getMatch().getArena().canBuild()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        User u = UserManager.getInstance().getUser(event.getPlayer().getUniqueId());
        if (u.getMatch() != null && !u.getMatch().getArena().canBuild()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (event.getClickedBlock() == null ||
                !event.getClickedBlock().getType().equals(Material.LEVER) ||
                !event.getClickedBlock().getRelative(BlockFace.UP).getType().equals(Material.OAK_WALL_SIGN)){
            return;
        }
        Sign sign = (Sign) event.getClickedBlock().getRelative(BlockFace.UP).getState();
        String type = sign.getLine(1).toUpperCase().replace(" ", "_");
        sign.setLine(2, sign.getLine(2).equals("[OFF]") ? "[ON]" : "[OFF]");
        sign.update(true);
        new PreferenceOrder(event.getPlayer(), type);
    }
}
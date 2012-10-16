package redcastlemedia.multitallented.bukkit.heromatchmaking.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.material.Lever;
import redcastlemedia.multitallented.bukkit.heromatchmaking.*;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;
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

    @EventHandler(ignoreCancelled = true)
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

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        final User user = controller.getUserManager().getUser(event.getPlayer().getName());
        if (user.getMatch() != null) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + HeroMatchMaking.NAME + " You can't teleport while in a match!");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        new DamageOrder(controller, event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        User u = controller.getUserManager().getUser(event.getPlayer().getName());
        if (u.getMatch() != null && !u.getMatch().getArena().canBuild()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        User u = controller.getUserManager().getUser(event.getPlayer().getName());
        if (u.getMatch() != null && !u.getMatch().getArena().canBuild()) {
            event.setCancelled(true);
        }
    }

    /*@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(!event.isCancelled() && event.getClickedBlock().equals(Material.LEVER)){
            Lever lever = (Lever)event.getClickedBlock();
            if(event.getClickedBlock().getRelative(0, 1, 0).equals(Material.SIGN)){
                Sign sign = (Sign)event.getClickedBlock().getRelative(0, 1, 0);
                String type = sign.getLine(1);
                GameType gType = null;
                TeamType tType = null;
                if(lever.isPowered()) return;*/
                /**
                 * I figured it would be better to do it this way instead of doing a
                 * switch(type) because this way is easier to add stuff to the list
                 * in case something needs to be added.
                 */
                /*if(
                        type == "RTS" ||
                        type == "SPLEEF" ||
                        type == "VANILLA" ||
                        type == "ANYTHING_GOES" ||
                        type == "PITFALL" ||
                        type == "CTF" ||
                        type == "DOMINATION" ||
                        type == "TDM" ||
                        type == "ASSAULT"){
                    gType = GameType.valueOf(type);
                } else if(
                        type == "ONE_V_ONE" ||
                        type == "TWO_V_TWO" ||
                        type == "THREE_FFA" ||
                        type == "FOUR_FFA" ||
                        type == "THREE_V_THREE" ||
                        type == "MOSH_PIT" ||
                        type == "BIG_TEAM" ||
                        type == "SOLO"){
                    tType = TeamType.valueOf(type);
                }
                /**
                 * Ofc I still have to study how these Types are passed and interpreted
                 * to the MatchManager. this is just the basic body for me to work on
                 * tomorrow, when I have more time to actually study the code.
                 */
            /*}
        }
    }*/
}

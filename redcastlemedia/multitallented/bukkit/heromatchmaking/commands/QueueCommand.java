/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.commands;

import java.util.HashSet;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.parts.redcastlemedia.multitallented.controllers.Controller;
import org.parts.redcastlemedia.multitallented.controllers.Order;
import redcastlemedia.multitallented.bukkit.heromatchmaking.builders.RTSArenaBuilder;
import redcastlemedia.multitallented.bukkit.heromatchmaking.managers.ArenaManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.managers.PlayerManager;

/**
 *
 * @author Multitallented
 */
public class QueueCommand implements HCommand {

    @Override
    public Order build(final CommandSender cs, Command command, String label, String[] args) {
        return new Order("command", true) {

            @Override
            public void exec() {
                if (!(cs instanceof Player)) {
                    cs.sendMessage(ChatColor.GRAY + "[HeroMatchMaking] You must be in game to queue.");
                    return;
                }
                PlayerManager pm = (PlayerManager) Controller.getInstance("playermanager");
                Player p = (Player) cs;
                
                //Perform a permission check to see if they can queue
                Permission perm = (Permission) Controller.getInstance("perm");
                if (perm != null && !perm.has(p, "heromatchmaking.queue")) {
                    p.sendMessage(ChatColor.GRAY + "[HeroMatchMaking] You don't have permission to queue");
                    return;
                }
                
                if (!pm.containsQueuingPlayer(p)) {
                    pm.addQueuingPlayer(p);
                }
                p.sendMessage(ChatColor.GOLD + "[HeroMatchMaking] Please wait, we're finding someone to fight you.");
                
                HashSet<Player> readyPlayers = pm.checkStartMatch();
                while (readyPlayers != null) {

                    //TODO change this to be dynamic later
                    RTSArenaBuilder arena = new RTSArenaBuilder();

                    ArenaManager am = (ArenaManager) Controller.getInstance("arenamanager");
                    am.scheduleMatch(readyPlayers, arena);
                    for (Player p0 : readyPlayers) {
                        pm.removeQueuingPlayer(p0);
                        pm.putPlayerLocation(p0, arena);
                    }
                    readyPlayers = pm.checkStartMatch();
                    
                }
                
            }
            
        };
    }
    
}

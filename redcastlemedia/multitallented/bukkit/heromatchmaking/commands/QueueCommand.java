/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.commands;

import java.util.HashSet;
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
                
                if (!pm.containsQueuingPlayer(p)) {
                    pm.addQueuingPlayer(p);
                }
                
                HashSet<Player> readyPlayers = null;
                do {
                    readyPlayers = pm.checkStartMatch();
                    
                    for (Player p0 : readyPlayers) {
                        pm.removeQueuingPlayer(p0);
                        //TODO player get settings from player manager
                    }

                    //TODO change this to be dynamic later
                    RTSArenaBuilder arena = new RTSArenaBuilder();

                    ArenaManager am = (ArenaManager) Controller.getInstance("arenamanager");
                    am.scheduleMatch(readyPlayers, arena);
                    for (Player p0 : readyPlayers) {
                        pm.putPlayerLocation(p0, arena);
                    }
                    
                } while (readyPlayers != null);
                
            }
            
        };
    }
    
}

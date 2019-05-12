package org.redcastlemedia.multitallented.instantarenas.order;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.model.GameType;
import org.redcastlemedia.multitallented.instantarenas.model.TeamType;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class PreferenceOrder {
    public PreferenceOrder(CommandSender sender, String value) {
        Player player;
        try {
            player = (Player) sender;
        } catch (Exception e) {
            sender.sendMessage(InstantArenas.NAME + " only players can use this command.");
            return;
        }
        User u = UserManager.getInstance().getUser(player.getUniqueId());
        try {
            GameType gType = GameType.valueOf(value);
            if (u.getGType().contains(gType)) {
                u.getGType().remove(gType);
                player.sendMessage(ChatColor.GOLD + InstantArenas.NAME + " Set " + gType.name() + " false.");
                return;
            } else {
                u.getGType().add(gType);
                player.sendMessage(ChatColor.GOLD + InstantArenas.NAME + " Set " + gType.name() + " true.");
                return;
            }
        } catch (IllegalArgumentException iae) {
            
        }
        try {
            TeamType tType = TeamType.valueOf(value);
            if (u.getTType().contains(tType)) {
                u.getTType().remove(tType);
                player.sendMessage(ChatColor.GOLD + InstantArenas.NAME + " Set " + tType.name() + " false.");
            } else {
                u.getTType().add(tType);
                player.sendMessage(ChatColor.GOLD + InstantArenas.NAME + " Set " + tType.name() + " true.");
            }
        } catch (IllegalArgumentException iae) {
        }
        
    }
}

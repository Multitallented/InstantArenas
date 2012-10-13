package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class PreferenceOrder {
    public PreferenceOrder(HeroMatchMaking controller, CommandSender sender, String value) {
        Player player;
        try {
            player = (Player) sender;
        } catch (Exception e) {
            sender.sendMessage(HeroMatchMaking.NAME + " only players can use this command.");
            return;
        }
        User u = controller.getUserManager().getUser(player.getName());
        try {
            GameType gType = GameType.valueOf(value);
            if (u.getGType().contains(gType)) {
                u.getGType().remove(gType);
                player.sendMessage(ChatColor.GOLD + HeroMatchMaking.NAME + " Set " + gType.name() + " false.");
                return;
            } else {
                u.getGType().add(gType);
                player.sendMessage(ChatColor.GOLD + HeroMatchMaking.NAME + " Set " + gType.name() + " true.");
                return;
            }
        } catch (IllegalArgumentException iae) {
            
        }
        try {
            TeamType tType = TeamType.valueOf(value);
            if (u.getTType().contains(tType)) {
                u.getTType().remove(tType);
                player.sendMessage(ChatColor.GOLD + HeroMatchMaking.NAME + " Set " + tType.name() + " false.");
            } else {
                u.getTType().add(tType);
                player.sendMessage(ChatColor.GOLD + HeroMatchMaking.NAME + " Set " + tType.name() + " true.");
            }
        } catch (IllegalArgumentException iae) {
        }
        
    }
}

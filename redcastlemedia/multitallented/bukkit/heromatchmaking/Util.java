/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Multitallented
 */
public class Util {
    public static void dispatchHelpMessage(CommandSender cs, int i) {
        if (i == 0) {
            cs.sendMessage(ChatColor.GRAY + "[HeroMatchMaking] Help page 1 of 2");
            //TODO finish this
        } else {
            cs.sendMessage(ChatColor.GRAY + "[HeroMatchMaking] Help page 2 of 2");
        }
    }
}

package org.redcastlemedia.multitallented.instantarenas;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.redcastlemedia.multitallented.instantarenas.model.GameType;
import org.redcastlemedia.multitallented.instantarenas.model.TeamType;
import org.redcastlemedia.multitallented.instantarenas.order.DisableOrder;
import org.redcastlemedia.multitallented.instantarenas.order.InitOrder;
import org.redcastlemedia.multitallented.instantarenas.order.PreferenceOrder;
import org.redcastlemedia.multitallented.instantarenas.order.QueueOrder;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

/**
 *
 * @author Multitallented
 */
public class InstantArenas extends JavaPlugin {
    public static Permission perm = null;
    public static Economy econ = null;
    public static final String NAME = "InstantArenas";

    private static InstantArenas instance = null;

    public static InstantArenas getInstance() {
        if (instance == null) {
            new InstantArenas();
        }
        return instance;
    }

    public InstantArenas() {
        instance = this;
    }
    
    
    @Override
    public void onEnable() {
        new InitOrder();
    }
    @Override
    public void onDisable() {
        new DisableOrder();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            String allArgs = "";
            boolean first = true;
            for (String s : args) {
                if (first) {
                    first = false;
                    allArgs += s;
                } else {
                    allArgs += " " + s;
                }
            }
            if (args.length == 0) {
                //TODO write help commands here
                return true;
            }
            if (args[0].equalsIgnoreCase("q") || args[0].equalsIgnoreCase("queue")) {
                new QueueOrder(sender);
            } else {
                String test = allArgs.toUpperCase().replaceAll(" ", "_");
                System.out.println(test);
                try {
                    if (TeamType.valueOf(test) != null) {
                        new PreferenceOrder(sender, test);
                    }
                } catch (IllegalArgumentException iae) {
                    
                }
                try {
                    if (GameType.valueOf(test) != null) {
                        new PreferenceOrder(sender, test);
                    }
                } catch (IllegalArgumentException iae) {
                    
                }
            }
            
        } catch (NullPointerException npe) {
            sender.sendMessage(ChatColor.RED + InstantArenas.NAME + " Unrecognized command " + label);
            return true;
        }
        return true;
    }
}

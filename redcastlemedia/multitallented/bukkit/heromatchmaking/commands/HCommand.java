/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.parts.redcastlemedia.multitallented.controllers.Order;

/**
 *
 * @author Multitallented
 */
public interface HCommand {
    public Order build(CommandSender cs, Command command, String label, String[] args);
}

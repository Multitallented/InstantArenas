/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parts.redcastlemedia.multitallented.controllers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Multitallented
 */
public interface CommandManager {
    
    public Order build(CommandSender cs, Command command, String label, String[] args);
}

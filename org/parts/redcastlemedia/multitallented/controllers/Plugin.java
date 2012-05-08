package org.parts.redcastlemedia.multitallented.controllers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    
    @Override
    public void onDisable() {
        System.out.println(this + " is now disabled!");
    }
    
    @Override
    public void onEnable() {
        new Controller(this);
        System.out.println(this + " is now enabled!");
    }
    
    @Override
    public boolean onCommand(CommandSender scs, Command scommand, String slabel, String sargs[]) {
        CommandManager cm = (CommandManager) Controller.getInstance("commandmanager");
        Controller.callOrder(cm.build(scs, scommand, slabel, sargs));
        return true;
    }
}


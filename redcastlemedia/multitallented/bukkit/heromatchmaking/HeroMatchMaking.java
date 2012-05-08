/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.parts.redcastlemedia.multitallented.controllers.CommandManager;
import org.parts.redcastlemedia.multitallented.controllers.Controller;
import redcastlemedia.multitallented.bukkit.heromatchmaking.managers.InitManager;

/**
 *
 * @author Multitallented
 */
public class HeroMatchMaking extends JavaPlugin {
    @Override
    public void onDisable() {
        System.out.println(this + " is now disabled!");
    }
    
    @Override
    public void onEnable() {
        Controller controller = new Controller(this);
        InitManager im = new InitManager();
        Controller.addInstance("initmanager", im);
        controller.init();
        System.out.println(this + " is now enabled!");
    }
    
    @Override
    public boolean onCommand(CommandSender scs, Command scommand, String slabel, String sargs[]) {
        CommandManager cm = (CommandManager) Controller.getInstance("commandmanager");
        Controller.callOrder(cm.build(scs, scommand, slabel, sargs));
        return true;
    }
}

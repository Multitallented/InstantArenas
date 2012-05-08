/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.managers;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.parts.redcastlemedia.multitallented.controllers.CommandManager;
import org.parts.redcastlemedia.multitallented.controllers.Order;
import redcastlemedia.multitallented.bukkit.heromatchmaking.Util;
import redcastlemedia.multitallented.bukkit.heromatchmaking.commands.*;

/**
 *
 * @author Multitallented
 */
public class HMMCommandManager implements CommandManager {
    private HashMap<String, HCommand> commands = new HashMap<String, HCommand>();
    
    public HMMCommandManager() {
        commands.put("queue", new QueueCommand()); 
        //TODO add all commands to the commands array
    }
    
    @Override
    public Order build(CommandSender cs, Command command, String label, String[] args) {
        Order o = null;
        if (args.length > 0 && commands.containsKey(args[0])) {
            HCommand com = commands.get(args[0]);
            o = com.build(cs, command, label, args);
        } else {
            //Unrecognized Command
            final CommandSender fcs = cs;
            o = new Order("command", true) {
                @Override
                public void exec() {
                    Util.dispatchHelpMessage(fcs, 0);
                }
            };
        }
        return o;
    }
}
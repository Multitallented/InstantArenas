package redcastlemedia.multitallented.bukkit.heromatchmaking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.Arena;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.Match;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;
import redcastlemedia.multitallented.bukkit.heromatchmaking.util.Util;

/**
 *
 * @author Multitallented
 */
public class QueueOrder {
    public QueueOrder(final HeroMatchMaking controller, CommandSender sender) {
        Player player;
        try {
            player = (Player) sender;
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + HeroMatchMaking.NAME + " Only in game players can use /hmm queue");
            return;
        }
        if (controller.getMatchManager().hasQueuingPlayer(player)) {
            controller.getMatchManager().removeQueuingPlayer(player);
            sender.sendMessage(ChatColor.RED + HeroMatchMaking.NAME + " You have left the queue.");
            return;
        }
        
        Util.checkValidWorld(controller);
        
        
        User user = controller.getUserManager().getUser(player.getName());
        if (user == null) {
            Logger logger = Logger.getLogger("Minecraft");
            String message = HeroMatchMaking.NAME + " failed to load user " + player.getName();
            logger.severe(message);
            return;
        }
        HashMap<String, ArrayList<User>> tempMap = controller.getMatchManager().getPotentialMatches(user);
        for (String s : tempMap.keySet()) {
            System.out.println(s);
        }
        if (tempMap == null || tempMap.isEmpty()) {
            user.getPlayer().sendMessage(HeroMatchMaking.NAME + " Please wait. Finding a match...");
            controller.getMatchManager().addQueuingPlayer(player);
            return;
        }
        Arena arena = null;
        Match match = null;
	while (!tempMap.isEmpty()) {
            match = controller.getMatchManager().selectRandomMatch(tempMap);
            tempMap.remove(match.getGType() + ":" + match.getTType());
            arena = controller.getArenaManager().getArena(match.getGType(), match.getTType());
            if (arena != null) {
                arena.setLocation(controller.getMatchManager().findSpace(arena.getSize()));
                break;
            }
        }
        if (arena == null) { //No arenas exist for any of the potential matches
            user.getPlayer().sendMessage(HeroMatchMaking.NAME + " Please wait. Finding a match...");
            controller.getMatchManager().addQueuingPlayer(player);
            return;
        }
        for (User u : match.getRawPlayers()) {
            Player p = u.getPlayer();
            controller.getMatchManager().removeQueuingPlayer(p);
            p.sendMessage(ChatColor.GOLD + HeroMatchMaking.NAME + " Starting " + match.getTType().name() + " " + match.getGType().name() + " in 5s...");
        }
        match.setArena(arena);
        final Match theMatch = match;
        Bukkit.getScheduler().scheduleSyncDelayedTask(controller, new Runnable() {
            
            @Override
            public void run() {
                boolean isCanceled = false;
                for (User u : theMatch.getRawPlayers()) {
                    Player p = u.getPlayer();
                    if (!p.isOnline() || p.isDead()) {
                        isCanceled = true;
                        break;
                    }
                }
                if (isCanceled) {
                    for (User u : theMatch.getRawPlayers()) {
                        Player p = u.getPlayer();
                        if (p.isOnline() && !p.isDead()) {
                            p.sendMessage(HeroMatchMaking.NAME + " someone disconnected or died. Looking for a new match...");
                            new QueueOrder(controller, p);
                        }
                    }
                    return;
                }
                for (User u : theMatch.getRawPlayers()) {
                    controller.getUserManager().saveUserPreviousState(u);
                    u.getPlayer().sendMessage(ChatColor.GOLD + HeroMatchMaking.NAME + " Starting match now!");
                }
                theMatch.getArena().build();
                controller.getMatchManager().clearDroppedItems(theMatch.getArena());
                controller.getArenaManager().preparePlayers(theMatch); //sets inventory, health, class, etc. and teleports them
                controller.getMatchManager().addMatch(theMatch);
            }
        }, 100L);
    }
}

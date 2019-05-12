package org.redcastlemedia.multitallented.instantarenas.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.manager.ArenaManager;
import org.redcastlemedia.multitallented.instantarenas.manager.MatchManager;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.model.Arena;
import org.redcastlemedia.multitallented.instantarenas.model.Match;
import org.redcastlemedia.multitallented.instantarenas.model.User;
import org.redcastlemedia.multitallented.instantarenas.util.Util;

/**
 *
 * @author Multitallented
 */
public class QueueOrder {
    public QueueOrder(CommandSender sender) {
        Player player;
        try {
            player = (Player) sender;
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + InstantArenas.NAME + " Only in game players can use /hmm queue");
            return;
        }
        if (MatchManager.getInstance().hasQueuingPlayer(player)) {
            MatchManager.getInstance().removeQueuingPlayer(player);
            sender.sendMessage(ChatColor.RED + InstantArenas.NAME + " You have left the queue.");
            return;
        }

        Util.checkValidWorld();

        User user = UserManager.getInstance().getUser(player.getUniqueId());
        if (user == null) {
            Logger logger = Logger.getLogger("Minecraft");
            String message = InstantArenas.NAME + " failed to load user " + player.getName();
            logger.severe(message);
            return;
        }
        HashMap<String, ArrayList<User>> tempMap = MatchManager.getInstance().getPotentialMatches(user);
        for (String s : tempMap.keySet()) {
            System.out.println(s);
        }
        if (tempMap == null || tempMap.isEmpty()) {
            Player p = Bukkit.getPlayer(user.getUuid());
            p.sendMessage(InstantArenas.NAME + " Please wait. Finding a match...");
            MatchManager.getInstance().addQueuingPlayer(player);
            return;
        }
        Arena arena = null;
        Match match = null;
        while (!tempMap.isEmpty()) {
            match = MatchManager.getInstance().selectRandomMatch(tempMap);
            tempMap.remove(match.getGType() + ":" + match.getTType());
            arena = ArenaManager.getInstance().getArena(match.getGType(), match.getTType());
            if (arena != null) {
                arena.setLocation(MatchManager.getInstance().findSpace(arena.getSize()));
                break;
            }
        }
        if (arena == null) { //No arenas exist for any of the potential matches
            Player p = Bukkit.getPlayer(user.getUuid());
            p.sendMessage(InstantArenas.NAME + " Please wait. Finding a match...");
            MatchManager.getInstance().addQueuingPlayer(player);
            return;
        }
        for (User u : match.getRawPlayers()) {
            Player p = Bukkit.getPlayer(u.getUuid());
            MatchManager.getInstance().removeQueuingPlayer(p);
            p.sendMessage(ChatColor.GOLD + InstantArenas.NAME + " Starting " + match.getTType().name() +
                    " " + match.getGType().name() + " in 5s...");
        }
        match.setArena(arena);
        final Match theMatch = match;
        MatchManager.getInstance().addMatch(theMatch);
        theMatch.getArena().build();
        MatchManager.getInstance().clearDroppedItems(theMatch.getArena());
        Bukkit.getScheduler().scheduleSyncDelayedTask(InstantArenas.getInstance(), new Runnable() {
            
            @Override
            public void run() {
                boolean isCanceled = false;
                for (User u : theMatch.getRawPlayers()) {
                    Player p = Bukkit.getPlayer(u.getUuid());
                    if (!p.isOnline() || p.isDead()) {
                        isCanceled = true;
                        break;
                    }
                }
                if (isCanceled) {
                    MatchManager.getInstance().removeMatch(theMatch);
                    for (User u : theMatch.getRawPlayers()) {
                        Player p = Bukkit.getPlayer(u.getUuid());
                        if (p.isOnline() && !p.isDead()) {
                            p.sendMessage(InstantArenas.NAME + " someone disconnected or died. Looking for a new match...");
                            new QueueOrder(p);
                        }
                    }
                    return;
                }
                for (User u : theMatch.getRawPlayers()) {
                    UserManager.getInstance().saveUserPreviousState(u);
                    Bukkit.getPlayer(u.getUuid()).sendMessage(ChatColor.GOLD + InstantArenas.NAME + " Starting match now!");
                }
                ArenaManager.getInstance().preparePlayers(theMatch); //sets inventory, health, class, etc. and teleports them
            }
        }, 100L);
    }
}

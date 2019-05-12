package org.redcastlemedia.multitallented.instantarenas.order;

import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.instantarenas.manager.MatchManager;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class QuitOrder {
    public QuitOrder(Player player) {
        UserManager um = UserManager.getInstance();
        User u = um.getUser(player.getUniqueId());
        um.restoreLoggingOutUser(u);
        um.removeUser(u);
        if (u.getMatch() == null) {
            MatchManager.getInstance().removeQueuingPlayer(player);
            return;
        }
        //also ends the match if it should be ended
        MatchManager.getInstance().checkEndMatch(u);
    }
}

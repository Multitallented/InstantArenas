package org.redcastlemedia.multitallented.instantarenas.order;

import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.instantarenas.manager.MatchManager;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class DeathOrder {
    public DeathOrder(Player p) {
        UserManager um = UserManager.getInstance();
        um.saveUserData(p.getUniqueId());
        User u = um.getUser(p.getUniqueId());
        if (u.getMatch() == null) {
            MatchManager.getInstance().removeQueuingPlayer(p);
            return;
        }
        um.restorePreviousUserState(u);
        //also ends the match if it should be ended
        MatchManager.getInstance().checkEndMatch(u);
    }
}

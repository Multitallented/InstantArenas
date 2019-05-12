package org.redcastlemedia.multitallented.instantarenas.order;

import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;

/**
 *
 * @author Multitallented
 */
public class JoinOrder {
    public JoinOrder(Player player) {
        UserManager um = UserManager.getInstance();
        um.restorePreviousUserState(um.getUser(player.getUniqueId()));
    }
}

package org.redcastlemedia.multitallented.instantarenas.order;

import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class RespawnOrder {
    public RespawnOrder(Player player) {
        User u = UserManager.getInstance().getUser(player.getUniqueId());
        UserManager.getInstance().restorePreviousUserState(u);
    }
}

package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.UserManager;

/**
 *
 * @author Multitallented
 */
public class JoinOrder {
    public JoinOrder(final HeroMatchMaking controller, Player player) {
        UserManager um = controller.getUserManager();
        um.restorePreviousUserState(um.getUser(player.getName()), "join");
    }
}

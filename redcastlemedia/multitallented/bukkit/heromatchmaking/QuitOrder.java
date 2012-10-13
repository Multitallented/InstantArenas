package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.UserManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class QuitOrder {
    public QuitOrder(HeroMatchMaking controller, Player player) {
        UserManager um = controller.getUserManager();
        um.saveUserData(player.getName());
        User u = um.getUser(player.getName());
        um.removeUser(u);
        if (u == null || !u.isInMatch()) {
            controller.getMatchManager().removeQueuingPlayer(player);
            return;
        }
        um.restorePreviousUserState(u);
        //also ends the match if it should be ended
        controller.getMatchManager().checkEndMatch(u);
    }
}

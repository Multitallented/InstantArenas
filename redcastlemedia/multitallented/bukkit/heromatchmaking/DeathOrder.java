package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.UserManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class DeathOrder {
    public DeathOrder(HeroMatchMaking controller, Player p) {
        UserManager um = controller.getUserManager();
        um.saveUserData(p.getName());
        User u = controller.getUserManager().getUser(p.getName());
        if (!u.isInMatch()) {
            controller.getMatchManager().removeQueuingPlayer(p);
            return;
        }
        um.restorePreviousUserState(u, "death");
        //also ends the match if it should be ended
        controller.getMatchManager().checkEndMatch(u);
    }
}

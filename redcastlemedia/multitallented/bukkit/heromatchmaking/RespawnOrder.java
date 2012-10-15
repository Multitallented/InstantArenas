package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class RespawnOrder {
    public RespawnOrder(HeroMatchMaking controller, Player player) {
        User u = controller.getUserManager().getUser(player.getName());
        controller.getUserManager().restorePreviousUserState(u, "respawn");
    }
}

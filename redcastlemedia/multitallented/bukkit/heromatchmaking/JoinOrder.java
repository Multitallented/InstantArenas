package redcastlemedia.multitallented.bukkit.heromatchmaking;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.UserManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class JoinOrder {
    public JoinOrder(final HeroMatchMaking controller, Player player) {
        UserManager um = controller.getUserManager();
        um.loadUserData(player.getName());
        final User u = um.getUser(player.getName());
        if (u != null && u.getPreviousLocation() != null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(controller, new Runnable() {
                @Override
                public void run() {
                    u.getPlayer().teleport(u.getPreviousLocation());
                    u.setPreviousLocation(null);
                    controller.getUserManager().saveUserData(u.getName());
                }
            });
        }
    }
}

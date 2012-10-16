package redcastlemedia.multitallented.bukkit.heromatchmaking;

import java.util.ArrayList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class DamageOrder {
    public DamageOrder(HeroMatchMaking controller, EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player) ||
                !(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent edby = (EntityDamageByEntityEvent) event;
        Entity e = edby.getDamager();
        if (edby.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            e = ((Projectile) e).getShooter();
        }
        if (!(e instanceof Player)) {
            return;
        }
        Player damager = (Player) e;
        User uDamager = controller.getUserManager().getUser(damager.getName());
        if (uDamager.getMatch() == null) {
            return;
        }
        if (!uDamager.getMatch().getArena().hasDamage()) {
            event.setCancelled(true);
            return;
        }
        if (uDamager.getMatch().getPlayers().size() == 1 || uDamager.getMatch().getArena().hasFriendlyFire()) {
            return;
        }
        User damagee = controller.getUserManager().getUser(((Player) event.getEntity()).getName());
        for (ArrayList<User> tempList : uDamager.getMatch().getPlayers()) {
            if (tempList.contains(damagee) && tempList.contains(uDamager)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}

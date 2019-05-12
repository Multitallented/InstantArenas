package org.redcastlemedia.multitallented.instantarenas.order;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class DamageOrder {
    public DamageOrder(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player) ||
                !(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent edby = (EntityDamageByEntityEvent) event;
        Entity e = edby.getDamager();
        ProjectileSource projectileSource = null;
        if (edby.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            projectileSource = ((Projectile) e).getShooter();
        }
        if (!(projectileSource instanceof Player)) {
            return;
        }
        Player damager = (Player) projectileSource;
        User uDamager = UserManager.getInstance().getUser(damager.getUniqueId());
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
        User damagee = UserManager.getInstance().getUser(event.getEntity().getUniqueId());
        for (ArrayList<User> tempList : uDamager.getMatch().getPlayers()) {
            if (tempList.contains(damagee) && tempList.contains(uDamager)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}

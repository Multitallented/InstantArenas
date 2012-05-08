/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.builders;

import org.bukkit.Location;

/**
 *
 * @author Multitallented
 */
public interface ArenaBuilder {
    public void build(Location loc);
    
    public Location getStartPoint(int i);
}

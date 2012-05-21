/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.builders;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Multitallented
 */
public interface ArenaBuilder {
    public void build(Location loc);
    
    public Location getStartPoint(int i);
    
    public HashSet<HashSet<Player>> getPlayers();
    
    public void setPlayers(HashSet<HashSet<Player>> players);
    
}

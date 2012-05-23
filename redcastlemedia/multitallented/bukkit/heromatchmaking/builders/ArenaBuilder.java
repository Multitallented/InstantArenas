/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.builders;

import redcastlemedia.multitallented.bukkit.heromatchmaking.GameType;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.TeamType;

/**
 *
 * @author Multitallented
 */
public abstract class ArenaBuilder {
    private HashSet<HashSet<Player>> players = new HashSet<HashSet<Player>>();
    private Location l;
    private int id;
    private boolean joinInProgress;
    private TeamType teamType;
    private GameType gameType;
    
    
    public void setLocation(int id, Location l) {
        this.l = l;
        this.id = id;
    }
    
    public abstract void build();
    
    public abstract Location getStartPoint(int i);
    
    public Location getLocation() {
        return l;
    }
    
    public int getID() {
        return id;
    }
    
    public abstract int getSize();
    
    public HashSet<HashSet<Player>> getPlayers() {
        return players;
    }

    public void setPlayers(HashSet<HashSet<Player>> players) {
        this.players = players;
    }
    
}

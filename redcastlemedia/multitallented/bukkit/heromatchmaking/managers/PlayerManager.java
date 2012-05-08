/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.managers;

import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.builders.ArenaBuilder;

/**
 *
 * @author Multitallented
 */
public class PlayerManager {
    private HashMap<Player, ArenaBuilder> playerLocations = new HashMap<Player, ArenaBuilder>();
    private HashSet<Player> queuingPlayers = new HashSet<Player>();
    
    public ArenaBuilder getPlayerLocation(Player p) {
        return playerLocations.get(p);
    }
    
    public void putPlayerLocation(Player p, ArenaBuilder ab) {
        playerLocations.put(p, ab);
    }
    
    public void removePlayerLocation(Player p) {
        playerLocations.remove(p);
    }
    
    public boolean containsQueuingPlayer(Player p) {
        return queuingPlayers.contains(p);
    }
    
    public void addQueuingPlayer(Player p) {
        queuingPlayers.add(p);
    }
    
    public void removeQueuingPlayer(Player p) {
        queuingPlayers.remove(p);
    }
    
    public HashSet<Player> checkStartMatch() {
        //TODO make matchmaking code here
        if (queuingPlayers.size() > 1) {
            HashSet<Player> tempSet = new HashSet<Player>();
            int i = 0;
            for (Player p : queuingPlayers) {
                tempSet.add(p);
                i++;
                if (i > 1) {
                    return tempSet;
                }
            }
            return null;
        } else {
            return null;
        }
    }
    
}

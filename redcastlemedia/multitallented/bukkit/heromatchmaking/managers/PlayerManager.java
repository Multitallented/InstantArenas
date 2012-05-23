/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.managers;

import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.entity.Player;
import redcastlemedia.multitallented.bukkit.heromatchmaking.PlayerPreferences;
import redcastlemedia.multitallented.bukkit.heromatchmaking.builders.ArenaBuilder;

/**
 *
 * @author Multitallented
 */
public class PlayerManager {
    private HashMap<Player, ArenaBuilder> playerLocations = new HashMap<Player, ArenaBuilder>();
    private HashSet<Player> queuingPlayers = new HashSet<Player>();
    private HashSet<Player> respawningPlayers = new HashSet<Player>();
    private HashMap<Player, PlayerPreferences> preferences = new HashMap<Player, PlayerPreferences>();
    
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
    
    public PlayerPreferences getPreference(Player p) {
        return preferences.get(p);
    }
    
    public void addQueuingPlayer(Player p) {
        queuingPlayers.add(p);
    }
    
    public void removeQueuingPlayer(Player p) {
        queuingPlayers.remove(p);
    }
    
    public boolean hasFightingPlayer(Player p) {
        return playerLocations.containsKey(p);
    }
    
    public boolean hasRespawningPlayer(Player p) {
        return respawningPlayers.contains(p);
    }
    
    public void addRespawningPlayer(Player p) {
        respawningPlayers.add(p);
    }
    
    public boolean removeRespawningPlayer(Player p) {
        if (respawningPlayers.contains(p)) {
            respawningPlayers.remove(p);
            return true;
        } else {
            return false;
        }
    }
    
    public HashSet<Player> checkStartMatch() {
        //TODO make matchmaking code here
        //check Heroes level range in config
        //check previous record and range in config
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

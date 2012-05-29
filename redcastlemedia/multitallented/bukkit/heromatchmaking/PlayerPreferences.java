/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redcastlemedia.multitallented.bukkit.heromatchmaking;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author Multitallented
 */
public class PlayerPreferences {
    private HashSet<GameType> arenas = new HashSet<GameType>();
    private HashSet<TeamType> teams = new HashSet<TeamType>();
    
    public PlayerPreferences() {
        arenas.addAll(Arrays.asList(GameType.values()));
        teams.addAll(Arrays.asList(TeamType.values()));
    }
    
    public boolean setGamePreference(GameType type) {
        if (arenas.contains(type)) {
            arenas.remove(type);
            return false;
        } else {
            arenas.add(type);
            return true;
        }
    }
    
    public boolean setTeamPreferences(TeamType type) {
        if (teams.contains(type)) {
            teams.remove(type);
            return false;
        } else {
            teams.add(type);
            return true;
        }
    }
    
    public HashSet<GameType> getGameTypes() {
        return arenas;
    }
    
    public HashSet<TeamType> getTeamTypes() {
        return teams;
    }
    
}

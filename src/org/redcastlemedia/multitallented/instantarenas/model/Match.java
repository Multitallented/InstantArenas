package org.redcastlemedia.multitallented.instantarenas.model;

import java.util.ArrayList;

/**
 *
 * @author Multitallented
 */
public class Match {
    private final boolean joinInProgress;
    private ArrayList<ArrayList<User>> players = new ArrayList<>();
    private Arena arena = null;
    private final GameType gType;
    private final TeamType tType;
    
    public Match(boolean joinInProgress, GameType gType, TeamType tType, ArrayList<ArrayList<User>> players) {
        this.joinInProgress = joinInProgress;
        this.gType = gType;
        this.tType = tType;
        this.players = players;
    }
    
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    
    public boolean getJoinInProgress() {
        return joinInProgress;
    }
    
    public Arena getArena() {
        return arena;
    }
    
    public GameType getGType() {
        return gType;
    }
    
    public TeamType getTType() {
        return tType;
    }
    
    public ArrayList<ArrayList<User>> getPlayers() {
        return players;
    }
    
    public ArrayList<User> getRawPlayers() {
        ArrayList<User> rawUsers = new ArrayList<>();
        for (ArrayList<User> list : players) {
            for (User u : list) {
                rawUsers.add(u);
            }
        }
        return rawUsers;
    }
    
    public void setPlayers(ArrayList<ArrayList<User>> players) {
        this.players = players;
    }
}

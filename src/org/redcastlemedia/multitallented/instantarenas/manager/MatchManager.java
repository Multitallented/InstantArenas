package org.redcastlemedia.multitallented.instantarenas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.model.Arena;
import org.redcastlemedia.multitallented.instantarenas.model.GameType;
import org.redcastlemedia.multitallented.instantarenas.model.Match;
import org.redcastlemedia.multitallented.instantarenas.model.TeamType;
import org.redcastlemedia.multitallented.instantarenas.model.User;


/**
 *
 * @author Multitallented
 */
public class MatchManager {
    private HashSet<Player> queuingPlayers = new HashSet<>();
    private ArrayList<Match> matches = new ArrayList<>();

    public static MatchManager getInstance() {
        if (instance == null) {
            new MatchManager();
        }
        return instance;
    }
    private static MatchManager instance = null;
    public MatchManager() {
        instance = this;
    }

    public void addQueuingPlayer(Player player) {
        queuingPlayers.add(player);
    }
    public void removeQueuingPlayer(Player player) {
        queuingPlayers.remove(player);
    }
    public boolean hasQueuingPlayer(Player player) {
        return queuingPlayers.contains(player);
    }
    public void addMatch(Match match) {
        matches.add(match);
    }
    public void removeMatch(Match match) {
        matches.remove(match);
    }
    
    public boolean checkEndMatch(User u) {
        Match currentMatch = u.getMatch();
        
        if (currentMatch.getTType() == TeamType.SOLO) {
            matches.remove(currentMatch);
            u.setMatch(null);
            return true;
        }
        currentMatch.getRawPlayers().remove(u);
        u.setMatch(null);
        u.setLoses(u.getLoses() + 1);
        for (ArrayList<User> tempList : currentMatch.getPlayers()) {
            if (tempList.contains(u)) {
                tempList.remove(u);
            }
        }
        boolean endMatch = false;
        for (ArrayList<User> tempList : currentMatch.getPlayers()) {
            if (currentMatch.getPlayers().size() > 1 && tempList.isEmpty()) {
                endMatch = true;
                endMatch(currentMatch);
            } else if (currentMatch.getPlayers().size() == 1 && tempList.size() < 2) {
                endMatch(currentMatch);
                endMatch = true;
            }
        }
        return endMatch;
    }
    
    public void clearDroppedItems(Arena ab) {
        Location l = ab.getLocation();
        int size = ab.getSize();
        for (Entity e : l.getWorld().getEntitiesByClass(Item.class)) {
           double x = e.getLocation().getX();
           double z = e.getLocation().getZ();
           if (x < l.getX() || x > l.getX() + size || z < l.getZ() || z > l.getZ() + size) {
               continue;
           }
           e.remove();
        }
    }
    
    public void endMatch(Match m) {
        matches.remove(m);
        for (User u : m.getRawPlayers()) {
            u.setWins(u.getWins() + 1);
            if (InstantArenas.econ != null) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(u.getUuid());
                InstantArenas.econ.depositPlayer(offlinePlayer, ConfigManager.getInstance().getWinnings());
            }
            u.setMatch(null);
            UserManager.getInstance().restorePreviousUserState(u);
        }
    }
    
    public HashMap<String, ArrayList<User>> getPotentialMatches(User user) {
        HashMap<String, ArrayList<User>> tempMap = new HashMap<>();

        if (user.getTType().contains(TeamType.SOLO)) {
            ArrayList<User> tempList = new ArrayList<>();
            tempList.add(user);
            for (GameType gType : user.getGType()) {
                tempMap.put(gType + ":" + "SOLO", tempList);
            }
            return tempMap;
        }

        for (Player p : queuingPlayers) {
            User u = UserManager.getInstance().getUser(p.getUniqueId());
            for (GameType gType : u.getGType()) {
                if (user.getGType().contains(gType)) {
                    for (TeamType tType : u.getTType()) {
                        if (user.getTType().contains(tType)) {
                            String key = gType.name() + ":" + tType.name();
                            if (tempMap.get(key) == null) {
                                ArrayList<User> newList = new ArrayList<>();
                                newList.add(u);
                                newList.add(user);
                                tempMap.put(key, newList);
                            } else {
                                tempMap.get(key).add(u);
                            }
                        }
                    }
                }
            }
        }
        HashSet<String> removeLater = new HashSet<>();
        for (String s : tempMap.keySet()) {
            TeamType tType = TeamType.valueOf(s.split(":")[1]);

            switch (tType) {
                default:
                case ONE_V_ONE:
                    break;
                case THREE_FFA:
                    if (tempMap.get(s).size() < 2) {
                        removeLater.add(s);
                    }
                    break;
                case TWO_V_TWO:
                case FOUR_FFA:
                    if (tempMap.get(s).size() < 3) {
                        removeLater.add(s);
                    }
                    break;
                case THREE_V_THREE:
                case MOSH_PIT:
                    if (tempMap.get(s).size() < 5) {
                        removeLater.add(s);
                    }
                    break;
                case BIG_TEAM:
                    if (tempMap.get(s).size() < 7) {
                        removeLater.add(s);
                    }
                    break;
            }
        }
        for (String s : removeLater) {
                tempMap.remove(s);
        }

        return tempMap;
    }
    
    public Match selectRandomMatch(HashMap<String, ArrayList<User>> tempMap) {
        int i = (int) (Math.random() * (tempMap.size() - 0.01));
        int j = 0;
        GameType gType = null;
        TeamType tType = null;
        String key = null;
        for (String s : tempMap.keySet()) {
            if (i != j) {
                j++;
                continue;
            }
            gType = GameType.valueOf(s.split(":")[0]);
            tType = TeamType.valueOf(s.split(":")[1]);
            key = s;
        }
        ArrayList<ArrayList<User>> teams = new ArrayList<>();
        ArrayList<User> team1 = new ArrayList<>();
        ArrayList<User> team2 = new ArrayList<>();
        switch (tType) {
            default:
            case ONE_V_ONE:
            case TWO_V_TWO:
            case THREE_V_THREE:
            case BIG_TEAM:
                int k = 1;
                for (User u : tempMap.get(key)) {
                    if (k == 1) {
                        team2.add(u);
                        k = 2;
                    } else {
                        team1.add(u);
                        k = 1;
                    }
                }
                teams.add(team1);
                teams.add(team2);
                break;
            case SOLO:
            case THREE_FFA:
            case FOUR_FFA:
                for (User u : tempMap.get(key)) {
                    team1.add(u);
                }
                teams.add(team1);
                break;
                
        }
        return new Match(false, gType, tType, teams);
    }
    
    public Location findSpace(int size) {
        int previousX = 0;
        for (Match m : matches) {
            try {
                int currentSize = m.getArena().getSize();
                int currentX = (int) m.getArena().getLocation().getX();
                if (previousX + size < currentX) {
                    break;
                } else {
                    previousX = currentX + currentSize;
                }
            } catch (NullPointerException npe) {
                
            }
        }
        return new Location(Bukkit.getWorld(ConfigManager.getInstance().getWorld()), previousX, 64, 0);
    }
}

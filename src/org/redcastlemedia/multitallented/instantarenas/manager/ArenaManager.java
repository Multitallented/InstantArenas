package org.redcastlemedia.multitallented.instantarenas.manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.redcastlemedia.multitallented.instantarenas.builder.LobbyArenaBuilder;
import org.redcastlemedia.multitallented.instantarenas.builder.PitfallArenaBuilder;
import org.redcastlemedia.multitallented.instantarenas.builder.RTSArenaBuilder;
import org.redcastlemedia.multitallented.instantarenas.builder.SpleefArenaBuilder;
import org.redcastlemedia.multitallented.instantarenas.model.Arena;
import org.redcastlemedia.multitallented.instantarenas.model.GameType;
import org.redcastlemedia.multitallented.instantarenas.model.Match;
import org.redcastlemedia.multitallented.instantarenas.model.TeamType;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class ArenaManager {
    private static ArenaManager instance = null;

    public static ArenaManager getInstance() {
        if (instance == null) {
            new ArenaManager();
        }
        return instance;
    }

    public ArenaManager() {
        this.instance = this;
    }
    
    public Arena getArena(GameType gType, TeamType tType) {
        switch(gType) {
            default:
            case ANYTHING_GOES:
            case ASSAULT:
            case CTF:
            case DOMINATION:
                return null;
            case LOBBY:
                switch(tType) {
                    case SOLO:
                        return new LobbyArenaBuilder();
                    default:
                        return null;
                }
            case PITFALL:
                switch(tType) {
                    case ONE_V_ONE:
                    case THREE_FFA:
                    case FOUR_FFA:
                    case MOSH_PIT:
                        return new PitfallArenaBuilder();
                    default:
                        return null;
                }
            case RTS:
                switch(tType) {
                    case TWO_V_TWO:
                    case ONE_V_ONE:
                        return new RTSArenaBuilder();
                    default:
                        return null;
                }
            case SPLEEF:
                switch(tType) {
                    case ONE_V_ONE:
                    case THREE_FFA:
                    case FOUR_FFA:
                    case MOSH_PIT:
                        return new SpleefArenaBuilder();
                    default:
                        return null;
                }
            case TDM:
            case VANILLA:
                return null;
            
        }
    }
    
    public void preparePlayers(Match match) {
        Arena arena = match.getArena();
        ArrayList<User> users = match.getRawPlayers();
        for (User u : users) {
            UserManager.getInstance().saveUserPreviousState(u);
            Player player = Bukkit.getPlayer(u.getUuid());
            player.getInventory().clear();
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            try {
                player.getInventory().setHelmet(arena.getStartingItems().get(0));
                player.getInventory().setChestplate(arena.getStartingItems().get(1));
                player.getInventory().setLeggings(arena.getStartingItems().get(2));
                player.getInventory().setBoots(arena.getStartingItems().get(3));
                for (int i = 4; i<arena.getStartingItems().size(); i++) {
                    player.getInventory().addItem(arena.getStartingItems().get(i));
                }
            } catch (Exception e) {
                
            }
            player.setFoodLevel(20);
            player.setHealth(20);
            player.setExp(0f);
        }
        if (match.getPlayers().size() == 2) {
            for (User u : match.getPlayers().get(0)) {
                Player p = Bukkit.getPlayer(u.getUuid());
                if (p.getInventory().getHelmet() == null) {
                    p.getInventory().setHelmet(new ItemStack(Material.BLUE_WOOL, 1));
                }
            }
            for (User u : match.getPlayers().get(1)) {
                Player p = Bukkit.getPlayer(u.getUuid());
                if (p.getInventory().getHelmet() == null) {
                    p.getInventory().setHelmet(new ItemStack(Material.RED_WOOL, 1));
                }
            }
        }
        if (match.getRawPlayers().size() == 1) {
            Player p = Bukkit.getPlayer(match.getRawPlayers().get(0).getUuid());
            p.teleport(arena.getStartPoint(0));
            return;
        }
        try {
            int k = 0;
            for (int i=0; i< match.getPlayers().get(0).size(); i++) {
                User u = match.getPlayers().get(0).get(i);
                Player p = Bukkit.getPlayer(u.getUuid());
                p.teleport(arena.getStartPoint(k));
                u.setMatch(match);
                k+=2;
            }
            k=1;
            for (int i=0; i< match.getPlayers().get(1).size(); i++) {
                User u = match.getPlayers().get(1).get(i);
                Player p = Bukkit.getPlayer(u.getUuid());
                p.teleport(arena.getStartPoint(k));
                u.setMatch(match);
                k+=2;
            }
        } catch (Exception e) {
            
        }
    }
    
}
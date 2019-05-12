package org.redcastlemedia.multitallented.instantarenas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Multitallented
 */
@Getter
@Setter
public class User {
    private final UUID uuid;
    private int wins;
    private int loses;
    private List<GameType> gType;
    private List<TeamType> tType;
    private Match match = null;
    private ArrayList<ItemStack> previousInventory = null;
    private double previousHP = 0;
    private int previousStamina = -1;
    private float previousExp = 0;
    private String previousLocation = null;
    private int previousMana = 0;
    
    public User(UUID uuid, int wins, int loses, List<GameType> gType, List<TeamType> tType) {
        this.uuid = uuid;
        this.wins = wins;
        this.loses = loses;
        this.gType = gType;
        this.tType = tType;
    }
    public User(UUID uuid, int wins, int loses, List<GameType> gType, List<TeamType> tType, String l) {
        this.uuid = uuid;
        this.wins = wins;
        this.loses = loses;
        this.gType = gType;
        this.tType = tType;
        this.previousLocation = l;
    }

    public Location getPreviousLocation() {
        if (previousLocation == null || previousLocation.equals("")) {
            return null;
        }
        String[] locations = previousLocation.split(";");
        return new Location(Bukkit.getWorld(locations[0]), Double.parseDouble(locations[1]), Double.parseDouble(locations[2]), Double.parseDouble(locations[3]));
    }
    public String getRawPreviousLocation() {
        return this.previousLocation;
    }
    public void setPreviousLocation(String input) {
        this.previousLocation = input;
    }

    public List<String> getStringGType() {
        List<String> tempList = new ArrayList<>();
        for (GameType gTypes : this.gType) {
            tempList.add(gTypes.name());
        }
        return tempList;
    }

    public List<String> getStringTType() {
        List<String> tempList = new ArrayList<>();
        for (TeamType tTypes : this.tType) {
            tempList.add(tTypes.name());
        }
        return tempList;
    }
}

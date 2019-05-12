package org.redcastlemedia.multitallented.instantarenas.model;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Multitallented
 */
public abstract class Arena {

    @Getter
    @Setter
    private Location location = null;
    
    public abstract HashSet<TeamType> getTeamTypes();
    
    public abstract GameType getGameType();
    
    public abstract ArrayList<ItemStack> getStartingItems();
    
    public abstract void build();
    
    public abstract Location getStartPoint(int i);
    
    public abstract int getSize();
    
    public abstract boolean hasDamage();
    
    public abstract boolean hasFriendlyFire();
    
    public abstract boolean canBuild();
    
    public abstract int getLives();
    
    public abstract boolean isAnythingGoes();
    
    public void loadChunks() {
        if (location.getWorld() == null) {
            return;
        }
        double i = location.getX() - 32;
        double k = location.getZ() - 32;
        do {
            i += 32;
            k += 32;
            Chunk chunk = location.getWorld().getChunkAt(new Location(location.getWorld(), i, 65, k));
            if (!chunk.isLoaded()) {
                chunk.load(true);
            }
        } while (i<getSize() + location.getX() && k < getSize() + location.getZ());
    }
}

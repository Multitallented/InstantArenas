package redcastlemedia.multitallented.bukkit.heromatchmaking.model;

import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;

/**
 *
 * @author Multitallented
 */
public abstract class Arena {
    private final HeroMatchMaking plugin;
    
    public Arena(HeroMatchMaking plugin) {
        this.plugin = plugin;
    }
    
    public HeroMatchMaking getPlugin() {
        return plugin;
    }
    
    private Location l = null;
    
    public abstract HashSet<TeamType> getTeamTypes();
    
    public abstract GameType getGameType();
    
    public abstract ArrayList<ItemStack> getStartingItems();
    
    public abstract void build();
    
    public abstract Location getStartPoint(int i);
    
    public abstract int getSize();
    
    public abstract boolean hasDamage();
    
    public abstract boolean hasFriendlyFire();
    
    public abstract HeroClass getHeroClass();
    
    public abstract HeroClass getProf();
    
    public abstract boolean canBuild();
    
    public abstract int getLives();
    
    public abstract boolean isAnythingGoes();
    
    public Location getLocation() {
        return l;
    }
    public void setLocation(Location l) {
        this.l = l;
    }
    
    public void loadChunks() {
        double i = l.getX() - 32;
        double k = l.getZ() - 32;
        do {
            i += 32;
            k += 32;
            Chunk chunk = l.getWorld().getChunkAt(new Location(l.getWorld(), i, 65, k));
            if (!chunk.isLoaded()) {
                chunk.load(true);
            }
        } while (i<getSize() + l.getX() && k < getSize() + l.getZ());
    }
}

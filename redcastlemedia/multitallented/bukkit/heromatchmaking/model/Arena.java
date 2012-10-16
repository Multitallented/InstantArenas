package redcastlemedia.multitallented.bukkit.heromatchmaking.model;

import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Multitallented
 */
public abstract class Arena {
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
}

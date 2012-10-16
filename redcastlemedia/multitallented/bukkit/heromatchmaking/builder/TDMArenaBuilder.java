package redcastlemedia.multitallented.bukkit.heromatchmaking.builder;

import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.Arena;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;

/**
 *
 * @author Multitallented
 */
public class TDMArenaBuilder extends Arena implements Listener {
    public TDMArenaBuilder(HeroMatchMaking hmm) {
        super(hmm);
    }

    @Override
    public HashSet<TeamType> getTeamTypes() {
        HashSet<TeamType> types = new HashSet<>();
        types.add(TeamType.ONE_V_ONE);
        types.add(TeamType.TWO_V_TWO);
        types.add(TeamType.THREE_V_THREE);
        types.add(TeamType.BIG_TEAM);
        return types;
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        
    }
    
    @Override
    public boolean isAnythingGoes() {
        return false;
    }
    
    @Override
    public int getLives() {
        return 999;
    }
    
    @Override
    public boolean canBuild() {
        return false;
    }

    @Override
    public GameType getGameType() {
        return GameType.TDM;
    }
    
    @Override
    public boolean hasFriendlyFire() {
        return false;
    }
    
    @Override
    public boolean hasDamage() {
        return true;
    }

    @Override
    public ArrayList<ItemStack> getStartingItems() {
        ArrayList<ItemStack> tempMap = new ArrayList<>();
        tempMap.add(null);
        tempMap.add(null);
        tempMap.add(null);
        tempMap.add(null);
        tempMap.add(new ItemStack(Material.BOW, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_SWORD, 1));
        tempMap.add(new ItemStack(Material.ARROW, 10));
        return tempMap;
    }

    @Override
    public void build() {
        super.loadChunks();
        Location loc = super.getLocation();
        Location l = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        World world = l.getWorld();
        int x0 = (int) l.getX();
        int y0 = (int) l.getY();
        int z0 = (int) l.getZ();
        
        for (int x=0; x<21;x++) {
            for (int y=0; y<25; y++) {
                for (int z=0; z<21; z++) {
                    Material mat = Material.AIR;
                    if (y == 0 || x == 0 || x==20 || z==0 || z==20) {
                        mat = Material.BEDROCK;
                    } else if (y == 1) {
                        mat = Material.GLOWSTONE;
                    } else if (y == 24) {
                        mat = Material.GLASS;
                    }
                    
                    
                    world.getBlockAt(x0 + x, y0 + y, z0 + z).setType(mat);
                }
            }
        }
    }

    @Override
    public Location getStartPoint(int i) {
        Location l = super.getLocation();
        if (i==0) {
            return new Location(l.getWorld(), l.getX() + 3, l.getY() + 2.5, l.getZ() + 3);
        } else if (i==1) {
            return new Location(l.getWorld(), l.getX() + 9, l.getY() + 2.5, l.getZ() + 9);
        } else if (i==2) {
            return new Location(l.getWorld(), l.getX() + 3, l.getY() + 2.5, l.getZ() + 9);
        } else if (i==3) {
            return new Location(l.getWorld(), l.getX() + 9, l.getY() + 2.5, l.getZ() + 3);
        } else if (i==4) {
            return new Location(l.getWorld(), l.getX() + 6, l.getY() + 2.5, l.getZ() + 3);
        } else if (i==5) {
            return new Location(l.getWorld(), l.getX() + 6, l.getY() + 2.5, l.getZ() + 9);
        } else {
            return new Location(l.getWorld(), l.getX() + 3, l.getY() + 2.5, l.getZ() + 3);
        }
    }

    @Override
    public int getSize() {
        return 22;
    }
    
    @Override
    public HeroClass getHeroClass() {
        return HeroMatchMaking.heroes.getClassManager().getClass("VanillaClass");
    }
    
    @Override
    public HeroClass getProf() {
        return HeroMatchMaking.heroes.getClassManager().getClass("VanillaProf");
    }
}
package redcastlemedia.multitallented.bukkit.heromatchmaking.builder;

import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.Arena;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;

/**
 *
 * @author Multitallented
 */
public class SpleefArenaBuilder extends Arena {
    public SpleefArenaBuilder(HeroMatchMaking hmm) {
        super(hmm);
    }

    @Override
    public HashSet<TeamType> getTeamTypes() {
        HashSet<TeamType> types = new HashSet<>();
        types.add(TeamType.ONE_V_ONE);
        types.add(TeamType.THREE_FFA);
        types.add(TeamType.FOUR_FFA);
        types.add(TeamType.MOSH_PIT);
        return types;
    }
    
    @Override
    public boolean isAnythingGoes() {
        return false;
    }
    
    @Override
    public int getLives() {
        return 1;
    }
    
    @Override
    public boolean canBuild() {
        return true;
    }

    @Override
    public GameType getGameType() {
        return GameType.SPLEEF;
    }
    
    @Override
    public boolean hasFriendlyFire() {
        return false;
    }
    
    @Override
    public boolean hasDamage() {
        return false;
    }

    @Override
    public ArrayList<ItemStack> getStartingItems() {
        ArrayList<ItemStack> tempMap = new ArrayList<>();
        tempMap.add(new ItemStack(Material.DIAMOND_HELMET, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_BOOTS, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_SPADE, 1));
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
        
        for (int x=0; x<12;x++) {
            for (int y=0; y<7; y++) {
                for (int z=0; z<12; z++) {
                    Material mat = Material.AIR;
                    if (y == 0 || y == 6 || x == 0 || x==11 || z==0 || z==11) {
                        mat = Material.BEDROCK;
                    } else if (y == 1) {
                        mat = Material.STATIONARY_LAVA;
                    } else if (y == 2) {
                        mat = Material.SNOW_BLOCK;
                    } else if (y == 5) {
                        mat = Material.GLOWSTONE;
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
        return 12;
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
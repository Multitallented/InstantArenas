/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.builders;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import redcastlemedia.multitallented.bukkit.heromatchmaking.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.TeamType;

/**
 *
 * @author Multitallented
 */
public class SpleefArenaBuilder extends ArenaBuilder {
    
    @Override
    public Location getStartPoint(int i) {
        Location l = super.getLocation();
        if (i==0) {
            return new Location(l.getWorld(), l.getX() + 2.5, l.getY() + 67.5, l.getZ() + 2.5);
        } else if (i==1) {
            return new Location(l.getWorld(), l.getX() + 17.5, l.getY() + 67.5, l.getZ() + 2.5);
        } else if (i==2) {
            return new Location(l.getWorld(), l.getX() + 17.5, l.getY() + 67.5, l.getZ() + 17.5);
        } else if (i==3) {
            return new Location(l.getWorld(), l.getX() + 2.5, l.getY() + 67.5, l.getZ() + 17.5);
        } else {
            return new Location(l.getWorld(), l.getX() + 2.5, l.getY() + 67.5, l.getZ() + 2.5);
        }
    }
    
    @Override
    public void build() {
        Location loc = super.getLocation();
        Location l = new Location(loc.getWorld(), loc.getX(), loc.getY() + 64, loc.getZ());
        World world = l.getWorld();
        int x0 = (int) l.getX();
        int y0 = (int) l.getY();
        int z0 = (int) l.getZ();
        
        for (int x=0; x<21;x++) {
            for (int y=0; y<7; y++) {
                for (int z=0; z<21; z++) {
                    Material mat = Material.AIR;
                    if (y == 0 || y == 6 || x == 0 || x==20 || z==0 || z==20) {
                        mat = Material.BEDROCK;
                    } else if (y==1) {
                        mat = Material.STATIONARY_LAVA;
                    } else if (y==2) {
                        mat = Material.CLAY;
                    } else if (y==6) {
                        mat = Material.GLOWSTONE;
                    }
                    
                    
                    world.getBlockAt(x0 + x, y0 + y, z0 + z).setType(mat);
                }
            }
        }
        
    }

    @Override
    public int getSize() {
        return 20;
    }
    
    
    @Override
    public GameType getGameType() {
        return GameType.RTS;
    }
    
    @Override
    public HashSet<TeamType> getTeamTypes() {
        HashSet<TeamType> types = new HashSet<TeamType>();
        types.add(TeamType.ONE_V_ONE);
        types.add(TeamType.TWO_V_TWO);
        types.add(TeamType.THREE_FFA);
        types.add(TeamType.FOUR_FFA);
        types.add(TeamType.MOSH_PIT);
        return types;
    }
    
    @Override
    public HashSet<ItemStack> getStartingItems() {
        HashSet<ItemStack> items = new HashSet<ItemStack>();
        items.add(new ItemStack(Material.DIAMOND_SPADE, 1));
        return items;
    }
    
}

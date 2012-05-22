/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.builders;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Multitallented
 */
public class RTSArenaBuilder extends ArenaBuilder {
    
    @Override
    public Location getStartPoint(int i) {
        Location l = super.getLocation();
        if (i==0) {
            return new Location(l.getWorld(), l.getX() + 3.5, l.getY() + 66, l.getZ() + 4.5);
        } else if (i==1) {
            return new Location(l.getWorld(), l.getX() + 17.5, l.getY() + 66, l.getZ() + 16.5);
        } else if (i==2) {
            return new Location(l.getWorld(), l.getX() + 4.5, l.getY() + 66, l.getZ() + 3.5);
        } else if (i==3) {
            return new Location(l.getWorld(), l.getX() + 16.5, l.getY() + 66, l.getZ() + 17.5);
        } else {
            return new Location(l.getWorld(), l.getX() + 3.5, l.getY() + 66, l.getZ() + 4.5);
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
                    } else if ((x==3 && z==3) || (x==17 && z==17)) {
                        mat = Material.LOG;
                    } else if ((y>2 && y<6) && (x<6 || x>14) && (z<6 || z>14 ) && (x + z > 29 || x + z < 9)) {
                        mat = Material.LEAVES;
                    } else if ((x<3 && z > 17) || (x>17 && z<3)) {
                        mat = Material.DIRT;
                    } else if (x + z == 20) {
                        mat = Material.SMOOTH_BRICK;
                    }
                    if ((y==1 || y==2)) {
                        if ((x==8 && z==9) || (x==9 && z==8) || (x==12 && z==11) || (x==11 && z==12)) {
                            mat = Material.IRON_FENCE;
                        } else if ((x>7 && x<11) && (z>7 && z<11)) {
                            mat = Material.COBBLESTONE;
                        } else if ((x>9 && x<13) && (z>9 && z<13)) {
                            mat = Material.COBBLESTONE;
                        }
                    }
                    
                    
                    world.getBlockAt(x0 + x, y0 + y, z0 + z).setType(mat);
                }
            }
        }
        world.getBlockAt(x0 + 9, y0 + 1, z0 + 9).setType(Material.CHEST);
        world.getBlockAt(x0 + 11, y0 + 1, z0 + 11).setType(Material.CHEST);
        world.getBlockAt(x0 + 2, y0 + 1, z0 + 5).setType(Material.CHEST);
        world.getBlockAt(x0 + 18, y0 + 1, z0 + 15).setType(Material.CHEST);
        world.getBlockAt(x0 + 10, y0 + 3, z0 + 10).setType(Material.FURNACE);
        world.getBlockAt(x0 + 3, y0 + 1, z0 + 5).setType(Material.WORKBENCH);
        world.getBlockAt(x0 + 17, y0 + 1, z0 + 15).setType(Material.WORKBENCH);
        world.getBlockAt(x0 + 11, y0 + 1, z0 + 10).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 9, y0 + 1, z0 + 10).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 10, y0 + 1, z0 + 11).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 10, y0 + 4, z0 + 10).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 10, y0 + 1, z0 + 9).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 18, y0 + 2, z0 + 2).setType(Material.DIAMOND_ORE);
        world.getBlockAt(x0 + 2, y0 + 2, z0 + 18).setType(Material.DIAMOND_ORE);
        world.getBlockAt(x0 + 10, y0 + 5, z0 + 10).setType(Material.DIAMOND_ORE);
        world.getBlockAt(x0 + 15, y0 + 3, z0 + 5).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 5, y0 + 3, z0 + 15).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 3, y0 + 5, z0 + 3).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 17, y0 + 5, z0 + 17).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 12, y0 + 1, z0 + 9).setType(Material.SMOOTH_BRICK);
        world.getBlockAt(x0 + 9, y0 + 1, z0 + 12).setType(Material.SMOOTH_BRICK);
        world.getBlockAt(x0 + 11, y0 + 1, z0 + 8).setType(Material.SMOOTH_BRICK);
        world.getBlockAt(x0 + 8, y0 + 1, z0 + 11).setType(Material.SMOOTH_BRICK);
        
        try {
            Chest chest1 = (Chest) world.getBlockAt(x0 + 9, y0 + 1, z0 + 9).getState();
            Chest chest2 = (Chest) world.getBlockAt(x0 + 11, y0 + 1, z0 + 11).getState();
            Chest chest3 = (Chest) world.getBlockAt(x0 + 2, y0 + 1, z0 + 5).getState();
            Chest chest4 = (Chest) world.getBlockAt(x0 + 18, y0 + 1, z0 + 15).getState();
            Furnace furnace1 = (Furnace) world.getBlockAt(x0 + 10, y0 + 3, z0 + 10).getState();
            chest1.getInventory().clear();
            chest1.getInventory().addItem(new ItemStack(Material.BOW, 1));
            chest1.getInventory().addItem(new ItemStack(Material.ARROW, 64));
            chest1.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 8));
            chest1.update();
            chest2.getInventory().clear();
            chest2.getInventory().addItem(new ItemStack(Material.BOW, 1));
            chest2.getInventory().addItem(new ItemStack(Material.ARROW, 64));
            chest2.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 8));
            chest2.update();
            chest3.getInventory().clear();
            chest3.getInventory().addItem(new ItemStack(Material.SMOOTH_BRICK, 8));
            chest3.update();
            chest4.getInventory().clear();
            chest4.getInventory().addItem(new ItemStack(Material.SMOOTH_BRICK, 8));
            chest4.update();
            furnace1.getInventory().clear();
            furnace1.getInventory().setFuel(new ItemStack(Material.COAL, 64));
            furnace1.update();
        } catch (Exception e) {
            
        }
    }

    @Override
    public int getSize() {
        return 20;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.builders;

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
public class RTSArenaBuilder implements ArenaBuilder {
    private Location l;
    
    @Override
    public Location getStartPoint(int i) {
        if (i==0) {
            return new Location(l.getWorld(), l.getX() + 3, l.getY() + 1, l.getZ() + 4);
        } else if (i==1) {
            return new Location(l.getWorld(), l.getX() + 17, l.getY() + 1, l.getZ() + 16);
        } else if (i==2) {
            return new Location(l.getWorld(), l.getX() + 4, l.getY() + 1, l.getZ() + 3);
        } else if (i==3) {
            return new Location(l.getWorld(), l.getX() + 16, l.getY() + 1, l.getZ() + 17);
        } else {
            return new Location(l.getWorld(), l.getX() + 3, l.getY() + 1, l.getZ() + 4);
        }
    }
    
    @Override
    public void build(Location loc) {
        this.l = loc;
        World world = loc.getWorld();
        int x0 = (int) loc.getX();
        int y0 = (int) loc.getY();
        int z0 = (int) loc.getZ();
        
        for (int i=0; i<21;i++) {
            for (int j=0; j<21; j++) {
                for (int k=0; k<7; k++) {
                    Material mat = Material.AIR;
                    if (k == 0 || k == 6 || i == 0 || i==20 || j==0 || j==20) {
                        mat = Material.BEDROCK;
                    } else if ((i==3 && j==3) || (i==17 && j==17)) {
                        mat = Material.LOG;
                    } else if ((k>1 && k<6) && (i<6 || i>14) && (k<6 || k>14 )) {
                        mat = Material.LEAVES;
                    } else if ((k==1 || k==2)) {
                        if ((i==8 && k==9) || (i==9 && k==8)) {
                            mat = Material.IRON_FENCE;
                        } else if ((i>7 && i<11) && (j>7&&j<11)) {
                            mat = Material.COBBLESTONE;
                        } else if ((i>9 && i<13) && (j>9 && j<13)) {
                            mat = Material.COBBLESTONE;
                        }
                    } else if ((i<3 && j > 17) || (i>17 && j<3)) {
                        mat = Material.DIRT;
                    } else if (i + j == 20) {
                        mat = Material.SMOOTH_BRICK;
                    }
                    
                    
                    world.getBlockAt(x0 + i, y0 + j, z0 + k).setType(mat);
                }
            }
        }
        world.getBlockAt(x0 + 9, y0 + 9, z0 + 1).setType(Material.CHEST);
        world.getBlockAt(x0 + 11, y0 + 11, z0 + 1).setType(Material.CHEST);
        world.getBlockAt(x0 + 2, y0 + 5, z0 + 1).setType(Material.CHEST);
        world.getBlockAt(x0 + 18, y0 + 15, z0 + 1).setType(Material.CHEST);
        world.getBlockAt(x0 + 10, y0 + 10, z0 + 3).setType(Material.FURNACE);
        world.getBlockAt(x0 + 3, y0 + 5, z0 + 1).setType(Material.WORKBENCH);
        world.getBlockAt(x0 + 17, y0 + 15, z0 + 1).setType(Material.WORKBENCH);
        world.getBlockAt(x0 + 11, y0 + 10, z0 + 1).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 9, y0 + 10, z0 + 1).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 10, y0 + 11, z0 + 1).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 10, y0 + 10, z0 + 4).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 10, y0 + 9, z0 + 1).setType(Material.IRON_ORE);
        world.getBlockAt(x0 + 18, y0 + 2, z0 + 2).setType(Material.DIAMOND_ORE);
        world.getBlockAt(x0 + 2, y0 + 18, z0 + 2).setType(Material.DIAMOND_ORE);
        world.getBlockAt(x0 + 10, y0 + 10, z0 + 5).setType(Material.DIAMOND_ORE);
        world.getBlockAt(x0 + 15, y0 + 5, z0 + 3).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 5, y0 + 15, z0 + 3).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 3, y0 + 3, z0 + 5).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 17, y0 + 17, z0 + 5).setType(Material.GLOWSTONE);
        world.getBlockAt(x0 + 12, y0 + 9, z0 + 1).setType(Material.SMOOTH_BRICK);
        world.getBlockAt(x0 + 9, y0 + 12, z0 + 1).setType(Material.SMOOTH_BRICK);
        world.getBlockAt(x0 + 11, y0 + 8, z0 + 1).setType(Material.SMOOTH_BRICK);
        world.getBlockAt(x0 + 8, y0 + 11, z0 + 1).setType(Material.SMOOTH_BRICK);
        
        try {
            Chest chest1 = (Chest) world.getBlockAt(x0 + 9, y0 + 9, z0 + 1).getState();
            Chest chest2 = (Chest) world.getBlockAt(x0 + 11, y0 + 11, z0 + 1).getState();
            Chest chest3 = (Chest) world.getBlockAt(x0 + 2, y0 + 5, z0 + 1).getState();
            Chest chest4 = (Chest) world.getBlockAt(x0 + 18, y0 + 15, z0 + 1).getState();
            Furnace furnace1 = (Furnace) world.getBlockAt(x0 + 10, y0 + 10, z0 + 3).getState();
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
    
}

package org.redcastlemedia.multitallented.instantarenas.builder;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Lever;
import org.redcastlemedia.multitallented.instantarenas.model.Arena;
import org.redcastlemedia.multitallented.instantarenas.model.GameType;
import org.redcastlemedia.multitallented.instantarenas.model.TeamType;

/**
 *
 * @author Multitallented
 */
public class LobbyArenaBuilder extends Arena {

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
        return false;
    }

    @Override
    public void build() {
        super.loadChunks();
        Location loc = super.getLocation();
        World world = loc.getWorld();
        int x0 = (int) loc.getX();
        int y0 = (int) loc.getY();
        int z0 = (int) loc.getZ();

        for (int i=0; i<20;i++) {
            for (int j=0; j<20; j++) {
                for (int k=0; k<9; k++) {
                    Material mat = Material.AIR;
                    if (k == 0 || k == 8 || i == 0 || i == 19 || j == 0 || j == 19) {
                        mat = Material.BEDROCK;
                    } else if(i == 1 || i == 18 || j == 1 || j == 18){
                        mat = Material.OAK_LOG;
                    } else if(i == 2 || i == 17 || j == 2 || j == 17){
                        mat = Material.OAK_LEAVES;
                    } else {
                        if(k == 1 || k == 7){
                            if(i < 7 || i > 12 || j < 7 || j > 12){
                                if(i == j || i == 19 - j){
                                    mat = Material.GOLD_BLOCK;
                                }
                                if(i < 6 || i > 13 || j < 6 || j > 13){
                                    if(i == j - 1 || j == i - 1 || i == 18 - j || j == 20 - i){
                                        mat = Material.IRON_BLOCK;
                                    }
                                }
                            }
                            if(((i > 7 && i < 12) && (j > 6 && j < 13)) || ((j > 7 && j < 12) && (i > 6 & i < 13))){
                                mat = Material.WHITE_WOOL; //white wool
                            }
                            if(((i > 7 && i < 12) && (j == 6 || j == 13)) || ((j > 7 && j < 12) && (i == 6 || i == 13))){
                                    mat = Material.BLUE_WOOL; //blue wool
                            }
                            if((i == 9 || i == 10) && (j == 9 || j == 10)){
                                mat = Material.LAPIS_BLOCK;
                            }
                            if(mat == Material.AIR){
                                double a = Math.random();
                                if(a < 0.7){
                                    //normal stone brick
                                    mat = Material.STONE_BRICKS;
                                } else if(a < 0.85){
                                    //mossy stone brick
                                    mat = Material.MOSSY_STONE_BRICKS;
                                } else {
                                    //cracked stone brick
                                    mat = Material.CRACKED_STONE_BRICKS;
                                }
                            }
                        } else {
                            if(k == 6){
                                if(((i == 7 || i == 12) && j > 7 && j < 12) || ((j == 7 || j == 12) && i > 7 && i < 12)){
                                    mat = Material.IRON_BARS;
                                }
                            }
                            if((i == 8 && j == 8) || (i == 8 && j == 11) || (i == 11 && j == 8) || (i == 11 && j == 11)){
                                if(k == 6){
                                    mat = Material.OAK_FENCE;
                                } else if(k == 5){
                                    mat = Material.GLOWSTONE;
                                }
                            }
                            if((i == 7 && j == 7) || (i == 12 && j == 7) || (i == 7 && j == 12) || (i == 12 && j == 12) ||
                                    (i == 6 && j == 7) || (i == 13 && j == 7) || (i == 6 && j == 12) || (i == 13 && j == 12) ||
                                    (j == 6 && i == 7) || (j == 13 && i == 7) || (j == 6 && i == 12) || (j == 13 && i == 12)){
                                mat = Material.IRON_BARS;
                            }
                        }
                    }
                    world.getBlockAt(x0 + i, y0 + k, z0 + j).setType(mat);
                }
            }
        }

        Material dc = Material.RED_WOOL; //red wool
        String type = "Game Type";
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 3, dc, BlockFace.SOUTH, type, "RTS");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 6, dc, BlockFace.SOUTH, type, "Spleef");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 9, dc, BlockFace.SOUTH, type, "Vanilla");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 12, dc, BlockFace.SOUTH, type, "Anything Goes");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 15, dc, BlockFace.SOUTH, type, "Pitfall");
        placeSwitch(world, x0 + 4, y0 + 2, z0 + 17, dc, BlockFace.EAST, type, "CTF");
        placeSwitch(world, x0 + 7, y0 + 2, z0 + 17, dc, BlockFace.EAST, type, "Domination");
        placeSwitch(world, x0 + 10, y0 + 2, z0 + 17, dc, BlockFace.EAST, type, "TDM");
        placeSwitch(world, x0 + 13, y0 + 2, z0 + 17, dc, BlockFace.EAST, type, "Assault");
        placeSwitch(world, x0 + 16, y0 + 2, z0 + 17, dc, BlockFace.EAST, type, "Custom");

        dc = Material.YELLOW_WOOL; //yellow wool
        type = "Team Type";
        placeSwitch(world, x0 + 5, y0 + 2, z0 + 2, dc, BlockFace.WEST, type, "One V One");
        placeSwitch(world, x0 + 8, y0 + 2, z0 + 2, dc, BlockFace.WEST, type, "Two V Two");
        placeSwitch(world, x0 + 11, y0 + 2, z0 + 2, dc, BlockFace.WEST, type, "Three FFA");
        placeSwitch(world, x0 + 14, y0 + 2, z0 + 2, dc, BlockFace.WEST, type, "Four FFA");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 5, dc, BlockFace.NORTH, type, "Three V Three");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 8, dc, BlockFace.NORTH, type, "Mosh Pit");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 11, dc, BlockFace.NORTH, type, "Big Team");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 14, dc, BlockFace.NORTH, type, "Solo");

        try {

        } catch (Exception e) {

        }
    }

    @Override
    public boolean hasDamage() {
        return false;
    }

    @Override
    public boolean hasFriendlyFire() {
        return false;
    }

    @Override
    public Location getStartPoint(int i) {
        return this.getLocation().add(10, 2, 10);
    }

    @Override
    public int getSize() {
        return 20;
    }

    @Override
    public GameType getGameType() {
        return GameType.LOBBY;
    }

    @Override
    public HashSet<TeamType> getTeamTypes() {
        HashSet<TeamType> types = new HashSet<>();
        types.add(TeamType.SOLO);
        return types;
    }

    @Override
    public ArrayList<ItemStack> getStartingItems() {
        return new ArrayList<>();
    }

    public void placeSwitch(World world, int x, int y, int z, Material dc, BlockFace bf, String type, String name){
        world.getBlockAt(x, y, z).setType(dc);
        world.getBlockAt(x, y, z).getRelative(BlockFace.UP).setType(dc);
        Lever lever = new Lever(Material.LEVER);
        lever.setFacingDirection(bf);

        Block block = world.getBlockAt(x, y, z).getRelative(bf);
        block.setType(lever.getItemType());
        ((Lever) block.getState()).setFacingDirection(bf);
        org.bukkit.material.Sign mSign = new org.bukkit.material.Sign();
        mSign.setFacingDirection(bf);
        world.getBlockAt(x, y, z).getRelative(BlockFace.UP).getRelative(bf).setType(Material.OAK_WALL_SIGN);
//        world.getBlockAt(x, y, z).getRelative(BlockFace.UP).getRelative(bf).setData(bfSign);
        Sign sign = (Sign)world.getBlockAt(x, y, z).getRelative(BlockFace.UP).getRelative(bf).getState();
        sign.setLine(0, type);
        sign.setLine(1, name);
        sign.setLine(2, "[OFF]");
        sign.update(true);
    }
}
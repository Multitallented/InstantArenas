package redcastlemedia.multitallented.bukkit.heromatchmaking.builder;

import com.herocraftonline.heroes.characters.classes.HeroClass;
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

import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.Arena;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;

/**
 *
 * @author Multitallented
 */
public class LobbyArenaBuilder extends Arena {
    public LobbyArenaBuilder(HeroMatchMaking hmm) {
        super(hmm);
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
                    	mat = Material.LOG;
                    } else if(i == 2 || i == 17 || j == 2 || j == 17){
                    	mat = Material.LEAVES;
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
                    			mat = Material.WOOL; //while wool
                    		}
                    		if(((i > 7 && i < 12) && (j == 6 || j == 13)) || ((j > 7 && j < 12) && (i == 6 || i == 13))){
                    			mat = Material.LAPIS_ORE; //temp
                    			//mat = Material.WOOL; //blue wool
                    		}
                    		if((i == 9 || i == 10) && (j == 9 || j == 10)){
                    			mat = Material.LAPIS_BLOCK;
                    		}
                    		if(mat == Material.AIR){
                    			double a = Math.random();
                    			if(a < 0.75){
                    				mat = Material.STONE; //normal stone brick
                    			} else if(a < 0.9){
                    				mat = Material.MOSSY_COBBLESTONE; //mossy stone brick
                    			} else {
                    				mat = Material.COBBLESTONE; //cracked stone brick
                    			}
                    		}
                    	} else {
                    		if(k == 6){
                    			if(((i == 7 || i == 12) && j > 7 && j < 12) || ((j == 7 || j == 12) && i > 7 && i < 12)){
                    				mat = Material.IRON_FENCE;
                    			}
                    		}
                    		if((i == 8 && j == 8) || (i == 8 && j == 11) || (i == 11 && j == 8) || (i == 11 && j == 11)){
                    			if(k == 6){
                    				mat = Material.FENCE;
                    			} else if(k == 5){
                    				mat = Material.GLOWSTONE;
                    			}
                    		}
                    		if((i == 7 && j == 7) || (i == 12 && j == 7) || (i == 7 && j == 12) || (i == 12 && j == 12) ||
                    				(i == 6 && j == 7) || (i == 13 && j == 7) || (i == 6 && j == 12) || (i == 13 && j == 12) ||
                    				(j == 6 && i == 7) || (j == 13 && i == 7) || (j == 6 && i == 12) || (j == 13 && i == 12)){
                    			mat = Material.IRON_FENCE;
                    		}
                    	}
                    }
                    world.getBlockAt(x0 + i, y0 + k, z0 + j).setType(mat);
                }
            }
        }

        Material mat = Material.WOOL; //red wool
        String type = "GAME_TYPE";
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 3, mat, BlockFace.SOUTH, type, "RTS");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 6, mat, BlockFace.SOUTH, type, "SPLEEF");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 9, mat, BlockFace.SOUTH, type, "VANILLA");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 12, mat, BlockFace.SOUTH, type, "ANYTHING_GOES");
        placeSwitch(world, x0 + 2, y0 + 2, z0 + 15, mat, BlockFace.SOUTH, type, "PITFALL");
        placeSwitch(world, x0 + 4, y0 + 2, z0 + 17, mat, BlockFace.EAST, type, "CTF");
        placeSwitch(world, x0 + 7, y0 + 2, z0 + 17, mat, BlockFace.EAST, type, "DOMINATION");
        placeSwitch(world, x0 + 10, y0 + 2, z0 + 17, mat, BlockFace.EAST, type, "TDM");
        placeSwitch(world, x0 + 13, y0 + 2, z0 + 17, mat, BlockFace.EAST, type, "ASSAULT");
        placeSwitch(world, x0 + 16, y0 + 2, z0 + 17, mat, BlockFace.EAST, type, "CUSTOM");

        mat = Material.WOOL; //yellow wool
        type = "TEAM_TYPE";
        placeSwitch(world, x0 + 5, y0 + 2, z0 + 2, mat, BlockFace.WEST, type, "ONE_V_ONE");
        placeSwitch(world, x0 + 8, y0 + 2, z0 + 2, mat, BlockFace.WEST, type, "TWO_V_TWO");
        placeSwitch(world, x0 + 11, y0 + 2, z0 + 2, mat, BlockFace.WEST, type, "THREE_FFA");
        placeSwitch(world, x0 + 14, y0 + 2, z0 + 2, mat, BlockFace.WEST, type, "FOUR_FFA");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 5, mat, BlockFace.NORTH, type, "THREE_V_THREE");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 8, mat, BlockFace.NORTH, type, "MOSH_PIT");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 11, mat, BlockFace.NORTH, type, "BIG_TEAM");
        placeSwitch(world, x0 + 17, y0 + 2, z0 + 14, mat, BlockFace.NORTH, type, "SOLO");

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

    @Override
    public HeroClass getHeroClass() {
        return null;
    }

    @Override
    public HeroClass getProf() {
        return null;
    }

    public void placeSwitch(World world, int x, int y, int z, Material mat, BlockFace bf, String type, String name){
    	byte bfByte = 0;
    	if(bf == BlockFace.EAST){ //north
    	    bfByte = 0x4;
    	} else if(bf == BlockFace.WEST){ //south
    	    bfByte = 0x3;
    	} else if(bf == BlockFace.SOUTH){ //east
    	    bfByte = 0x1;
    	} else if(bf == BlockFace.NORTH){ //west
    	    bfByte = 0x2;
    	}
        world.getBlockAt(x, y, z).setType(mat);
        world.getBlockAt(x, y, z).getRelative(BlockFace.UP).setType(mat);
        Lever lever = new Lever(Material.LEVER);
        lever.setFacingDirection(bf);
        lever.setData(bfByte);
        world.getBlockAt(x, y, z).getRelative(bf).setType(lever.getItemType());
        world.getBlockAt(x, y, z).getRelative(bf).setData(lever.getData());
        if(bf == BlockFace.EAST){ //north
            bfByte = 0x2;
        } else if(bf == BlockFace.WEST){ //south
            bfByte = 0x3;
        } else if(bf == BlockFace.SOUTH){ //east
            bfByte = 0x5;
        } else if(bf == BlockFace.NORTH){ //west
            bfByte = 0x4;
        }
        org.bukkit.material.Sign mSign = new org.bukkit.material.Sign();
        mSign.setFacingDirection(bf);
        mSign.setData(bfByte);
        world.getBlockAt(x, y, z).getRelative(BlockFace.UP).getRelative(bf).setType(Material.WALL_SIGN);
        world.getBlockAt(x, y, z).getRelative(BlockFace.UP).getRelative(bf).setData(bfByte);
        Sign sign = (Sign)world.getBlockAt(x, y, z).getRelative(BlockFace.UP).getRelative(bf).getState();
        sign.setLine(0, type);
        sign.setLine(1, name);
        sign.setLine(2, "[OFF]");
        sign.update(true);
    }
}
package redcastlemedia.multitallented.bukkit.heromatchmaking.builder;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.Arena;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class PitfallArenaBuilder extends Arena implements Listener {
    
    public PitfallArenaBuilder(HeroMatchMaking controller) {
        super(controller);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack is = p.getItemInHand();
        int hp = 0;
        switch (is.getType()) {
            case SAPLING:
                hp = 1;
                break;
            case APPLE:
                hp = 2;
                break;
            default:
                return;
        }
        User u = getPlugin().getUserManager().getUser(p.getName());
        if (u.getMatch() == null || !(u.getMatch().getArena() instanceof PitfallArenaBuilder)) {
            return;
        }
        if (is.getAmount() == 1) {
            p.getInventory().removeItem(is);
        } else {
            is.setAmount(is.getAmount());
        }
        if (HeroMatchMaking.heroes == null) {
            p.setHealth(p.getHealth() + hp);
        } else {
            Hero hero = HeroMatchMaking.heroes.getCharacterManager().getHero(p);
            hero.setHealth(hp + hero.getHealth());
        }
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
        return GameType.PITFALL;
    }

    @Override
    public boolean hasDamage() {
        return true;
    }

    @Override
    public boolean hasFriendlyFire() {
        return true;
    }

    @Override
    public ArrayList<ItemStack> getStartingItems() {
        ArrayList<ItemStack> tempMap = new ArrayList<>();
        tempMap.add(new ItemStack(Material.DIAMOND_HELMET, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
        tempMap.add(new ItemStack(Material.DIAMOND_BOOTS, 1));
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
            for (int y=0; y<100; y++) {
                for (int z=0; z<21; z++) {
                    Material mat = Material.AIR;
                    if (y == 0 || y == 99 || x == 0 || x==20 || z==0 || z==20) {
                        mat = Material.BEDROCK;
                    } else if (y == 1) {
                        mat = Material.STATIONARY_LAVA;
                    } else if (y < 91) {
                        mat = Material.LEAVES;
                    } else if (y == 98) {
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
            return new Location(l.getWorld(), l.getX() + 5, l.getY() + 91.5, l.getZ() + 5);
        } else if (i==1) {
            return new Location(l.getWorld(), l.getX() + 15, l.getY() + 91.5, l.getZ() + 15);
        } else if (i==2) {
            return new Location(l.getWorld(), l.getX() + 5, l.getY() + 91.5, l.getZ() + 15);
        } else if (i==3) {
            return new Location(l.getWorld(), l.getX() + 15, l.getY() + 91.5, l.getZ() + 5);
        } else if (i==4) {
            return new Location(l.getWorld(), l.getX() + 10, l.getY() + 91.5, l.getZ() + 5);
        } else if (i==5) {
            return new Location(l.getWorld(), l.getX() + 10, l.getY() + 91.5, l.getZ() + 15);
        } else {
            return new Location(l.getWorld(), l.getX() + 5, l.getY() + 91.5, l.getZ() + 5);
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
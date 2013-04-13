package redcastlemedia.multitallented.bukkit.heromatchmaking.builder;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.HashSet;
import org.bukkit.Bukkit;
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

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getHealth() > 19) {
            return;
        }
        ItemStack is = p.getItemInHand();
        int hp;
        ItemStack iss;
        switch (is.getType()) {
            case SAPLING:
                hp = 1;
                iss=new ItemStack(Material.SAPLING, 1);
                break;
            case APPLE:
                hp = 2;
                iss=new ItemStack(Material.APPLE, 1);
                break;
            default:
                return;
        }
        User u = getPlugin().getUserManager().getUser(p.getName());
        if (u.getMatch() == null || !(u.getMatch().getArena() instanceof PitfallArenaBuilder)) {
            return;
        }
        p.getInventory().removeItem(iss);
        p.updateInventory();
        if (p.getHealth() + hp < p.getMaxHealth()) {
            p.setHealth(p.getHealth() + hp);
        } else {
            p.setHealth(p.getMaxHealth());
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
        return false;
    }

    @Override
    public boolean hasFriendlyFire() {
        return false;
    }

    @Override
    public ArrayList<ItemStack> getStartingItems() {
        return new ArrayList<>();
    }

    @Override
    public void build() {
        super.loadChunks();
        Location loc = super.getLocation();
        Location l = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        final World world = l.getWorld();
        final int x0 = (int) l.getX();
        final int y0 = (int) l.getY();
        final int z0 = (int) l.getZ();
        
        for (int x=0; x<21;x++) {
            for (int y=0; y<21; y++) {
                for (int z=0; z<21; z++) {
                    Material mat = Material.AIR;
                    if (y == 0 || x == 0 || x==20 || z==0 || z==20) {
                        mat = Material.BEDROCK;
                    } else if (y == 1) {
                        mat = Material.STATIONARY_LAVA;
                    } else if (y < 91) {
                        mat = Material.LEAVES;
                    }
                    
                    
                    world.getBlockAt(x0 + x, y0 + y, z0 + z).setType(mat);
                }
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            
            public void run() {
                for (int x=0; x<21;x++) {
                    for (int y=21; y<42; y++) {
                        for (int z=0; z<21; z++) {
                            Material mat = Material.AIR;
                            if (x == 0 || x==20 || z==0 || z==20) {
                                mat = Material.BEDROCK;
                            } else if (y < 91) {
                                mat = Material.LEAVES;
                            }


                            world.getBlockAt(x0 + x, y0 + y, z0 + z).setType(mat);
                        }
                    }
                }
            }
        },1L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            
            public void run() {
                for (int x=0; x<21;x++) {
                    for (int y=42; y<63; y++) {
                        for (int z=0; z<21; z++) {
                            Material mat = Material.AIR;
                            if (x == 0 || x==20 || z==0 || z==20) {
                                mat = Material.BEDROCK;
                            } else if (y < 91) {
                                mat = Material.LEAVES;
                            }


                            world.getBlockAt(x0 + x, y0 + y, z0 + z).setType(mat);
                        }
                    }
                }
            }
        },2L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            
            public void run() {
                for (int x=0; x<21;x++) {
                    for (int y=63; y<84; y++) {
                        for (int z=0; z<21; z++) {
                            Material mat = Material.AIR;
                            if (x == 0 || x==20 || z==0 || z==20) {
                                mat = Material.BEDROCK;
                            } else if (y < 91) {
                                mat = Material.LEAVES;
                            }


                            world.getBlockAt(x0 + x, y0 + y, z0 + z).setType(mat);
                        }
                    }
                }
            }
        },3L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
            
            public void run() {
                for (int x=0; x<21;x++) {
                    for (int y=84; y<100; y++) {
                        for (int z=0; z<21; z++) {
                            Material mat = Material.AIR;
                            if (y == 99 || x == 0 || x==20 || z==0 || z==20) {
                                mat = Material.BEDROCK;
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
        },4L);
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
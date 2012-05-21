/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package redcastlemedia.multitallented.bukkit.heromatchmaking.managers;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.parts.redcastlemedia.multitallented.controllers.Controller;
import org.parts.redcastlemedia.multitallented.models.YMLProxy;
import redcastlemedia.multitallented.bukkit.heromatchmaking.builders.ArenaBuilder;
import redcastlemedia.multitallented.bukkit.heromatchmaking.builders.RTSArenaBuilder;

/**
 *
 * @author Multitallented
 */
public class ArenaManager {
    private HashMap<Integer, ArenaBuilder> arenas = new HashMap<Integer, ArenaBuilder>();
    private final World world;
    private HashMap<Player, ArenaBuilder> existingArenas = new HashMap<Player, ArenaBuilder>();
    private HashMap<Player, Location> previousLocation = new HashMap<Player, Location>();
    private HashMap<Player, ArrayList<ItemStack>> previousInventory = new HashMap<Player, ArrayList<ItemStack>>();
    private HashMap<Player, HeroClass> previousClass = new HashMap<Player, HeroClass>();
    private HashMap<Player, Integer> previousHealth = new HashMap<Player, Integer>();
    
    public ArenaManager() {
        YMLProxy config = (YMLProxy) Controller.getInstance("config");
        String worldName = config.getString("world", "world");
        World w = Bukkit.getWorld(worldName);
        this.world = w;
        Logger log = Bukkit.getLogger();
        if (w == null) {
            log.warning("[HeroMatchMaking] invalid world name in config.");
            Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("HeroMatchMaking"));
            return;
        } else {
          log.info("[HeroMatchMaking] Using " + worldName + " for instanced matches.");
          log.warning("[HeroMatchMaking] Make sure there is nothing valuable in " + worldName);
        }
    }
    
    public ArenaBuilder getExistingArena(Player p) {
        return existingArenas.get(p);
    }
    
    public void scheduleMatch(final HashSet<Player> players, ArenaBuilder ab) {
        for (Player p : players) {
            p.sendMessage(ChatColor.GOLD + "[HeroMatchMaking] Your match will begin in 5s");
        }
        
        Bukkit.getScheduler().scheduleSyncDelayedTask((JavaPlugin) Controller.getInstance("plugin"), 
                new Runnable() {

                @Override
                public void run() {
                    //TODO put players in a match
                    setupNextArena(new RTSArenaBuilder(), players);
                }
                    
                }, 100l);
        
    }
    
    public void returnPlayer(Player p) {
        Hero hero = null;
        if (previousLocation.containsKey(p)) {
            p.teleport(previousLocation.get(p));
        }
        if (previousClass.containsKey(p)) {
            hero = ((Heroes) Controller.getInstance("heroes")).getCharacterManager().getHero(p);
            hero.changeHeroClass(previousClass.get(p), false);
        }
        if (previousHealth.containsKey(p)) {
            if (hero != null) {
                hero.setHealth(previousHealth.get(p));
            } else {
                p.setHealth(previousHealth.get(p));
            }
        }
        PlayerInventory pInv = p.getInventory();
        ArrayList<ItemStack> inv = previousInventory.get(p);
        pInv.setBoots(inv.get(3));
        pInv.setHelmet(inv.get(0));
        pInv.setLeggings(inv.get(2));
        pInv.setChestplate(inv.get(1));
        for (int k = 4; k<inv.size(); k++) {
            pInv.addItem(inv.get(k));
        }
    }
    
    public void checkEndMatch(Player p) {
        ArenaBuilder ab = existingArenas.get(p);
        HashSet<HashSet<Player>> players = ab.getPlayers();
        if (players.size() > 1) {
            return;
        }
        HashSet<HashSet<Player>> tempPlayers = ab.getPlayers();
        HashSet<HashSet<Player>> destroyThese = new HashSet<HashSet<Player>>();
        for (HashSet<Player> playerSet : tempPlayers) {
            if (playerSet.contains(p)) {
                //TODO Record stats
                if (playerSet.size() < 2) {
                    destroyThese.add(playerSet);
                    break;
                } else {
                    playerSet.remove(p);
                    return;
                }
            }
        }
        for (HashSet<Player> destroy : destroyThese) {
            tempPlayers.remove(destroy);
        }
        System.out.println(tempPlayers.size());
        System.out.println(ab.getPlayers().size());
        if (tempPlayers.size() > 1) {
            return;
        }
        
        //record stats
        for (HashSet<Player> playerSet : tempPlayers) {
            for (Player pl : playerSet) {
                returnPlayer(pl);
            }
        }
        //remove arena from list and make the arena available
        
        
        //TODO end the match
        //Clear all itemstacks in arena
    }
    
    private void setupNextArena(ArenaBuilder arena, HashSet<Player> players) {
        int j=0;
        //TODO fix this to be dynamic with custom arena sizes
        for (Integer i : arenas.keySet()) {
            if (j != i) {
                break;
            }
            j++;
        }
        Location l = new Location(world, 0 + (20*j), 0, 0);
        arena.build(l);
        arenas.put(j, arena);
        HashSet<HashSet<Player>> play = new HashSet<HashSet<Player>>();
        Object h = Controller.getInstance("heroes");
        Heroes heroes = null;
        if (h!=null) {
            heroes = (Heroes) h;
        }
        int i=0;
        PlayerManager pm = (PlayerManager) Controller.getInstance("playermanager");
        for (Player p : players) {
            //TODO make this dynamic
            HashSet<Player> tempPlayers = new HashSet<Player>();
            tempPlayers.add(p);
            play.add(tempPlayers);
            //////////////////
            existingArenas.put(p, arena);
            previousLocation.put(p, p.getLocation());
            pm.putPlayerLocation(p, arena);
            if (heroes != null) {
                Hero hero = heroes.getCharacterManager().getHero(p);
                previousHealth.put(p, hero.getHealth());
                hero.setHealth(hero.getMaxHealth());
                previousClass.put(p, hero.getHeroClass());
                hero.changeHeroClass(heroes.getClassManager().getClass(((YMLProxy) Controller.getInstance("config")).getString("default-class")), false);
            } else {
                previousHealth.put(p, p.getHealth());
                p.setHealth(20);
            }
            previousInventory.put(p, copyInventory(p));
            p.getInventory().clear();
            p.updateInventory();
            
            p.teleport(arena.getStartPoint(i));
            p.sendMessage(ChatColor.GOLD + "[HeroMatchMaking] Your match has begun!");
            i++;
        }
        
        
    }
    
    private ArrayList<ItemStack> copyInventory(Player p) {
        PlayerInventory inv = p.getInventory();
        ArrayList<ItemStack> pInv = new ArrayList<ItemStack>();
        
        pInv.add(inv.getHelmet());
        pInv.add(inv.getChestplate());
        pInv.add(inv.getLeggings());
        pInv.add(inv.getBoots());
        
        pInv.addAll(Arrays.asList(inv.getContents()));
        return pInv;
    }

    public void playerRespawned(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        event.setRespawnLocation(previousLocation.get(p));
        PlayerInventory pInv = p.getInventory();
        ArrayList<ItemStack> inv = previousInventory.get(p);
        pInv.setBoots(inv.get(3));
        pInv.setHelmet(inv.get(0));
        pInv.setLeggings(inv.get(2));
        pInv.setChestplate(inv.get(1));
        for (int k = 4; k<inv.size(); k++) {
            pInv.addItem(inv.get(k));
        }
        Hero hero = null;
        if (previousClass.containsKey(p)) {
            hero = ((Heroes) Controller.getInstance("heroes")).getCharacterManager().getHero(p);
            if (hero != null) {
                hero.changeHeroClass(previousClass.get(p), false);
            }
        }
        if (previousHealth.containsKey(p)) {
            if (hero != null) {
                hero.setHealth(previousHealth.get(p));
            } else {
                p.setHealth(previousHealth.get(p));
            }
        }
    }
}

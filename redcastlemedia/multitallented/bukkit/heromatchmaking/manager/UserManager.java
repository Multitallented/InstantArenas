package redcastlemedia.multitallented.bukkit.heromatchmaking.manager;

import com.herocraftonline.heroes.characters.Hero;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redcastlemedia.multitallented.bukkit.heromatchmaking.HeroMatchMaking;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.User;

/**
 *
 * @author Multitallented
 */
public class UserManager {
    private final HeroMatchMaking controller;
    private HashMap<String, User> users = new HashMap<>();
    
    public UserManager(HeroMatchMaking controller) {
        this.controller = controller;
    }
    
    /**
     * Restores the user to their state before they entered the match
     * unless they are logging out, in which case it will teleport them
     * when they log in next time. It also erases their previous user
     * data.
     * @param u the User
     * @param state the name event that caused this method to trigger
     */
    public void restorePreviousUserState(final User u) {
        if (u.getPreviousLocation() == null) {
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(controller, new Runnable() {
            
            @Override
            public void run() {
                Logger logger = Logger.getLogger("Minecraft");
                Player p = u.getPlayer();
                p.setFireTicks(1);
                if (u.getPreviousLocation() != null) {
                    p.teleport(u.getPreviousLocation());
                    u.setPreviousLocation(null);
                }
                ArrayList<ItemStack> inventory = u.getPreviousInventory();
                if (inventory != null && inventory.size() > 3) {
                    p.getInventory().clear();
                    p.getInventory().setHelmet(inventory.get(0));
                    p.getInventory().setChestplate(inventory.get(1));
                    p.getInventory().setLeggings(inventory.get(2));
                    p.getInventory().setBoots(inventory.get(3));
                    for (int i = 4; i<inventory.size(); i++) {
                        if (inventory.get(i) != null) {
                            p.getInventory().addItem(inventory.get(i));
                        }
                    }
                }
                if (u.getPreviousStamina() > -1) {
                    p.setFoodLevel(u.getPreviousStamina());
                }
                if (HeroMatchMaking.heroes != null) {
                    Hero h = HeroMatchMaking.heroes.getCharacterManager().getHero(p);
                    if (u.getPreviousHP() > 0) {
                        h.setHealth(u.getPreviousHP());
                    } else {
                        h.setHealth(h.getMaxHealth());
                    }
                    h.syncHealth();
                    u.setPreviousHP(0);
                    if (u.getPreviousMana() > 0) {
                        h.setMana(u.getPreviousMana());
                    }
                    u.setPreviousMana(0);
                    if (u.getPreviousClass() != null) {
                        h.setHeroClass(u.getPreviousClass(), false);
                    }
                    u.setPreviousClass(null);
                    if (u.getPreviousProf() != null) {
                        h.setHeroClass(u.getPreviousProf(), true);
                    }
                    u.setPreviousProf(null);
                } else {
                    if (u.getPreviousHP() > 0) {
                        p.setHealth(u.getPreviousHP());
                    } else {
                        p.setHealth(20);
                    }
                    u.setPreviousHP(0);
                    if (u.getPreviousExp() > 0) {
                        p.setExp(u.getPreviousExp());
                    }
                    u.setPreviousExp(0f);
                }
            }
        });
        
    }
    
    public void saveUserPreviousState(User u) {
        Player p = u.getPlayer();
        Location l = p.getLocation();
        u.setPreviousLocation(l.getWorld().getName() + ";" + l.getX() + ";" + l.getY() + ";" + l.getZ());
        ArrayList<ItemStack> inventory = new ArrayList<>();
        inventory.add(p.getInventory().getHelmet());
        inventory.add(p.getInventory().getChestplate());
        inventory.add(p.getInventory().getLeggings());
        inventory.add(p.getInventory().getBoots());
        inventory.addAll(Arrays.asList(p.getInventory().getContents()));
        u.setPreviousInventory(inventory);
        u.setPreviousStamina(p.getFoodLevel());
        if (HeroMatchMaking.heroes != null) {
            Hero h = HeroMatchMaking.heroes.getCharacterManager().getHero(p);
            u.setPreviousHP(h.getHealth());
            u.setPreviousMana(h.getMana());
            u.setPreviousClass(h.getHeroClass());
            u.setPreviousProf(h.getSecondClass());
        } else {
            u.setPreviousHP(p.getHealth());
            u.setPreviousExp(p.getExp());
        }

        saveUserData(p.getName());
    }
    
    public void loadUserData(String name) {
        File dataFolder = new File(controller.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        } else {
            File f = new File(dataFolder, name + ".yml");
            if (!f.exists()) {
                saveUserData(name);
            }
            FileConfiguration config = new YamlConfiguration();
            try {
                config.load(f);
                Player p = Bukkit.getPlayer(name);
                users.put(f.getName().replace(".yml", ""),
                        new User(f.getName().replace(".yml", ""),
                        config.getInt("wins"), config.getInt("loses"),
                        getGTypeList(config.getStringList("game-types")),
                        getTTypeList(config.getStringList("team-types")), p,
                        config.getString("previous-location", null)));
            } catch (Exception e) {
                failedLoad(f.getName());
            }
        }
    }
    
    private void failedLoad(String name) {
        Logger logger = Logger.getLogger("Minecraft");
        String message = HeroMatchMaking.NAME + " failed to load " + name;
        logger.severe(message);
    }
    
    private List<GameType> getGTypeList(List<String> input) {
        List<GameType> tempList = new ArrayList<>();
        for (String s : input) {
            tempList.add(GameType.valueOf(s));
        }
        return tempList;
    }
    
    private List<TeamType> getTTypeList(List<String> input) {
        List<TeamType> tempList = new ArrayList<>();
        for (String s : input) {
            tempList.add(TeamType.valueOf(s));
        }
        return tempList;
    }
    
    public void saveUserData() {
        File dataFolder = new File(controller.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        for (String s : users.keySet()) {
            saveUserData(s);
        }
    }
    public void removeUser(User u) {
        users.remove(u.getName());
    }
    
    public void saveUserData(String name) {
        File userFile = new File(new File(controller.getDataFolder(), "data"), name + ".yml");
        try {
            if (!userFile.exists()) {
                userFile.createNewFile();
            }
            FileConfiguration config = new YamlConfiguration();
            config.load(userFile);
            
            User u;
            if (users.containsKey(name)) {
                u = users.get(name);
            } else {
                List<GameType> gTypes = new ArrayList<>();
                List<TeamType> tTypes = new ArrayList<>();
                u = new User(name, 0, 0, gTypes, tTypes, Bukkit.getPlayer(name));
            }

            config.set("wins", u.getWins());
            config.set("loses", u.getLoses());
            config.set("game-types", u.getStringGType());
            config.set("team-types", u.getStringTType());
            if (u.getPreviousLocation() != null) {
                config.set("previous-location", u.getRawPreviousLocation());
            }

            config.save(userFile);
        } catch (Exception e) {
            Logger log = Logger.getLogger("Minecraft");
            String message = HeroMatchMaking.NAME + " failed to save " + name + ".yml";
            log.severe(message);
        }
    }
    
    public User getUser(String name) {
        if (users.get(name) != null) {
        } else {
            loadUserData(name);
        }
        return users.get(name);
    }
}

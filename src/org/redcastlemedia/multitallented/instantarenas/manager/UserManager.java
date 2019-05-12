package org.redcastlemedia.multitallented.instantarenas.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.redcastlemedia.multitallented.instantarenas.InstantArenas;
import org.redcastlemedia.multitallented.instantarenas.model.GameType;
import org.redcastlemedia.multitallented.instantarenas.model.TeamType;
import org.redcastlemedia.multitallented.instantarenas.model.User;

/**
 *
 * @author Multitallented
 */
public class UserManager {
    private HashMap<UUID, User> users = new HashMap<>();

    private static UserManager instance = null;

    public static UserManager getInstance() {
        if (instance == null) {
            new UserManager();
        }
        return instance;
    }

    public UserManager() {
        instance = this;
    }
    
    /**
     * Restores the user to their state before they entered the match
     * unless they are logging out, in which case it will teleport them
     * when they log in next time. It also erases their previous user
     * data.
     * @param u the User
     */
    public void restorePreviousUserState(final User u) {
        final UserManager um = this;
        if (u.getPreviousLocation() == null) {
            return;
        }
        final Player p = Bukkit.getPlayer(u.getUuid());
        if (u.getPreviousStamina() > -1) {
            p.setFoodLevel(u.getPreviousStamina());
        }

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
        Bukkit.getScheduler().scheduleSyncDelayedTask(InstantArenas.getInstance(), new Runnable() {
            
            @Override
            public void run() {
                p.setFireTicks(1);
                if (u.getPreviousLocation() != null) {
                    Location prevLocation = u.getPreviousLocation();
                    if (!prevLocation.getChunk().isLoaded()) {
                        prevLocation.getChunk().load(true);
                    }
                    p.teleport(prevLocation);
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
                um.saveUserData(u.getUuid());
            }
        });
        
    }
    
    public void restoreLoggingOutUser(final User u) {
        final UserManager um = this;
        if (u.getPreviousLocation() == null) {
            return;
        }
        Player p = Bukkit.getPlayer(u.getUuid());
        p.setFireTicks(1);
        if (u.getPreviousLocation() != null) {
            Location prevLocation = u.getPreviousLocation();
            if (!prevLocation.getChunk().isLoaded()) {
                prevLocation.getChunk().load(true);
            }
            p.teleport(prevLocation);
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

        um.saveUserData(u.getUuid());
        
    }
    
    public void saveUserPreviousState(User u) {
        Player p = Bukkit.getPlayer(u.getUuid());
        Location l = p.getLocation();
        u.setPreviousLocation(l.getWorld().getUID() + ";" + l.getX() + ";" + l.getY() + ";" + l.getZ());
        ArrayList<ItemStack> inventory = new ArrayList<>();
        inventory.add(p.getInventory().getHelmet());
        inventory.add(p.getInventory().getChestplate());
        inventory.add(p.getInventory().getLeggings());
        inventory.add(p.getInventory().getBoots());
        inventory.addAll(Arrays.asList(p.getInventory().getContents()));
        u.setPreviousInventory(inventory);
        u.setPreviousStamina(p.getFoodLevel());
        u.setPreviousHP(p.getHealth());
        u.setPreviousExp(p.getExp());

        saveUserData(p.getUniqueId());
    }
    
    
    
    public void loadUserData(UUID uuid) {
        File dataFolder = new File(InstantArenas.getInstance().getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        } else {
            File f = new File(dataFolder, uuid + ".yml");
            if (!f.exists()) {
                saveUserData(uuid);
            }
            FileConfiguration config = new YamlConfiguration();
            try {
                config.load(f);
                Player p = Bukkit.getPlayer(uuid);
                users.put(uuid,
                        new User(uuid,
                        config.getInt("wins"), config.getInt("loses"),
                        getGTypeList(config.getStringList("game-types")),
                        getTTypeList(config.getStringList("team-types")),
                        config.getString("previous-location", null)));
            } catch (Exception e) {
                failedLoad(f.getName());
            }
        }
    }
    
    private void failedLoad(String name) {
        Logger logger = Logger.getLogger("Minecraft");
        String message = InstantArenas.NAME + " failed to load " + name;
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
        File dataFolder = new File(InstantArenas.getInstance().getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        for (UUID s : users.keySet()) {
            saveUserData(s);
        }
    }
    public void removeUser(User u) {
        users.remove(u.getUuid());
    }
    
    public void saveUserData(UUID uuid) {
        File userFile = new File(new File(InstantArenas.getInstance().getDataFolder(), "data"), uuid + ".yml");
        try {
            if (!userFile.exists()) {
                userFile.createNewFile();
            }
            FileConfiguration config = new YamlConfiguration();
            config.load(userFile);
            
            User u;
            if (users.containsKey(uuid)) {
                u = users.get(uuid);
            } else {
                List<GameType> gTypes = new ArrayList<>();
                gTypes.add(GameType.RTS);
                List<TeamType> tTypes = new ArrayList<>();
                tTypes.add(TeamType.ONE_V_ONE);
                u = new User(uuid, 0, 0, gTypes, tTypes);
            }

            config.set("wins", u.getWins());
            config.set("loses", u.getLoses());
            config.set("game-types", u.getStringGType());
            config.set("team-types", u.getStringTType());
            if (u.getPreviousLocation() != null) {
                config.set("previous-location", u.getRawPreviousLocation());
            } else {
                config.set("previous-location", "");
            }

            config.save(userFile);
        } catch (Exception e) {
            Logger log = Logger.getLogger("Minecraft");
            String message = InstantArenas.NAME + " failed to save " + uuid + ".yml";
            log.severe(message);
        }
    }
    
    public User getUser(UUID uuid) {
        if (users.get(uuid) != null) {
        } else {
            loadUserData(uuid);
        }
        return users.get(uuid);
    }
}

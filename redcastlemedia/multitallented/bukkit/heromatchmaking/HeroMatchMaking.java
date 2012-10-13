package redcastlemedia.multitallented.bukkit.heromatchmaking;

import com.herocraftonline.heroes.Heroes;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import redcastlemedia.multitallented.bukkit.heromatchmaking.listener.HMMListener;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.ArenaManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.ConfigManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.MatchManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.manager.UserManager;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.GameType;
import redcastlemedia.multitallented.bukkit.heromatchmaking.model.TeamType;

/**
 *
 * @author Multitallented
 */
public class HeroMatchMaking extends JavaPlugin {
    public static Permission perm = null;
    public static Economy econ = null;
    public static Heroes heroes = null;
    private ConfigManager configManager;
    public static final String NAME = "[HeroMatchMaking]";
    private UserManager userManager;
    private HMMListener listener;
    private MatchManager matchManager;
    private ArenaManager arenaManager;
    
    
    @Override
    public void onEnable() {
        new InitOrder(this);
    }
    @Override
    public void onDisable() {
        new DisableOrder(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            String allArgs = "";
            boolean first = true;
            for (String s : args) {
                if (first) {
                    first = false;
                    allArgs += s;
                } else {
                    allArgs += " " + s;
                }
            }
            if (args[0].equalsIgnoreCase("q") || args[0].equalsIgnoreCase("queue")) {
                new QueueOrder(this, sender);
            } else {
                String test = allArgs.toUpperCase().replaceAll(" ", "_");
                System.out.println(test);
                try {
                    if (TeamType.valueOf(test) != null) {
                        new PreferenceOrder(this, sender, test);
                    }
                } catch (IllegalArgumentException iae) {
                    
                }
                try {
                    if (GameType.valueOf(test) != null) {
                        new PreferenceOrder(this, sender, test);
                    }
                } catch (IllegalArgumentException iae) {
                    
                }
            }
            
        } catch (NullPointerException npe) {
            sender.sendMessage(ChatColor.RED + HeroMatchMaking.NAME + " Unrecognized command " + label);
            return true;
        }
        return true;
    }
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    public UserManager getUserManager() {
        return this.userManager;
    }
    public MatchManager getMatchManager() {
        return this.matchManager;
    }
    public ArenaManager getArenaManager() {
        return this.arenaManager;
    }
    protected void setConfigManager(ConfigManager config) {
        this.configManager = config;
    }
    protected void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    protected void setListener(HMMListener listener) {
        this.listener = listener;
    }
    protected void setMatchManager(MatchManager matchManager) {
        this.matchManager = matchManager;
    }
    protected void setArenaManager(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }
}

package redcastlemedia.multitallented.bukkit.heromatchmaking.model;

import com.herocraftonline.heroes.characters.classes.HeroClass;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Multitallented
 */
public class User {
    private final String name;
    private int wins;
    private int loses;
    private List<GameType> gType;
    private List<TeamType> tType;
    private Match match = null;
    private final Player player;
    private ArrayList<ItemStack> previousInventory = null;
    private int previousHP = 0;
    private int previousStamina = -1;
    private float previousEXP = 0;
    private String previousLocation = null;
    private HeroClass previousClass = null;
    private HeroClass previousProf = null;
    private int previousMana = 0;
    
    public User(String name, int wins, int loses, List<GameType> gType, List<TeamType> tType, Player player) {
        this.name = name;
        this.wins = wins;
        this.loses = loses;
        this.gType = gType;
        this.tType = tType;
        this.player = player;
    }
    public User(String name, int wins, int loses, List<GameType> gType, List<TeamType> tType, Player player, String l) {
        this.name = name;
        this.wins = wins;
        this.loses = loses;
        this.gType = gType;
        this.tType = tType;
        this.player = player;
        this.previousLocation = l;
    }
    public int getPreviousMana() {
        return this.previousMana;
    }
    public void setPreviousMana(int input) {
        this.previousMana = input;
    }
    public void setPreviousProf(HeroClass hc) {
        this.previousProf = hc;
    }
    public HeroClass getPreviousProf() {
        return this.previousProf;
    }
    public void setPreviousClass(HeroClass hc) {
        this.previousClass = hc;
    }
    public HeroClass getPreviousClass() {
        return this.previousClass;
    }
    
    public Location getPreviousLocation() {
        if (previousLocation == null) {
            return null;
        }
        String[] locations = previousLocation.split(";");
        return new Location(Bukkit.getWorld(locations[0]), Double.parseDouble(locations[1]), Double.parseDouble(locations[2]), Double.parseDouble(locations[3]));
    }
    public String getRawPreviousLocation() {
        return this.previousLocation;
    }
    public void setPreviousLocation(String input) {
        this.previousLocation = input;
    }
    
    public float getPreviousExp() {
        return this.previousEXP;
    }
    public void setPreviousExp(float input) {
        this.previousEXP = input;
    }
    
    public void setPreviousStamina(int input) {
        this.previousStamina = input;
    }
    public int getPreviousStamina() {
        return this.previousStamina;
    }
    
    public void setPreviousHP(int hp) {
        this.previousHP = hp;
    }
    public int getPreviousHP() {
        return this.previousHP;
    }
    
    public void setPreviousInventory(ArrayList<ItemStack> input) {
        this.previousInventory = input;
    }
    public ArrayList<ItemStack> getPreviousInventory() {
        return this.previousInventory;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public String getName() {
        return name;
    }
    
    public int getWins() {
        return wins;
    }
    
    public void setWins(int newWins) {
        this.wins = newWins;
    }
    
    public int getLoses() {
        return loses;
    }
    
    public void setLoses(int newLoses) {
        this.loses = newLoses;
    }
    public List<String> getStringGType() {
        List<String> tempList = new ArrayList<>();
        for (GameType gTypes : this.gType) {
            tempList.add(gTypes.name());
        }
        return tempList;
    }
    public List<GameType> getGType() {
        return gType;
    }
    
    public void setGType(List<GameType> newGType) {
        this.gType = newGType;
    }

    public List<String> getStringTType() {
        List<String> tempList = new ArrayList<>();
        for (TeamType tTypes : this.tType) {
            tempList.add(tTypes.name());
        }
        return tempList;
    }
    public List<TeamType> getTType() {
        return tType;
    }
    
    public void setTType(List<TeamType> newTType) {
        this.tType = newTType;
    }
    
    public Match getMatch() {
        return match;
    }
    
    public void setMatch(Match newMatch) {
        this.match = newMatch;
    }
}

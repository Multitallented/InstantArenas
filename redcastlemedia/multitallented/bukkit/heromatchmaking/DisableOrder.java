package redcastlemedia.multitallented.bukkit.heromatchmaking;

/**
 *
 * @author Multitallented
 */
public class DisableOrder {
    public DisableOrder(HeroMatchMaking hmm) {
        hmm.getUserManager().saveUserData();
    }
}

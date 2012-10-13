package redcastlemedia.multitallented.bukkit.heromatchmaking;

/**
 *
 * @author Multitallented
 */
public class DisableOrder {
    public DisableOrder(HeroMatchMaking hmm) {
        //TODO disable option in user GameType or TeamType
        hmm.getUserManager().saveUserData();
    }
}

package org.redcastlemedia.multitallented.instantarenas.order;

import org.redcastlemedia.multitallented.instantarenas.manager.UserManager;

/**
 *
 * @author Multitallented
 */
public class DisableOrder {
    public DisableOrder() {
        UserManager.getInstance().saveUserData();
    }
}

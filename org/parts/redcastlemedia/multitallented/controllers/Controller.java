/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parts.redcastlemedia.multitallented.controllers;

import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Multitallented
 */
public class Controller {
    private static final HashMap<String, Object> instances = new HashMap<String, Object>();
    
    public Controller(JavaPlugin plugin) {
        instances.put("plugin", plugin);
    }
    
    public static Object getInstance(String name) {
        return instances.get(name);
    }
    
    public static void addInstance(String name, Object ret) {
        instances.put(name, ret);
    }
    
    public static void callOrder(Order o) {
        o = o.send();
        if (!o.isCancelled()) {
            o.exec();
        }
    }
    
    public void init() {
        Manager im = (Manager) Controller.getInstance("initmanager");
        callOrder(im.build());
    }
}

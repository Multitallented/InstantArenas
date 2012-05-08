package org.parts.redcastlemedia.multitallented.models;

import java.util.HashMap;
import org.parts.redcastlemedia.multitallented.controllers.Controller;

/**
 *
 * @author Multitallented
 */
public class Model {
    public static HashMap<String, YMLProxy> proxies = new HashMap<String, YMLProxy>();
    
    public Model() {
        Controller.addInstance("model", this);
    }
    
    public static YMLProxy getProxy(String name) {
        return proxies.get(name);
    }
    
    public static void addProxy(String name, YMLProxy proxy) {
        proxies.put(name, proxy);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.parts.redcastlemedia.multitallented.controllers;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Multitallented
 */
public abstract class Order {
    public String name = null;
    public boolean cancelled = false;
    public HashSet<OrderListener> listeners = new HashSet<OrderListener>();
    private final boolean cancellable;
    private ArrayList<Object> info;
    
    public Order(String name, boolean cancellable) {
        this.name=name;
        this.cancellable = cancellable;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract void exec();
    
    public boolean addListener(OrderListener ol) {
        return listeners.add(ol);
    }
    
    public boolean hasListener(OrderListener ol) {
        return listeners.contains(ol);
    }
    
    public boolean removeListener(OrderListener ol) {
        return listeners.remove(ol);
    }
    
    public Order send() {
        Order o = this;
        for (OrderListener ol : listeners) {
            o = ol.onOrder(o);
        }
        return o;
    }
    
    public void setCancelled(boolean cancel) {
        if (cancellable) {
            this.cancelled = cancel;
        }
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
}

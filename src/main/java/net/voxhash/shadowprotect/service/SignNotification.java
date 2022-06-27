package net.voxhash.shadowprotect.service;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SignNotification {
    private Player player;
    private String[] message;
    private Location location;
    
    public SignNotification(Player player, String[] message, Location location) {
        this.player = player;
        this.message = message;
        this.location = location;
    }

    public Player getPlayer() {
        return player;
    }

    public String[] getMessage() {
        return message;
    }

    public Location getLocation() {
        return location;
    }

    public void destroy() {
        getLocation().getBlock().breakNaturally();
    }
}

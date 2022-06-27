package net.voxhash.shadowprotect.manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.service.SignSpyService;

public class SignSpyServiceManager {

    private HashMap<Player, SignSpyService> services = new HashMap<>();
    
    public SignSpyServiceManager() {

    }

    public SignSpyService getSignSpyService(Player player) {
        if (!services.containsKey(player)) {
            services.put(player, new SignSpyService(player));
        }
        return services.get(player);
    }

    public void removeSignSpyService(Player player) {
        services.remove(player);
    }

    public void clearSignSpyServices() {
        services.clear();
    }
    
}

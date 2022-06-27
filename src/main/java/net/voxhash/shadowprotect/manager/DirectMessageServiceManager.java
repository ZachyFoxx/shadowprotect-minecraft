package net.voxhash.shadowprotect.manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.service.DirectMessageService;

public class DirectMessageServiceManager {
    private HashMap<Player, DirectMessageService> services = new HashMap<>();
    
    public DirectMessageServiceManager() {

    }

    public DirectMessageService getDirectMessageService(Player player) {
        if (!services.containsKey(player)) {
            services.put(player, new DirectMessageService(player));
        }
        return services.get(player);
    }

    public void removeDirectMessageService(Player player) {
        services.remove(player);
    }
}

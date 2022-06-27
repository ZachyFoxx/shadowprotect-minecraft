package net.voxhash.shadowprotect.manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.service.MessageService;

public class MessageServiceManager {

    private HashMap<Player, MessageService> services = new HashMap<>();
    
    public MessageServiceManager() {
        
    }

    public MessageService getMessageService(Player player) {
        if (!services.containsKey(player)) {
            services.put(player, new MessageService(player));
        }
        return services.get(player);
    }

    public void removeMessageService(Player player) {
        services.remove(player);
    }

    public void clearMessageServices() {
        services.clear();
    }
}

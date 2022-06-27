package net.voxhash.shadowprotect.manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.service.StaffChatService;

public class StaffChatServiceManager {
        
        private HashMap<Player, StaffChatService> services = new HashMap<>();
        
        public StaffChatServiceManager() {
            
        }
        
        public StaffChatService getStaffChatService(Player player) {
            if (!services.containsKey(player)) {
                services.put(player, new StaffChatService(player));
            }
            return services.get(player);
        }
        
        public void removeStaffChatService(Player player) {
            services.remove(player);
        }
        
        public void clearStaffChatServices() {
            services.clear();
        }
}

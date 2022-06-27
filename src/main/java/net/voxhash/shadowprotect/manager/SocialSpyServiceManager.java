package net.voxhash.shadowprotect.manager;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.service.SocialSpyService;

public class SocialSpyServiceManager {
        
        private HashMap<Player, SocialSpyService> services = new HashMap<>();
        
        public SocialSpyServiceManager() {
            
        }
        
        public SocialSpyService getSocialSpyService(Player player) {
            if (!services.containsKey(player)) {
                services.put(player, new SocialSpyService(player));
            }
            return services.get(player);
        }
        
        public void removeSocialSpyService(Player player) {
            services.remove(player);
        }
        
        public void clearSocialSpyServices() {
            services.clear();
        }
}

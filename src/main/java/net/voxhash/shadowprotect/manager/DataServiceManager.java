package net.voxhash.shadowprotect.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.service.DataService;

public class DataServiceManager {

    private HashMap<Player, DataService> services = new HashMap<>();

    public DataServiceManager() {

    }

    public List<DataService> getDataServices() {
        return new ArrayList<>(services.values());
    }

    public DataService getDataService(Player player) {
        if (!services.containsKey(player)) {
            services.put(player, new DataService(player));
        }
        return services.get(player);
    }

    public void removeDataService(Player player) {
        services.remove(player);
    }

    public void clearDataServices() {
        services.clear();
    }
}

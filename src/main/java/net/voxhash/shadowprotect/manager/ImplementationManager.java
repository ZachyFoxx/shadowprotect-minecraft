package net.voxhash.shadowprotect.manager;

import java.util.HashMap;

import org.bukkit.Bukkit;

import net.voxhash.shadowprotect.ShadowProtect;

@SuppressWarnings({"unchecked"})
public class ImplementationManager {

    ShadowProtect plugin;
    HashMap<Class<?>, Object> implementations = new HashMap<Class<?>, Object>();
    
    public ImplementationManager(ShadowProtect plugin) {
        this.plugin = plugin;
    }

    public void loadImplementationsIfExists(String ...plugins) {
        for (String plugin : plugins) {
            if (Bukkit.getPluginManager().getPlugin(plugin) != null) {
                this.implementations.put(Bukkit.getPluginManager().getPlugin(plugin).getClass(), Bukkit.getPluginManager().getPlugin(plugin));
            }
        }
    }

    public boolean exists(String name) {
        return plugin.getServer().getPluginManager().getPlugin(name) != null;
    }

    public <T> T get(String plugin) {
        return (T) this.plugin.getServer().getPluginManager().getPlugin(plugin);
    }
}

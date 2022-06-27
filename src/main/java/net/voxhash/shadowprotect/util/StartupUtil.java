package net.voxhash.shadowprotect.util;

import org.bukkit.plugin.java.JavaPlugin;

public class StartupUtil {

    // prevent instantiation
    private StartupUtil() {
    }

    public static boolean setupConfig(JavaPlugin plugin) {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getLogger().info("Error: No folder was found! Creating...");
                if (!plugin.getDataFolder().mkdirs()) {
                    plugin.getLogger().info("Error: Unable to create data folder, are your file permissions correct?");
                    return false;
                }
                plugin.saveDefaultConfig();
                plugin.saveConfig();
                plugin.getLogger().info("The folder was created successfully!");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

package net.voxhash.shadowprotect.listener;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Permissions;

public class PlayerCommandPreProcessListener implements Config, Format, Listener {
    
    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission(Permissions.COMMAND_BYPASS))
            return;

        String command = event.getMessage().split(" ")[0];
        if (General.COMMANDS_TO_DISABLE.contains(command.toLowerCase().substring(1))) {
            File bukkitFile = new File(getPlugin().getServer().getWorldContainer().getAbsolutePath().replace(".", "spigot.yml"));
            FileConfiguration bukkit = YamlConfiguration.loadConfiguration(bukkitFile);
            System.out.println(bukkitFile.getAbsolutePath());
            event.getPlayer().sendMessage(bukkit.getString("messages.unknown-command", "Unknown command. Type \"/help\" for help."));
            event.setCancelled(true);
        }
        
        if (Config.Social_Spy.ENABLED_COMMANDS.contains(command.toLowerCase().substring(1))) {
            getPlugin().socialSpyServiceManager.getSocialSpyService(event.getPlayer()).sendSocialSpyMessage(event.getMessage());
        }
    }
}

package net.voxhash.shadowprotect.locale;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LocaleConfiguration {

    FileConfiguration config;
    YamlConfiguration yamlConfig;
    
    public LocaleConfiguration(String file) {
        // this.yamlConfig = YamlConfiguration.loadConfiguration(file);
        this.config = this.yamlConfig;
    }
}

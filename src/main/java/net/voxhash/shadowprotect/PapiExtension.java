package net.voxhash.shadowprotect;

import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PapiExtension extends PlaceholderExpansion {

    private JavaPlugin retarded = ShadowProtect.getPlugin(ShadowProtect.class);

    public PapiExtension(){}

    @Override
    public String getIdentifier() {
        return "shadowprotect";
    }

    @Override
    public String getAuthor() {
        return "VoxHash";
    }

    @Override
    public String getVersion() {
        return retarded.getDescription().getVersion();
    }    
}

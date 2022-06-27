package net.voxhash.shadowprotect.util;

import net.voxhash.shadowprotect.ShadowProtect;

public interface WithPlugin {
    public default ShadowProtect getPlugin() {
        return ShadowProtect.getPlugin(ShadowProtect.class);
    }    
}

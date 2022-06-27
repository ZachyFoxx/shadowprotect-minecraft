package net.voxhash.shadowprotect.util;

import java.util.HashMap;
import java.util.Map;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.util.Constants.Config.General;

public interface Format extends WithPlugin, General {

    public static String format(String format, Map<String, String> replacements, Boolean ...color) {
        replacements.put("prefix", PREFIX);
        replacements.put("primary_color", PRIMARY_COLOR);
        replacements.put("secondary_color", SECONDARY_COLOR);
        replacements.put("extra_color", EXTRA_COLOR);
        replacements.put("error_color", ERROR_COLOR);
        replacements.put("warning_color", WARNING_COLOR);
        replacements.put("success_color", SUCCESS_COLOR);
        replacements.put("info_color", INFO_COLOR);
        replacements.put("message_color", MESSAGE);
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            // remove all $ from entry
            String key = entry.getKey().replaceAll("\\$", "\\\\$");
            format = format.replace("%" + key + "%", entry.getValue());
        }

        format = ChatColor.translateAlternateColorCodes('&', format);

        return format;
    }

    public static String format(String format, Player player, Map<String, String> ...replacements) {
        HashMap<String, String> _replacements = new HashMap<String, String>();
        for (Map<String, String> __replacements : replacements) {
            _replacements.putAll(__replacements);
        }
        _replacements.put("player_name", player.getName());
        _replacements.put("player_uuid", player.getDisplayName());
        _replacements.put("world", player.getWorld().getName());

        if (ShadowProtect.getPlugin(ShadowProtect.class).getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        }
        return format(format, _replacements);
    }
}

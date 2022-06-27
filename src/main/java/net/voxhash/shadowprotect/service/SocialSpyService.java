package net.voxhash.shadowprotect.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Messages;

public class SocialSpyService implements Messages, Format {

    Player player;
    DataService dataService;

    public SocialSpyService(Player player) {
        this.player = player;
        this.dataService = getPlugin().dataServiceManager.getDataService(player);
    }
    
    public static List<Player> getRecipients(Player from) {
        ArrayList<Player> recipients = new ArrayList<Player>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            DataService srvc = ShadowProtect.getPlugin(ShadowProtect.class).dataServiceManager.getDataService(p);
            if (srvc.isSocialSpyEnabled()) {
                recipients.add(p);
            }
        }
        return new ArrayList<>(recipients);
    }   

    public Player getPlayer() {
        return player;
    }

    public Boolean isSocialSpyEnabled() {
        return dataService.isSocialSpyEnabled();
    }

    public Boolean enableSocialSpy() {
        if (this.dataService.isSocialSpyEnabled()) return false;
        this.dataService.setSocialSpyEnabled(true);
        return true;
    }

    public Boolean disableSocialSpy() {
        if (!this.dataService.isSocialSpyEnabled()) return false;
        this.dataService.setSocialSpyEnabled(false);
        return true;
    }

    public Boolean sendSocialSpyMessage(String message) {
        if (this.dataService.isMuted()) return false;

        List<Player> recipients = getRecipients(player);
        String formattedMessage = Format.format(Messages.Social_Spy.SOCIAL_SPY_MESSAGE, this.player, new HashMap<String, String>() {{
            put("message", player.hasPermission("shadowprotect.chat.color") ? message : ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)));
            put("player_name", player.getName());
        }});
        for (Player recipient : recipients) {
            recipient.sendMessage(formattedMessage);
        }
        return true;
    }
}

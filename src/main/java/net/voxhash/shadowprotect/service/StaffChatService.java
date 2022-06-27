package net.voxhash.shadowprotect.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Messages;

public class StaffChatService implements Messages, Format {

    Player player;
    DataService dataService;

    public StaffChatService(Player player) {
        this.player = player;
        this.dataService = getPlugin().dataServiceManager.getDataService(player);
    }
    
    public static List<Player> getRecipients(Player from) {
        ArrayList<Player> recipients = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        recipients.removeIf(p ->{
            return !p.hasPermission("shadowprotect.chat.staff");
        });
        return new ArrayList<>(recipients);
    }   

    public Player getPlayer() {
        return player;
    }

    public Boolean isStaffChatEnabled() {
        return dataService.isStaffChatEnabled();
    }

    public Boolean enableStaffChat() {
        if (this.dataService.isStaffChatEnabled()) return false;
        this.dataService.setStaffChatEnabled(true);
        return true;
    }

    public Boolean disableStaffChat() {
        if (!this.dataService.isStaffChatEnabled()) return false;
        this.dataService.setStaffChatEnabled(false);
        return true;
    }

    public Boolean sendStaffChatMessage(String message) {
        if (this.dataService.isMuted()) return false;

        List<Player> recipients = getRecipients(player);
        String formattedMessage = Format.format(Messages.Staff.STAFF_CHAT_FORMAT, this.player, new HashMap<String, String>() {{
            put("message", player.hasPermission("shadowprotect.chat.color") ? message : ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)));
            put("player_name", player.getName());
        }});
        for (Player recipient : recipients) {
            recipient.sendMessage(formattedMessage);
        }
        return true;
    }
}

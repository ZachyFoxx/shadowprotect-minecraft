package net.voxhash.shadowprotect.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.chat.Channel;
import net.voxhash.shadowprotect.result.MessageResult;
import net.voxhash.shadowprotect.result.Priority;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Messages;

@SuppressWarnings("unchecked")
public class MessageService implements Format, Messages, Config {
    
    Player player;
    DataService dataService;

    public MessageService(Player player) {
        this.player = player;
        this.dataService = getPlugin().dataServiceManager.getDataService(player);
    }

    public static List<Player> getRecipients(Player from, Priority priority) {
        ArrayList<Player> recipients = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        recipients.removeIf(p ->{
            DataService srvc = ShadowProtect.getPlugin(ShadowProtect.class).dataServiceManager.getDataService(p);
            return srvc.getPriority().isGreaterThan(priority) || srvc.isBlocked(from);
        });
        return new ArrayList<>(recipients);
    }   
    
    public Player getPlayer() {
        return player;
    }

    public Channel getChannel() {
        return dataService.getChannel();
    }

    public Boolean moveChannel(Channel channel) {
        dataService.setChannel(channel);
        return true;
    }

    public Boolean clearChatBuffer() {
        if (player.hasPermission("shadowprotect.chat.clear.bypass"))
            return false;

        for (int i = 0; i < 100; i++)
            player.sendMessage(" ");

        return true;
    }

    public MessageResult broadcast(String message) {
        if (this.dataService.isMuted()) return MessageResult.FAIL_MUTED;

        List<Player> recipients = getRecipients(player, Priority.DEFAULT);
        String formattedMessage = Format.format(Messages.Chat.FORMAT, this.player, new HashMap<String, String>() {{
            put("message", player.hasPermission("shadowprotect.chat.color") ? message : ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)));
            put("player_name", player.getName());
        }});
        
        recipients.forEach(p -> p.sendMessage(formattedMessage));
        Bukkit.getConsoleSender().sendMessage(formattedMessage);
        
        return MessageResult.OK;
    }
}

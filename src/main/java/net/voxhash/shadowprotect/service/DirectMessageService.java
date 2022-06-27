package net.voxhash.shadowprotect.service;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.result.DirectMessageResult;
import net.voxhash.shadowprotect.result.Priority;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Messages;

@SuppressWarnings("unchecked")
public class DirectMessageService implements Format, Messages {

    private Player player;
    private Player last;
    
    public DirectMessageService(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
    
    public Player getLast() {
        return last;
    }

    public void setLast(Player last) {
        this.last = last;
    }

    public DirectMessageResult sendTo(Player target, String message) {
        if (target == null) return DirectMessageResult.FAIL_NONEXISTENT;
        if (target.equals(player)) return DirectMessageResult.FAIL_SELF;
        DirectMessageResult result = getPlugin().directMessageServiceManager.getDirectMessageService(target).receive(player, message);
        if (result != DirectMessageResult.OK) {
            return result;
        }
        
        this.player.sendMessage(Format.format(DM.OUTGOING, player, new HashMap<String, String>() {{
            put("from_name", player.getName());
            put("to_name", target.getName());
            put("message", player.hasPermission("shadowprotect.chat.color") ? message : ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)));
        }}));
        setLast(target);
        getPlugin().directMessageServiceManager.getDirectMessageService(target).setLast(player);
        return result;
    }

    public DirectMessageResult receive(Player from, String message) {
        if (isBlocked(player)) return DirectMessageResult.FAIL_BLOCK;
        if (getPlugin().dataServiceManager.getDataService(player).getPriority().isGreaterThan(Priority.DIRECT)) {
            return DirectMessageResult.FAIL_PRIORITY;
        }
        this.player.sendMessage(Format.format(DM.INCOMING, player, new HashMap<String, String>() {{
            put("from_name", from.getName());
            put("message", from.hasPermission("shadowprotect.chat.color") ? message : ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)));
        }}));
        return DirectMessageResult.OK;
    }

    public boolean isBlocked(Player player) {
        return getPlugin().dataServiceManager.getDataService(player).isBlocked(player);
    }

    public Boolean block(Player player) {
        return getPlugin().dataServiceManager.getDataService(player).block(player);
    }   
}

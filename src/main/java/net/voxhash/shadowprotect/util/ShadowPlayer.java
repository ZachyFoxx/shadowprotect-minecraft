package net.voxhash.shadowprotect.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.event.CustomAsyncPlayerChatEvent;

public class ShadowPlayer {

    private UUID uniqueId;
    private String username;
    private CustomAsyncPlayerChatEvent lastMessage;

    public ShadowPlayer(UUID uniqueId, String username) {
        this.uniqueId = uniqueId;
        this.username = username;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return username;
    }

    public CustomAsyncPlayerChatEvent getLastMessageEvent() {
        return lastMessage;
    }

    public void setLastMessage(CustomAsyncPlayerChatEvent lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void sendMessage(String message) {
        Bukkit.getPlayer(uniqueId).sendMessage(message);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }
    
}

package net.voxhash.shadowprotect.chat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class ChatMessage {

    private List<Player> recipients;
    private String message;
    private Date timestamp;
    private UUID uniqueId;

    public ChatMessage(List<Player> recipients, String message, Date timestamp) {
        this.recipients  = recipients;
        this.message = message;
        this.timestamp = timestamp;
        this.uniqueId = UUID.randomUUID();
    }

    public List<Player> getPlayer() {
        return recipients;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}

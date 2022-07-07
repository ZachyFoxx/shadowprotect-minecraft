package net.voxhash.shadowprotect.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.chat.ChatMessage;

public class ChatMessageManager {

    private HashMap<Player, ChatMessageManager> managers = new HashMap<>();
    private ArrayList<ChatMessage> messageBuffer = new ArrayList<>();

    public ChatMessageManager(Player player) {
        managers.put(player, this);
    }

    public ChatMessageManager() {
        
    }

    // addMessage. Adds a message to the buffer, if the buffer is larger than 100 messages, it will remove the oldest message.
    public void addMessage(ChatMessage message) {
        messageBuffer.add(message);
        if (messageBuffer.size() > 100) {
            messageBuffer.remove(0);
        }
    }

    public ChatMessage getMessage(UUID uuid) {
        return messageBuffer.stream().filter(message -> message.getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    public void removeMessage(UUID uuid) {
        messageBuffer.removeIf(message -> message.getUniqueId().equals(uuid));
    }

    public void clearMessages() {
        messageBuffer.clear();
    }

    public ChatMessageManager getManager(Player player) {
        if (!managers.containsKey(player)) {
            managers.put(player, new ChatMessageManager(player));
        }
        return managers.get(player);
    }
}

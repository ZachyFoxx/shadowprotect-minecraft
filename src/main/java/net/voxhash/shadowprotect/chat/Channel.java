package net.voxhash.shadowprotect.chat;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Channel {

    private UUID uniqueId;
    private Type type;
    private String name;
    private ArrayList<Player> members = new ArrayList<Player>();
    
    public Channel(UUID uniqueId, Type type, String name) {
        this.uniqueId = uniqueId;
        this.type = type;
        this.name = name;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getMembers() {
        return members;
    }
    
    public void addMember(Player player) {
        members.add(player);
    }

    public void removeMember(Player player) {
        members.remove(player);
    }

    public void close() {
        members.clear();
    }
    
    public enum Type {
        PUBLIC,
        PRIVATE,
        STAFF,
        LOCAL;
    }

}

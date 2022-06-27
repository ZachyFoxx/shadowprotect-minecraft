package net.voxhash.shadowprotect.service;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.chat.Channel;
import net.voxhash.shadowprotect.result.Priority;
import net.voxhash.shadowprotect.util.WithPlugin;

public class DataService implements WithPlugin {
    private Player player;
    private Priority priority = Priority.DEFAULT;
    private Boolean muted = false;
    private ArrayList<Player> blockedPlayers = new ArrayList<Player>();
    private Channel channel = getPlugin().channelManager.getChannel(new UUID(0, 0));

    private Boolean staffChatEnabled = false;
    private Boolean signSpyEnabled = false;
    private Boolean socialSpyEnabled = false;

    public DataService(Player player) {
        this.player = player;
    }

    public DataService get(Player player) {
        return getPlugin().dataServiceManager.getDataService(player);
    }

    public Player getPlayer() {
        return player;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean isMuted() {
        return muted;
    }

    public void setMuted(Boolean muted) {
        this.muted = muted;
    }

    public ArrayList<Player> getBlockedPlayers() {
        return blockedPlayers;
    }

    public void addBlockedPlayer(Player player) {
        blockedPlayers.add(player);
    }

    public void removeBlockedPlayer(Player player) {
        blockedPlayers.remove(player);
    }

    public Boolean isStaffChatEnabled() {
        return staffChatEnabled;
    }

    public void setStaffChatEnabled(Boolean staffChatEnabled) {
        this.staffChatEnabled = staffChatEnabled;
    }

    public Boolean isSocialSpyEnabled() {
        return socialSpyEnabled;
    }

    public void setSocialSpyEnabled(Boolean socialSpyEnabled) {
        this.socialSpyEnabled = socialSpyEnabled;
    }

    public Boolean isSignSpyEnabled() {
        return signSpyEnabled;
    }

    public void setSignSpyEnabled(Boolean signSpyEnabled) {
        this.signSpyEnabled = signSpyEnabled;
    }

    public Boolean isBlocked(Player player) {
        return blockedPlayers.contains(player);
    }

    public Boolean block(Player player) {
        if (blockedPlayers.contains(player)) return false;
        blockedPlayers.add(player);
        return true;
    }
    
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

package net.voxhash.shadowprotect.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.ShadowPlayer;
import net.voxhash.shadowprotect.util.Constants.Messages;

public class PlayerConnectionListener implements Listener, Messages, Format {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getPlugin().playerCache.put(event.getPlayer().getUniqueId(), new ShadowPlayer(event.getPlayer().getUniqueId(), event.getPlayer().getName()));
        if (Messages.Join_Leave.JOIN_MESSAGES.size() > 0) {
            String message = Messages.Join_Leave.JOIN_MESSAGES.get((int) (Math.random() * Messages.Join_Leave.JOIN_MESSAGES.size()));
            event.setJoinMessage(Format.format(message, event.getPlayer()));
        } else {
            event.setJoinMessage("");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        getPlugin().playerCache.remove(event.getPlayer().getUniqueId());
        if (Messages.Join_Leave.LEAVE_MESSAGES.size() > 0) {
            String message = Messages.Join_Leave.LEAVE_MESSAGES.get((int) (Math.random() * Messages.Join_Leave.LEAVE_MESSAGES.size()));
            event.setQuitMessage(Format.format(message, event.getPlayer()));
        } else {
            event.setQuitMessage("");
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        getPlugin().playerCache.remove(event.getPlayer().getUniqueId());
        if (Messages.Join_Leave.KICK_MESSAGES.size() > 0) {
            String message = Messages.Join_Leave.KICK_MESSAGES.get((int) (Math.random() * Messages.Join_Leave.KICK_MESSAGES.size()));
            event.setLeaveMessage(Format.format(message, event.getPlayer()));
        } else {
            event.setLeaveMessage("");
        }
    }
}

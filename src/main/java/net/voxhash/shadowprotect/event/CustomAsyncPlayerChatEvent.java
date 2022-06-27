package net.voxhash.shadowprotect.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * This event will sometimes fire synchronously, depending on how it was
 * triggered.
 * <p>
 * The constructor provides a boolean to indicate if the event was fired
 * synchronously or asynchronously. When asynchronous, this event can be
 * called from any thread, sans the main thread, and has limited access to the
 * API.
 * <p>
 * If a player is the direct cause of this event by an incoming packet, this
 * event will be asynchronous. If a plugin triggers this event by compelling a
 * player to chat, this event will be synchronous.
 * <p>
 * Care should be taken to check {@link #isAsynchronous()} and treat the event
 * appropriately.
 */
public class CustomAsyncPlayerChatEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private String message;
    private boolean cancelled;
    private final Long timestamp;
    private AsyncPlayerChatEvent originalEvent;

    public CustomAsyncPlayerChatEvent(Player player, String message, AsyncPlayerChatEvent event) {
        super(event.isAsynchronous());
        this.player = player;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.originalEvent = event;
    }

    public AsyncPlayerChatEvent getOriginalEvent() {
        return originalEvent;
    }


    /**
     * Gets the player who triggered this event.
     *
     * @return the player who triggered this event
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the message that was sent by the player.
     *
     * @return the message that was sent by the player
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Gets the timestamp of when this event was triggered.
     * 
     * @return the timestamp of when this event was triggered
     */
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setMessage(String message) {
        originalEvent.setMessage(message);
        this.message = message;
    }
}

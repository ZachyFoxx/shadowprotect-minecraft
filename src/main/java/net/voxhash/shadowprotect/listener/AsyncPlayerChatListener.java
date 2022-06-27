package net.voxhash.shadowprotect.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.voxhash.shadowprotect.event.CustomAsyncPlayerChatEvent;
import net.voxhash.shadowprotect.service.AntiSpamService;
import net.voxhash.shadowprotect.util.Constants;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.ShadowPlayer;

public class AsyncPlayerChatListener implements Listener, Constants, Format {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        CustomAsyncPlayerChatEvent cEvent = new CustomAsyncPlayerChatEvent(event.getPlayer(), event.getMessage(), event);
        Bukkit.getPluginManager().callEvent(cEvent);
        event.setCancelled(cEvent.isCancelled());
    }

    private ShadowPlayer getPlayer(Player player) {
        ShadowPlayer shadowPlayer = getPlugin().playerCache.get(player.getUniqueId());
        if (shadowPlayer == null) { 
            getPlugin().playerCache.put(player.getUniqueId(), new ShadowPlayer(player.getUniqueId(), player.getName()));
            shadowPlayer = getPlugin().playerCache.get(player.getUniqueId());
        }
        return shadowPlayer;
    }
    
    @EventHandler
    public void onCustomAsyncPlayerChatEvent(CustomAsyncPlayerChatEvent event) {
        event.setCancelled(true);
        final ShadowPlayer player = getPlayer(event.getPlayer());
        
        if (event.getMessage().startsWith(Messages.Staff.STAFF_CHAT_PREFIX) && event.getPlayer().hasPermission("shadowprotect.ch at.staff")) {
            getPlugin().staffChatServiceManager.getStaffChatService(event.getPlayer()).sendStaffChatMessage(event.getMessage().substring(1));
            return;
        }

        if (getPlugin().staffChatServiceManager.getStaffChatService(event.getPlayer()).isStaffChatEnabled()) {
            getPlugin().staffChatServiceManager.getStaffChatService(event.getPlayer()).sendStaffChatMessage(event.getMessage());
            return;
        }
        
        if (event.getPlayer().hasPermission(Permissions.CHAT_BYPASS)) {
            plugin.messageServiceManager.getMessageService(event.getPlayer()).broadcast(event.getMessage());
            player.setLastMessage(event);
            return;
        }
        
        
        AntiSpamService antiSpamService = new AntiSpamService(event.getMessage(), getPlugin().getPlayer(event.getPlayer()));
        boolean safe = antiSpamService.check();
        String result = antiSpamService.getResult();

        if (safe) {
            plugin.messageServiceManager.getMessageService(event.getPlayer()).broadcast(result);
            player.setLastMessage(event);
        }
    }
}

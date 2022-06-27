package net.voxhash.shadowprotect.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import net.voxhash.shadowprotect.service.AntiSpamService;
import net.voxhash.shadowprotect.service.SignNotification;
import net.voxhash.shadowprotect.util.WithPlugin;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Permissions;

public class SignChangeListener implements Listener, WithPlugin, Config {
    
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getPlayer().hasPermission(Permissions.CHAT_BYPASS)) return;
        SignNotification notification = new SignNotification(event.getPlayer(), event.getLines(), event.getBlock().getLocation());
        int i = 0;
        for (String line : event.getLines()) {
            if (General.CHECK_SIGN) {
                AntiSpamService antiSpamService = new AntiSpamService(line, getPlugin().getPlayer(event.getPlayer()));
                antiSpamService.check();
                String antiSpamResult = antiSpamService.getResult();
                event.setLine(i, antiSpamResult);
            }
            if (line.length() > 0) {
                i++;
            }
        }
        if (Config.Social_Spy.IGNORE_BLANK && i == 0) return;
        getPlugin().signSpyServiceManager.getSignSpyService(event.getPlayer()).sendSignSpyMessage(notification);
    }
}

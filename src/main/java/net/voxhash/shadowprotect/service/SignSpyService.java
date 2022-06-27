package net.voxhash.shadowprotect.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Messages;

public class SignSpyService implements Messages, Config, Format {

    Player player;
    DataService dataService;

    public SignSpyService(Player player) {
        this.player = player;
        this.dataService = getPlugin().dataServiceManager.getDataService(player);
    }

    public static List<Player> getRecipients() {
        ArrayList<Player> recipients = new ArrayList<Player>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            DataService srvc = ShadowProtect.getPlugin(ShadowProtect.class).dataServiceManager.getDataService(p);
            if (srvc.isSignSpyEnabled()) {
                recipients.add(p);
            }
        }
        return new ArrayList<>(recipients);
    }   

    public Player getPlayer() {
        return player;
    }

    public Boolean isSignSpyEnabled() {
        return dataService.isSignSpyEnabled();
    }

    public Boolean enableSignSpy() {
        if (this.dataService.isSignSpyEnabled()) return false;
        this.dataService.setSignSpyEnabled(true);
        return true;
    }

    public Boolean disableSignSpy() {
        if (!this.dataService.isSignSpyEnabled()) return false;
        this.dataService.setSignSpyEnabled(false);
        return true;
    }

    public Boolean sendSignSpyMessage(SignNotification notification) {
        List<Player> recipients = getRecipients();
        String formattedMessage = Format.format(Messages.Social_Spy.SIGN_SPY_MESSAGE, this.player, new HashMap<String, String>() {{
            put("sign_line_1", notification.getMessage()[0].length() == 0 ? "-" : notification.getMessage()[0]);
            put("sign_line_2", notification.getMessage()[1].length() == 0 ? "-" : notification.getMessage()[1]);
            put("sign_line_3", notification.getMessage()[2].length() == 0 ? "-" : notification.getMessage()[2]);
            put("sign_line_4", notification.getMessage()[3].length() == 0 ? "-" : notification.getMessage()[3]);
            put("sign_location", (notification.getLocation().getX() + ", " + notification.getLocation().getY() + ", " + notification.getLocation().getZ()));
        }});

        for (Player recipient : recipients) {
            recipient.sendMessage(formattedMessage);
        }
        return true;
    }
}
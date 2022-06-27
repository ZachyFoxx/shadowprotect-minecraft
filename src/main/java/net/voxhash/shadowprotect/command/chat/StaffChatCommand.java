package net.voxhash.shadowprotect.command.chat;

import java.util.HashMap;

import org.bukkit.entity.Player;

import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.ABooleanArgument;
import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.service.DataService;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Messages;

@Command("staffchat")
@Alias("sc")
@Permission("shadowprotect.chat.staff")
public class StaffChatCommand implements Format, Messages, Config {
    
    private static ShadowProtect plugin = ShadowProtect.getPlugin(ShadowProtect.class);
    
    @Default
    public static void chat(Player sender) {
        DataService dataService = plugin.dataServiceManager.getDataService(sender);
        dataService.setStaffChatEnabled(!dataService.isStaffChatEnabled());
        sender.sendMessage(Format.format(Messages.Staff.STAFF_CHAT_TOGGLE, sender, new HashMap<String, String>() {{
            put("status", dataService.isStaffChatEnabled() ? Config.General.SUCCESS_COLOR + "enabled" : Config.General.ERROR_COLOR +  "disabled");
        }}));
    }
    
    @Subcommand("toggle")
    public static void send(Player sender, @ABooleanArgument boolean enabled) {
        DataService dataService = plugin.dataServiceManager.getDataService(sender);
        dataService.setStaffChatEnabled(enabled);
        sender.sendMessage(Format.format(Messages.Staff.STAFF_CHAT_TOGGLE, sender, new HashMap<String, String>() {{
            put("status", enabled ? Config.General.SUCCESS_COLOR + "enabled" : Config.General.ERROR_COLOR +  "disabled");
        }}));
    }
    

}

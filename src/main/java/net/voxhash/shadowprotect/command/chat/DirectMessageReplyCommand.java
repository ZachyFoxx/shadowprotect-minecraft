package net.voxhash.shadowprotect.command.chat;

import org.bukkit.entity.Player;

import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.AGreedyStringArgument;
import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.result.DirectMessageResult;
import net.voxhash.shadowprotect.service.AntiSpamService;
import net.voxhash.shadowprotect.service.DirectMessageService;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Permissions;
import net.voxhash.shadowprotect.util.Constants.Messages.DM;

@Command("reply")
@Alias({"r"})
@Permission("shadowprotect.chat.message.reply")
public class DirectMessageReplyCommand implements Config {
    
    private static ShadowProtect plugin = ShadowProtect.getPlugin(ShadowProtect.class);

    public static void chat(Player sender) {
        sender.sendMessage("Usage: /reply <message>");
    }

    @Default
    public static void send(Player sender, @AGreedyStringArgument String message) {
        DirectMessageService srvc = plugin.directMessageServiceManager.getDirectMessageService(sender);
        if (srvc.getLast() == null) {
            sender.sendMessage(Format.format(DM.NO_LAST_DM, sender));
            return;
        }
        AntiSpamService antiSpamService = new AntiSpamService(message, ShadowProtect.getPlugin(ShadowProtect.class).getPlayer(sender));
        boolean safe = antiSpamService.check();
        String antiSpamResult = antiSpamService.getResult();
        if (General.CHECK_DM && !sender.hasPermission(Permissions.CHAT_BYPASS)) {
            if (safe) {
                sendMessage(srvc, antiSpamResult, sender);
            }
        } else {
            sendMessage(srvc, message, sender);
        }
    }
 
    private static void sendMessage(DirectMessageService srvc, String message, Player sender) {
        DirectMessageResult result = srvc.sendTo(srvc.getLast(), message);
        switch (result) {
            case OK: break;
            case FAIL_SELF: sender.sendMessage(Format.format(DM.CANNOT_DM_SELF, sender)); break;
            case FAIL_BLOCK: sender.sendMessage(Format.format(DM.BLOCKED, sender)); break;
            default: break;
        }
    }
}

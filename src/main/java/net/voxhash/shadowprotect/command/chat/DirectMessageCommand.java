package net.voxhash.shadowprotect.command.chat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.arguments.AGreedyStringArgument;
import dev.jorel.commandapi.annotations.arguments.APlayerArgument;
import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.result.DirectMessageResult;
import net.voxhash.shadowprotect.service.AntiSpamService;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Messages;
import net.voxhash.shadowprotect.util.Constants.Permissions;

@Command("msg")
@Alias({"dm", "message", "pm"})
@Permission("shadowprotect.chat.message")
public class DirectMessageCommand implements Format, Messages, Config {
    
    public static void chat(CommandSender sender) {
        sender.sendMessage("Usage: /msg <player> <message>");
    }

    @Default
    public static void send(Player sender, @APlayerArgument Player target, @AGreedyStringArgument String message) {
        AntiSpamService antiSpamService = new AntiSpamService(message, ShadowProtect.getPlugin(ShadowProtect.class).getPlayer(sender));
        boolean safe = antiSpamService.check();
        String antiSpamResult = antiSpamService.getResult();
        if (General.CHECK_DM && !sender.hasPermission(Permissions.CHAT_BYPASS)) {
            if (safe) {
                sendMessage(antiSpamResult, sender, target);
            }
        } else {
            sendMessage(message, sender, target);
        }
    }

    private static void sendMessage(String message, Player sender, Player target) {
        DirectMessageResult result = ShadowProtect.getPlugin(ShadowProtect.class).directMessageServiceManager.getDirectMessageService((Player) sender).sendTo(target, message);
        switch (result) {
            case OK: break;
            case FAIL_SELF: sender.sendMessage(Format.format(DM.CANNOT_DM_SELF, sender)); break;
            case FAIL_BLOCK: sender.sendMessage(Format.format(DM.BLOCKED, sender)); break;
            default: break;    
        }
    }
}

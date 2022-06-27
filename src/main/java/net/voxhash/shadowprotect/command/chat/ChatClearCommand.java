package net.voxhash.shadowprotect.command.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.service.MessageService;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Messages;

@Command("clearchat")
@Alias({"cc"})
@Permission("shadowprotect.chat.clear")
public class ChatClearCommand {
    private static ShadowProtect plugin = ShadowProtect.getPlugin(ShadowProtect.class);

    @Default
    public static void chat(Player sender) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            MessageService messageService = plugin.messageServiceManager.getMessageService(player);
            messageService.clearChatBuffer();
            player.sendMessage(Format.format(Messages.Chat.CLEARED, sender));
        }
    }
}

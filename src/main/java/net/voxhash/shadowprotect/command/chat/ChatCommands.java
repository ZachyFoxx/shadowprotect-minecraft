package net.voxhash.shadowprotect.command.chat;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AIntegerArgument;
import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.service.MessageService;
import net.voxhash.shadowprotect.util.Format;
import net.voxhash.shadowprotect.util.Constants.Config;
import net.voxhash.shadowprotect.util.Constants.Messages;

@Command("chat")
@Permission("shadowprotect.chat")
public class ChatCommands implements Format, Config, Messages {

    private static ShadowProtect plugin = ShadowProtect.getPlugin(ShadowProtect.class);
    
    @Default
    public static void chat(CommandSender sender) {

    }

    @Subcommand("clear")
    @Permission("shadowprotect.chat.clear")
    public static void clear(Player sender) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            MessageService messageService = plugin.messageServiceManager.getMessageService(player);
            messageService.clearChatBuffer();
            player.sendMessage(Format.format(Messages.Chat.CLEARED, sender));
        }
    } 

    @Subcommand("delay")
    @Permission("shadowprotect.chat.delay")
    public static void delay(Player sender, @AIntegerArgument Integer delay) {
        if (delay < 0) {
            sender.sendMessage(Format.format(Staff.DELAY_NEGATIVE, sender));
            return;
        }

        plugin.getConfig().set("chat-delay", delay);
        plugin.saveConfig();
        plugin.chatDelay = delay;
        sender.sendMessage(Format.format(Staff.DELAY_SET, sender, new HashMap<String, String>() {{ 
            put("delay", String.valueOf(delay));
        }}));
        Bukkit.broadcastMessage(Format.format(Chat.DELAY_SET, sender, new HashMap<String, String>() {{ 
            put("delay", String.valueOf(delay));
        }}));
    }

    @Subcommand("delay")
    @Permission("shadowprotect.chat.delay")
    public static void delayGet(Player sender) {
        sender.sendMessage(Format.format(Staff.DELAY, sender, new HashMap<String, String>() {{ 
            put("delay", String.valueOf(plugin.chatDelay));
        }}));
    }
}

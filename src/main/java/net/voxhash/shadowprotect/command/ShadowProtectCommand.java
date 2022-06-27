package net.voxhash.shadowprotect.command;

import org.bukkit.entity.Player;

import dev.jorel.commandapi.annotations.Alias;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import net.voxhash.shadowprotect.ShadowProtect;
import net.voxhash.shadowprotect.util.Constants;
import net.voxhash.shadowprotect.util.Format;

@Command("shadowprotect")
@Alias("sp")
@Permission("shadowprotect.admin")
public class ShadowProtectCommand {

    @Default
    public static void onDefault(Player sender) {
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " A powerful chat moderation plugin!", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &7/sp help - For more information!", sender));
    }

    @Subcommand("reload")
    public static void onReload(Player sender) {
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &aReloading plugin...", sender));
        ShadowProtect.getPlugin(ShadowProtect.class).reloadConfig();
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &aPlugin reloaded!", sender));
    }


    @Subcommand("help")
    public static void onHelp(Player sender) {
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &7Shadow Protect Commands", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &6/chat <clear/delay> [delay value]\n  &7&oClear or set the chat delay!", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &6/staffchat [true/false]\n  &7&oToggle your staff chat status", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &6/signspy [true/false]\n  &7&oToggle your sign spy status", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &6/socialspy [true/false]\n  &7&oToggle your social spy status", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &6/sp reload\n  &7&oReload the plugin!", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &6/smf register <email> <username> <password>\n  &7&oRegister a Smart Filter account", sender));
        sender.sendMessage(Format.format(Constants.Config.General.PREFIX + " &6/smf verify <token>\n  &7&oVerify your Smart Filter account", sender));
    }
}

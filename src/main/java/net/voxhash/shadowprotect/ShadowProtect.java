package net.voxhash.shadowprotect;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import net.voxhash.shadowprotect.command.ShadowProtectCommand;
import net.voxhash.shadowprotect.command.SmartFilterCommand;
import net.voxhash.shadowprotect.command.chat.ChatClearCommand;
import net.voxhash.shadowprotect.command.chat.ChatCommands;
import net.voxhash.shadowprotect.command.chat.DirectMessageCommand;
import net.voxhash.shadowprotect.command.chat.DirectMessageReplyCommand;
import net.voxhash.shadowprotect.command.chat.StaffChatCommand;
import net.voxhash.shadowprotect.command.socialspy.SignSpyCommand;
import net.voxhash.shadowprotect.command.socialspy.SocialSpyCommand;
import net.voxhash.shadowprotect.listener.AsyncPlayerChatListener;
import net.voxhash.shadowprotect.listener.PlayerCommandPreProcessListener;
import net.voxhash.shadowprotect.listener.PlayerConnectionListener;
import net.voxhash.shadowprotect.listener.SignChangeListener;
import net.voxhash.shadowprotect.manager.ChannelManager;
import net.voxhash.shadowprotect.manager.DataServiceManager;
import net.voxhash.shadowprotect.manager.DirectMessageServiceManager;
import net.voxhash.shadowprotect.manager.ImplementationManager;
import net.voxhash.shadowprotect.manager.MessageServiceManager;
import net.voxhash.shadowprotect.manager.SignSpyServiceManager;
import net.voxhash.shadowprotect.manager.SocialSpyServiceManager;
import net.voxhash.shadowprotect.manager.StaffChatServiceManager;
import net.voxhash.shadowprotect.util.Constants;
import net.voxhash.shadowprotect.util.ShadowPlayer;
import net.voxhash.shadowprotect.util.StartupUtil;

public final class ShadowProtect extends JavaPlugin {

    public Integer chatDelay = 0;

    public HashMap<UUID, ShadowPlayer> playerCache = new HashMap<UUID, ShadowPlayer>();
    public ImplementationManager implManager;
    public ChannelManager channelManager;
    public DataServiceManager dataServiceManager;
    public DirectMessageServiceManager directMessageServiceManager;
    public MessageServiceManager messageServiceManager;
    public StaffChatServiceManager staffChatServiceManager;
    public SignSpyServiceManager signSpyServiceManager;
    public SocialSpyServiceManager socialSpyServiceManager;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(true)); //Load with verbose output

        CommandAPI.unregister("msg");
        CommandAPI.unregister("whisper");

        CommandAPI.registerCommand(DirectMessageCommand.class);
        CommandAPI.registerCommand(DirectMessageReplyCommand.class);
        CommandAPI.registerCommand(ChatCommands.class);
        CommandAPI.registerCommand(StaffChatCommand.class);
        CommandAPI.registerCommand(SignSpyCommand.class);
        CommandAPI.registerCommand(SocialSpyCommand.class);
        CommandAPI.registerCommand(ShadowProtectCommand.class);
        CommandAPI.registerCommand(ChatClearCommand.class);
        SmartFilterCommand.command.register();

        StartupUtil.setupConfig(this);
        this.chatDelay = Constants.Config.Anti_Spam.Chat.DELAY;
        this.implManager = new ImplementationManager(this);
        this.channelManager = new ChannelManager();
        this.dataServiceManager = new DataServiceManager();
        this.directMessageServiceManager = new DirectMessageServiceManager();
        this.messageServiceManager = new MessageServiceManager();
        this.staffChatServiceManager = new StaffChatServiceManager();
        this.signSpyServiceManager = new SignSpyServiceManager();
        this.socialSpyServiceManager = new SocialSpyServiceManager();
    }
    
    @Override
    public void onEnable() {
        CommandAPI.onEnable(this);
        implManager.loadImplementationsIfExists("PlaceholderAPI");
        
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreProcessListener(), this);

        getLogger().info("ShadowProtect has been enabled!");
    }

    @Override
    public void onDisable() {
        reloadConfig();
    }

    public ShadowProtect getPlugin() {
        return this;
    }

    public ShadowPlayer getPlayer(Player player) {
        ShadowPlayer shadowPlayer = getPlugin().playerCache.get(player.getUniqueId());
        if (shadowPlayer == null) {
            getPlugin().playerCache.put(player.getUniqueId(), new ShadowPlayer(player.getUniqueId(), player.getName()));
            shadowPlayer = getPlugin().playerCache.get(player.getUniqueId());
        }
        return shadowPlayer;
    }
}

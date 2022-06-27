package net.voxhash.shadowprotect.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import net.voxhash.shadowprotect.chat.Channel;
import net.voxhash.shadowprotect.chat.Channel.Type;

public class ChannelManager {
    private HashMap<UUID, Channel> channels = new HashMap<UUID, Channel>();
    private Channel global = new Channel(new UUID(0, 0), Channel.Type.PUBLIC, "global");

    public ChannelManager() {
        channels.put(global.getUniqueId(), global);
    }

    public Channel createChannel(Type type, String name) {
        Channel channel = new Channel(UUID.randomUUID(), type, name);
        channels.put(channel.getUniqueId(), channel);
        return channel;
    }

    public Boolean removeChannel(Channel channel) {
        if (channels.containsKey(channel.getUniqueId())) {
            channels.remove(channel.getUniqueId());
            return true;
        }
        return false;
    }
    
    public Channel getChannel(UUID uniqueId) {
        if (channels.containsKey(uniqueId)) {
            return channels.get(uniqueId);
        }
        return null;
    }

    public Collection<Channel> getChannels() {
        return channels.values();
    }    
}

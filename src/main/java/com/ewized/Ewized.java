package com.ewized;

import com.ewized.minecraft.protocol.Packets;
import com.ewized.minecraft.proxy.server.ProxyMinecraftServer;

public interface Ewized {

    /** Get the packets service */
    static Packets packets() {
        return EwizedManager.get().packets;
    }

    /** Get the proxy instance of the Minecraft Server */
    static ProxyMinecraftServer server() {
        return EwizedManager.get().minecraftServer;
    }
}

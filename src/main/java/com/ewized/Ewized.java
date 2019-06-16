package com.ewized;

import com.ewized.minecraft.protocol.Packets;
import com.ewized.minecraft.proxy.server.ProxyMinecraftServer;

public interface Ewized {

    /** Get the packets service */
    static Packets packets() {
        return EwizedManager.get().packets;
    }

    /** Get the Minecraft Server */
    static ProxyMinecraftServer minecraftServer() {
        return EwizedManager.get().minecraftServer;
    }
}

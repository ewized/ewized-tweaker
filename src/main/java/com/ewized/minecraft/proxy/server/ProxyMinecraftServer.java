package com.ewized.minecraft.proxy.server;

import net.year4000.utilities.reflection.Gateways;
import net.year4000.utilities.reflection.annotations.Proxied;

@Proxied("net.minecraft.server.MinecraftServer")
public interface ProxyMinecraftServer {

    /** Create the proxy instance of the Minecraft server for the Ewized Manager */
    static ProxyMinecraftServer of(Object minecraftServer) {
        return Gateways.proxy(ProxyMinecraftServer.class, minecraftServer);
    }

    /** Get the underlying system for the minecraft Server */
    Object $this();
}

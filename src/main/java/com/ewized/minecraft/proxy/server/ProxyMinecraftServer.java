package com.ewized.minecraft.proxy.server;

import net.year4000.utilities.reflection.annotations.Proxied;

@Proxied("net.minecraft.server.MinecraftServer")
public interface ProxyMinecraftServer {
    /** Get the reference to the MinecraftServer instance */
    Object $this();
}

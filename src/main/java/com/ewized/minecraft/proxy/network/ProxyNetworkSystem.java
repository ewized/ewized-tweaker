package com.ewized.minecraft.proxy.network;

import io.netty.channel.ChannelFuture;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Proxied;

import java.util.List;

@Proxied("vx") // net.minecraft.network.NetworkSystem
public interface ProxyNetworkSystem {

    /** Get the list of network managers */
    @Getter(signature = "Ljava/util/List;", index = 0)
    List<ChannelFuture> endpoints();

    /** Get the list of network managers */
    @Getter(signature = "Ljava/util/List;", index = 1)
    List<Object> networkManagers();
}

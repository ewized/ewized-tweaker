/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.proxy.network;

import io.netty.channel.Channel;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Invoke;
import net.year4000.utilities.reflection.annotations.Proxied;

@Proxied("ja") // net.minecraft.network.NetworkManager
public interface ProxyNetworkManager {
    /** Get the object that this proxy is using */
    Object $this();

    /** The netty channel that is attached to the network manager */
    @Getter(signature = "Lio/netty/channel/Channel;")
    Channel channel();

    @Invoke(signature = "(Lka;)V") // net/minecraft/network/IPacket
    void sendPacket(Object packet);
}

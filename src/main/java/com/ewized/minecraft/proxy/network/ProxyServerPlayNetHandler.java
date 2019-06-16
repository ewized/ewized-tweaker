/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.proxy.network;

import net.year4000.utilities.reflection.annotations.Bridge;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Proxied;

@Proxied("vy") // net.minecraft.network.play.ServerPlayNetHandler
public interface ProxyServerPlayNetHandler {
    /** Get the object that this proxy is using */
    Object $this();

    /** Get the network manager */
    @Getter(signature = "Lja;") // net/minecraft/network/NetworkManager
    @Bridge(ProxyNetworkManager.class)
    ProxyNetworkManager networkManager();
}

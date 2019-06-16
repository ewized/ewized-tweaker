/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.proxy.entity.player;

import com.ewized.minecraft.proxy.entity.ProxyEntity;
import com.ewized.minecraft.proxy.network.ProxyServerPlayNetHandler;
import net.year4000.utilities.Conditions;
import net.year4000.utilities.reflection.Gateways;

import net.year4000.utilities.reflection.annotations.Bridge;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Proxied;
import com.ewized.minecraft.protocol.Packet;

@Proxied("net.minecraft.entity.player.EntityPlayerMP")
public interface ProxyServerPlayerEntity extends ProxyEntity {
    /** Create the proxy of the player */
    static ProxyServerPlayerEntity of(Object player) {
        Conditions.nonNull(player, "player");
        return Gateways.proxy(ProxyServerPlayerEntity.class, player);
    }

    /** Get the object that this proxy is using */
    Object $this();

    /** Grabs the current instance of the NetHandlerPlayServer */
    @Getter(signature = "Lnet/minecraft/network/NetHandlerPlayServer;")
    @Bridge(ProxyServerPlayNetHandler.class)
    ProxyServerPlayNetHandler netHandlerPlayServer();

    default String getName() {
        return "Player"; // todo find the game from the source so we can proxy it
    }

    /** Send the packet for this player */
    default void sendPacket(Packet packet) {
        netHandlerPlayServer().networkManager().sendPacket(packet.mcPacket());
    }
}

/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.protocol;

import com.ewized.minecraft.proxy.entity.player.ProxyServerPlayerEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface Packets {
    /** Get the default PacketManager for this interface using the plugin you supplied */
    static Packets manager(Object plugin) {
        return PacketManager.get(plugin);
    }

    /** Send the packet to the player */
    default void sendPacket(ProxyServerPlayerEntity player, Packet packet) {
        sendPacket(ImmutableList.of(player), packet);
    }

    /** Send the packet to the set of players */
    default void sendPacket(Packet packet, ProxyServerPlayerEntity... players) {
        sendPacket(ImmutableSet.copyOf(players), packet);
    }

    /** Send the packet to all the online players */
    default void sendPacket(Packet packet) {
       // sendPacket(Sponge.getServer().getOnlinePlayers(), packet);
        // todo capture online players from mc source
    }

    /** Send the packet to the collection of players */
    void sendPacket(Collection<ProxyServerPlayerEntity> players, Packet packet);

    /** Send the packet with the offset to the player */
    default void sendPacket(ProxyServerPlayerEntity player, Packet packet, long offset, TimeUnit unit) {
        sendPacket(ImmutableList.of(player), packet, offset, unit);
    }

    /** Send the packet with the offset to the set of players */
    default void sendPacket(Packet packet, long offset, TimeUnit unit, ProxyServerPlayerEntity... players) {
        sendPacket(ImmutableSet.copyOf(players), packet, offset, unit);
    }

    /** Send the packet with the offset to all the online players */
    default void sendPacket(Packet packet, long offset, TimeUnit unit) {
        //sendPacket(Sponge.getServer().getOnlinePlayers(), packet, offset, unit);
        // todo capture online players from mc source

    }

    /** Send the packet with the offset to the collection of players */
    void sendPacket(Collection<ProxyServerPlayerEntity> players, Packet packet, long offset, TimeUnit unit);

    /** Repeat the packet with the delay to the player */
    default void repeatPacket(ProxyServerPlayerEntity player, Packet packet, long delay, TimeUnit unit) {
        repeatPacket(ImmutableList.of(player), packet, delay, unit);
    }

    /** Repeat the packet with the delay to the set of players */
    default void repeatPacket(Packet packet, long delay, TimeUnit unit, ProxyServerPlayerEntity... players) {
        repeatPacket(ImmutableSet.copyOf(players), packet, delay, unit);
    }

    /** Repeat the packet with the delay to all the players */
    default void repeatPacket(Packet packet, long offset, TimeUnit unit) {
        //repeatPacket(Sponge.getServer().getOnlinePlayers(), packet, offset, unit);
    }

    /** Repeat the packet with the delay to the collection of players */
    void repeatPacket(Collection<ProxyServerPlayerEntity> players, Packet packet, long delay, TimeUnit unit);

    /** Register the consumer for all the players the packet listener */
    void registerListener(PacketType packetType, PacketListener consumer);

    /** Is the manager listening to the current packet type */
    boolean containsListener(PacketType packetType);

    /** Get the listener for the packet type */
    PacketListener getListener(PacketType packetType);

    /** Remove the listener for the packet type */
    void removeListener(PacketType packetType);
}

/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.protocol;

import com.ewized.minecraft.proxy.entity.player.ProxyEntityPlayerMP;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.util.AttributeKey;
import net.year4000.utilities.Conditions;
import net.year4000.utilities.ErrorReporter;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/** The packet manager that inject packets into the netty pipeline */
public class PacketManager implements Packets {
    public static final AttributeKey<ProxyEntityPlayerMP> PLAYER_KEY = AttributeKey.valueOf("player");
    public static final AttributeKey<PacketManager> PACKET_MANAGER_KEY = AttributeKey.valueOf("packet_manager");
    private static final Map<Class<?>, PacketManager> managers = Maps.newConcurrentMap();
    //private final Scheduler scheduler;
    final UUID id = UUID.randomUUID();
    final Map<Class<?>, PacketListener> listeners = Maps.newConcurrentMap();
    //final String plugin;

    /** Creates the manages and register listeners ect */
    protected PacketManager(Object plugin) {
        Conditions.nonNull(plugin, "plugin");
//        Sponge.getEventManager().registerListeners(plugin, this);
//        scheduler = Scheduler.builder().executor(Sponge.getScheduler().createAsyncExecutor(plugin)).build();
//        this.plugin = Sponge.getPluginManager().fromInstance(plugin).get().getId();
    }

    /** Used for unit tests */
    @VisibleForTesting
    PacketManager() {
        //scheduler = Scheduler.builder().build();
        //plugin = "utilities";
    }

    /** Only one PacketManager per instance from Packets.manager() */
    public static PacketManager get(Object plugin) {
        Class<?> clazz = plugin.getClass();
        managers.putIfAbsent(clazz, new PacketManager(plugin));
        return managers.get(clazz);
    }

    /** Does the map contain any listeners*/
    @Override
    public boolean containsListener(PacketType packetType) {
        Conditions.nonNull(packetType, "packetType");
        return containsListener(PacketTypes.fromType(packetType).getOrThrow("packetType"));
    }

    /** Does the map contain a listener, internal use ignores checks */
    @VisibleForTesting
    boolean containsListener(Class<?> clazz) {
        return listeners.get(clazz) != null;
    }

    /** Get the listener for the packet and player */
    @Override
    public PacketListener getListener(PacketType packetType) {
        Conditions.nonNull(packetType, "packetType");
        return getListener(PacketTypes.fromType(packetType).getOrThrow("packetType"));
    }

    /** Get the listener for the type and player, internal use ignores checks */
    @VisibleForTesting
    PacketListener getListener(Class<?> clazz) {
        return listeners.get(clazz);
    }

    /** Remove the listener for the packet type */
    @Override
    public void removeListener(PacketType packetType) {
        Conditions.nonNull(packetType, "packetType");
        removeListener(PacketTypes.fromType(packetType).getOrThrow("packetType"));
    }

    /** Remove the listener unit test method */
    @VisibleForTesting
    void removeListener(Class<?> clazz) {
        listeners.remove(clazz);
    }

    /** The implementation of sending a custom packet to the player */
    @Override
    public void sendPacket(Collection<ProxyEntityPlayerMP> players, Packet packet) {
        Conditions.nonNull(packet, "packet");
        Conditions.nonNull(players, "players");
        try {
            players.stream().map(ProxyEntityPlayerMP::of).forEach(player -> player.sendPacket(packet));
        } catch (Throwable throwable) {
            ErrorReporter.builder(throwable)
                .hideStackTrace()
                .add("Player(s): ", players)
                .add("Packet ID: ", Integer.toHexString(packet.packetType().id()))
                .add("Packet State: ", PacketTypes.State.values()[packet.packetType().state()])
                .add("Packet Bounded: ", PacketTypes.Binding.values()[packet.packetType().bounded()])
                .add("Packet Class: ", packet.mcPacketClass())
                .add("Packet Object: ", packet.mcPacket())
                .buildAndReport(System.err);
        }
    }

    @Override
    public void sendPacket(Collection<ProxyEntityPlayerMP> players, Packet packet, long offset, TimeUnit unit) {
        //scheduler.run(() -> sendPacket(players, packet), (int) offset, unit);
    }

    @Override
    public void repeatPacket(Collection<ProxyEntityPlayerMP> players, Packet packet, long delay, TimeUnit unit) {
        //scheduler.repeat(() -> sendPacket(players, packet), (int) delay, unit);
    }

    /** The implementation on listing for packets */
    @Override
    public void registerListener(PacketType packetType, PacketListener consumer) {
        Conditions.nonNull(packetType, "packetType");
        Conditions.nonNull(consumer, "consumer");
        Class<?> clazz = PacketTypes.fromType(packetType).getOrThrow();
        registerListener(clazz, consumer);
    }

    /** Register the listener, used for the unit test */
    @VisibleForTesting
    void registerListener(Class<?> clazz, PacketListener consumer) {
        listeners.put(clazz, consumer);
    }

    /**
     * This is where we inject our custom netty handler to capture packets incoming and out going.
     */
    public void onPlayerLogin(ProxyEntityPlayerMP playerEntity) {
        try {
            //ProxyEntityPlayerMP proxy = ProxyEntityPlayerMP.of(playerEntity);
            Channel channel = playerEntity.netHandlerPlayServer().networkManager().channel();
            channel.attr(PLAYER_KEY).set(playerEntity);
            channel.attr(PACKET_MANAGER_KEY).set(this);
            ChannelPipeline pipeline = channel.pipeline();
            if (pipeline.get(PipelineHandles.INBOUND_NAME) == null) {
                String where = (pipeline.get("fml:packet_handler") != null) ? "fml:packet_handler" : "packet_handler";
                pipeline.addBefore(where, PipelineHandles.INBOUND_NAME, PipelineHandles.INBOUND_HANDLER);
            }
            if (pipeline.get(PipelineHandles.OUTBOUND_NAME) == null) {
                pipeline.addAfter("packet_handler", PipelineHandles.OUTBOUND_NAME, PipelineHandles.OUTBOUND_HANDLER);
            }
        } catch (Throwable throwable) {
            ErrorReporter.builder(throwable)
                .hideStackTrace()
                .add("Could not inject the packet interceptor for: ", playerEntity) // todo print the player name and maybe the uuid
                .buildAndReport(System.err);
        }
    }
}

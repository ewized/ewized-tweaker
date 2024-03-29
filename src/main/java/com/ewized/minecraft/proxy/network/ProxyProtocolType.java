/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.proxy.network;

import com.google.common.collect.BiMap;
import net.year4000.utilities.Conditions;
import net.year4000.utilities.reflection.Gateways;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Invoke;
import net.year4000.utilities.reflection.annotations.Proxied;
import net.year4000.utilities.reflection.annotations.Static;
import com.ewized.minecraft.protocol.PacketType;

import java.util.Map;

@Proxied("jb") // net/minecraft/network/ProtocolType
public interface ProxyProtocolType {
    /** Get the proxy for the object */
    static ProxyProtocolType of(Object object) {
        Conditions.nonNull(object, "object");
        return Gateways.proxy(ProxyProtocolType.class, object);
    }

    /** Get the static version of this proxy */
    @Static
    static ProxyProtocolType get() {
        return Gateways.proxy(ProxyProtocolType.class);
    }

    /** Get the object that this proxy is using */
    Object $this();

    @Getter(signature = "Ljava/util/Map;", index = 1)
    Map<Object, BiMap<Integer, Class<?>>> classMap();

    /** Use magic to get the object of the packet */
    @Static
    default Class<?> packet(PacketType type) {
        ProxyEnumPacketDirection direction = ProxyEnumPacketDirection.get();
        return classMap().get(direction.value(type.bounded())).get(type.id());
    }

    // enum

    @Invoke
    @Static
    Object[] values();

    /** Get the enum value by the index */
    @Static
    default ProxyProtocolType value(int index){
        Object[] values = values();
        Conditions.inRange(index, -1, values.length);
        return of(values[index]);
    }
}

/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.protocol;

import com.ewized.minecraft.proxy.entity.player.ProxyEntityPlayerMP;

import java.util.function.BiFunction;

@FunctionalInterface
public interface PacketListener extends BiFunction<ProxyEntityPlayerMP, Packet, Boolean> {
    /** Should the event be ignored, this leaves the packet to be handled normally */
    boolean IGNORE = false;

    /** Should the event be canceled, prevents the server/client ever knowing about it */
    boolean CANCEL = true;

    /** Return true to stop the packet */
    @Override
    Boolean apply(ProxyEntityPlayerMP player, Packet packet);
}

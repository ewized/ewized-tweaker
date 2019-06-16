package com.ewized;

import com.ewized.minecraft.protocol.Packets;

public interface Ewized {

    /** Get the packets service */
    static Packets packets() {
        return EwizedManager.get().packets;
    }
}

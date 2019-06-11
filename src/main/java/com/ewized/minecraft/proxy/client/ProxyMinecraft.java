package com.ewized.minecraft.proxy.client;

import net.year4000.utilities.reflection.Gateways;
import net.year4000.utilities.reflection.annotations.Bridge;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Proxied;
import net.year4000.utilities.reflection.annotations.Static;

@Proxied("cvk")
public interface ProxyMinecraft {

    @Static
    static ProxyMinecraft get() {
        return Gateways.proxy(ProxyMinecraft.class);
    }

    @Getter(signature = "Lcvk;")
    @Bridge(ProxyMinecraft.class)
    ProxyMinecraft instance();

    @Getter("aW")
    String debugProfilerName();


    /** Underlying instance */
    Object $this();
}

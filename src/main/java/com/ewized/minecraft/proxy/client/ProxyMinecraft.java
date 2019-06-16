package com.ewized.minecraft.proxy.client;

import net.year4000.utilities.reflection.Gateways;
import net.year4000.utilities.reflection.annotations.Bridge;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Proxied;
import net.year4000.utilities.reflection.annotations.Static;

@Proxied(value = "cvk", init = true)
public interface ProxyMinecraft {

    @Getter(signature = "Lcvk;")
    @Bridge(ProxyMinecraft.class)
    ProxyMinecraft instance();

    @Getter("aW")
    String debugProfilerName();


    /** Underlying instance */
    Object $this();
}

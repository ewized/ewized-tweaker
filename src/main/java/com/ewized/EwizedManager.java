package com.ewized;

import com.ewized.minecraft.protocol.Packets;
import com.ewized.minecraft.proxy.server.ProxyMinecraftServer;
import net.year4000.utilities.reflection.Reflections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** This is the main class that will serve at the mod platform */
public final class EwizedManager implements Ewized {
    private static EwizedManager manager;
    Packets packets;
    ProxyMinecraftServer minecraftServer;

    /** Get the singleton instance of this */
    public static EwizedManager get() {
        if (EwizedManager.manager == null) {
            EwizedManager.manager = new EwizedManager();
        }
        return EwizedManager.manager;
    }

    /** Only be init by the get methods */
    private EwizedManager() {
        this.packets = Packets.manager(this);
        this.minecraftServer = null;
    }


//    /** Main class to launch a server instance */
//    public static void main(String[] args) throws Exception {
//        Logger logger = LogManager.getLogger("ewized");
//        logger.info("Launching minecraft client...");
//
//        Class<?> clazz = Reflections.clazz("net.minecraft.client.main.Main").get();
//        logger.info(clazz);
//        Object obj = Reflections.instance(clazz, (Object[]) args).get();
//        logger.info(obj);
//
//    }
}
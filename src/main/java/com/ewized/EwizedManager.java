package com.ewized;

import com.ewized.minecraft.protocol.PacketListener;
import com.ewized.minecraft.protocol.PacketTypes;
import com.ewized.minecraft.protocol.Packets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** This is the main class that will serve at the mod platform */
public final class EwizedManager implements Ewized {
    private final Logger logger = LogManager.getLogger("ewized");
    private static EwizedManager manager;
    Packets packets;

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
        test();
    }

    private void test() {
        this.logger.info("Ewized Mod Loader");
        this.packets.registerListener(PacketTypes.PLAY_SERVER_CHAT_MESSAGE, ((player, packet) -> {
            this.logger.info("player said something...");
            return PacketListener.IGNORE;
        }));
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

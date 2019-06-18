package com.ewized;

import com.ewized.minecraft.protocol.PacketListener;
import com.ewized.minecraft.protocol.PacketManager;
import com.ewized.minecraft.protocol.PacketTypes;
import com.ewized.minecraft.protocol.Packets;
import com.ewized.minecraft.proxy.server.ProxyMinecraftServer;
import io.netty.channel.ChannelFuture;
import io.netty.channel.epoll.EpollEventLoopGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** This is the main class that will serve at the mod platform */
public final class EwizedManager implements Ewized {
    private final Logger logger = LogManager.getLogger("ewized");
    private static EwizedManager manager;
    Packets packets;
    ProxyMinecraftServer minecraftServer;

    /** Get the singleton instance of this */
    public static EwizedManager get() {
        return EwizedManager.manager;
    }

    /** Only be init by the get methods */
    public EwizedManager(ProxyMinecraftServer minecraftServer) {
        EwizedManager.manager = this;
        this.minecraftServer = minecraftServer;
        this.packets = Packets.manager(this);
        test();
    }

    private void test() {
        this.logger.info("Ewized Mod Loader");
        this.logger.info(this.minecraftServer);


        // todo look into the Network Manager and figure out how to hook in our PipelineHandler to auto inject player packets and such
        //((PacketManager) this.packets).onPlayerLogin(null);
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

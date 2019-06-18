package com.ewized.minecraft.mixin.server;

import com.ewized.Ewized;
import com.ewized.EwizedManager;
import com.ewized.minecraft.protocol.PacketManager;
import com.ewized.minecraft.proxy.network.ProxyNetworkManager;
import com.ewized.minecraft.proxy.server.ProxyMinecraftServer;
import io.netty.channel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.SocketAddress;
import java.util.List;

@Mixin(net.minecraft.server.MinecraftServer.class)
public class MixinMinecraftServer {
    private Logger logger = LogManager.getLogger("ewized");
    private EwizedManager ewizedManager;

    /**
     * Let it be known that this is the ewized tweaker mod system
     */
    @Overwrite()
    public String getServerModName() {
        return "ewized";
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        // networksystem has been inited at this point...
        this.logger.info("HAHAA init TAIL");
        // Init the manager system here so other classes can use it but after we have access to stuff we need
        this.ewizedManager = new EwizedManager(ProxyMinecraftServer.of(this));
    }

    @Inject(method = "run", at = @At(value = "INVOKE_ASSIGN", target = "d"))
    public void onRun(CallbackInfo ci) {
        // should be after the server is inited the the network system has the network managers
        this.logger.info("after server has been inited init TAIL");
        this.logger.info(Ewized.minecraftServer().networkSystem().endpoints());
        this.logger.info(Ewized.minecraftServer().networkSystem().networkManagers());
        for (ChannelFuture future : Ewized.minecraftServer().networkSystem().endpoints()) {
            this.logger.info(future.channel());
            this.logger.info(future.channel().pipeline());
            this.logger.info(future.channel().pipeline());
            future.addListener(fut -> {
                MixinMinecraftServer.this.logger.info("future listener()");

            });
            // we must register something here to capture the connection of the player to inject the packet injector
            future.channel().pipeline().addLast(new ChannelDuplexHandler() {
                @Override
                public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                    ctx.connect(remoteAddress, localAddress, promise);
                    promise.addListener(fute -> {
                        MixinMinecraftServer.this.logger.info("ChannelDuplexHandler future listener()");
                    });
                    MixinMinecraftServer.this.logger.info("ChannelHandlerContext connect()");
                    MixinMinecraftServer.this.logger.info(ctx);
                    MixinMinecraftServer.this.logger.info(Ewized.minecraftServer().networkSystem().networkManagers());

                }

                @Override
                public void read(ChannelHandlerContext ctx) throws Exception {
                    MixinMinecraftServer.this.logger.info("ChannelHandlerContext read()");
                    MixinMinecraftServer.this.logger.info(ctx);
                    MixinMinecraftServer.this.logger.info("try to inject...");
                    List<Object> endpoints = Ewized.minecraftServer().networkSystem().networkManagers();
                    MixinMinecraftServer.this.logger.info(endpoints);
                    synchronized (endpoints) {
                        MixinMinecraftServer.this.logger.info(ctx.channel().pipeline());
                        // if this work the protocol system should work
                        ((PacketManager) Ewized.packets()).injectPacketInjector(ctx.channel());
                        MixinMinecraftServer.this.logger.info(ctx.channel().pipeline());
                        MixinMinecraftServer.this.logger.info("loop");
                        for (Object networkManager : endpoints) {
                            MixinMinecraftServer.this.logger.info(ctx.channel().pipeline());
                            // if this work the protocol system should work
                            ((PacketManager) Ewized.packets()).injectPacketInjector(ctx.channel());
                            MixinMinecraftServer.this.logger.info(ctx.channel().pipeline());
                        }
                    }
                    ctx.read();
                }
            });
        }
    }
}

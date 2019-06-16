package com.ewized.minecraft.mixin.server;

import com.ewized.EwizedManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    // Init the manager system here so other classes can use it
    public EwizedManager ewizedManager = EwizedManager.get();

    /** Let it be known that this is the ewized tweaker mod system */
    @Overwrite
    public String getServerModName() {
        return "ewized";
    }
}

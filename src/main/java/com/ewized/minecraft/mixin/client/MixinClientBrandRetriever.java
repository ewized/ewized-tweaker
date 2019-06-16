package com.ewized.minecraft.mixin.client;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class MixinClientBrandRetriever {

    /** Let it be known that this is the ewized tweaker mod system */
    @Overwrite
    public static String getClientModName() {
        return "ewized";
    }
}

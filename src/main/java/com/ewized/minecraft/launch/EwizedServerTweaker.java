package com.ewized.minecraft.launch;

public class EwizedServerTweaker extends EwizedTweaker {
    @Override
    public String getLaunchTarget() {
        return "net.minecraft.server.MinecraftServer";
    }

    @Override
    public String[] getLaunchArguments() {
        return new String[0];
    }
}

package com.ewized.minecraft.launch;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinTweaker;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class  EwizedTweaker implements ITweaker {
    private List<String> argsOriginal = new ArrayList<>();
    private List<String> args = new ArrayList<>();
    private Logger logger = LogManager.getLogger("ewized");
    private String profile = "ewized";
    private MixinTweaker mixinTweaker = new MixinTweaker();

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.mixinTweaker.acceptOptions(args, gameDir, assetsDir, profile);
        this.logger.log(Level.INFO, "acceptOptions");
        this.args.addAll(args);
        this.argsOriginal.addAll(args);
        this.profile = profile;
        this.logger.log(Level.INFO, args);
        this.logger.log(Level.INFO, gameDir);
        this.logger.log(Level.INFO, assetsDir);
        this.logger.log(Level.INFO, profile);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        this.mixinTweaker.injectIntoClassLoader(launchClassLoader);
       this. logger.log(Level.INFO, "injectIntoClassLoader");
        this.logger.info(launchClassLoader);
        this.logger.info(launchClassLoader.getTransformers());
        this.logger.info(launchClassLoader.getSources());
        Mixins.addConfiguration("mixins.tweaker.core.json");
    }

    @Override
    public String getLaunchTarget() {
        this.logger.log(Level.INFO, "getLaunchTarget");
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        this.logger.log(Level.INFO, "getLaunchArguments");
        this.logger.log(Level.INFO, this.args.toString());

        this.args.add("--version");
        this.args.add(this.profile);

        try {
            // Found optifine use that instead
            this.logger.error(Launch.classLoader.findClass("optifine.OptiFineClassTransformer"));
            return new String[0];
        } catch (ClassNotFoundException e) {
            return this.args.toArray(new String[]{});
        }
    }
}


package com.ewized.minecraft.launch;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.year4000.utilities.reflection.Gateways;
import net.year4000.utilities.reflection.Reflections;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.MixinTweaker;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class  EwizedTweaker implements ITweaker {
    private List<String> args = new ArrayList<>();
    private Logger logger = LogManager.getLogger("ewized");
    private String profile = "ewized";
    public static LaunchClassLoader classLoader;
    MixinTweaker mixinTweaker = new MixinTweaker();


    public EwizedTweaker() {
        //MixinBootstrap.init();
//        logger.log(Level.INFO, OS.detect());
//        Runnable running = () -> {
//            File log = new File("log.log");
//            //Logger logger = LogManager.getLogger("ewized");
//            try {
//                FileWriter writer = new FileWriter(log);
//                while (true) {
//                    try {
//                        //logger.info("FINDING MC INSTANCE");
//                        String string = "\ninstance finder\n" ;
//                        //System.out.println("FINDING MC INSTANCE");
//                        //logger.info(ProxyMinecraft.get().instance());
//                        //System.out.println(ProxyMinecraft.get().instance());
//                        ProxyMinecraft proxy = ProxyMinecraft.get();
//                        string += (proxy.toString());
//                        string += ("\n");
//                        string += (proxy.instance().toString());
//                        string += ("\n");
//                        string += (proxy.instance().$this().toString());
//                        string += ("\n");
//                        string += (proxy.instance().debugProfilerName());
//
//                        writer.write(string);
//                        //writer.flush();
//
//                    } catch (Exception opps) {
//                        opps.printStackTrace(new PrintWriter(writer));
//                        System.out.println("opps");
//                    } finally {
//                        try {
//                            wait(5000);
//
//                        } catch (Exception e) {}
//                    }
//                }
//            } catch (IOException ioerror) {
//
//            }
//
//        };
//
//        Thread thread = new Thread(running);
//        thread.setDaemon(true);
//        thread.start();
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.mixinTweaker.acceptOptions(args, gameDir, assetsDir, profile);
        this.logger.log(Level.INFO, "acceptOptions");
        this.args.addAll(args);
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

        //launchClassLoader.registerTransformer("org.spongepowered.asm.launch.MixinTweaker");
//        launchClassLoader.registerTransformer("com.ewized.minecraft.launch.transformer.EwizedClassTransformer");
//        this.classLoader = launchClassLoader;
//        try {
//            Class<?> cvk = this.classLoader.findClass("cvk");
//            this.logger.info("clazz");
//            this.logger.info(cvk);
//            Field field = cvk.getField("N");
//            this.logger.info("N");
//            this.logger.info(field);
//            //ass<?> clazz = this.classLoader.findClass("com.ewized.minecraft.proxy.client.ProxyMinecraft");
//            this.logger.info("clazz");
//            //this.logger.info(clazz);
//            //this.logger.info(Gateways.proxy(clazz));
//        } catch (ClassNotFoundException | NoSuchFieldException error) {
//            this.logger.error(error);
//        }

        // Mixins
        //MixinEnvironment.getDefaultEnvironment().setSide(true ? MixinEnvironment.Side.CLIENT : MixinEnvironment.Side.SERVER);

    }

    @Override
    public String getLaunchTarget() {
        this.logger.log(Level.INFO, "getLaunchTarget");
        return "net.minecraft.client.main.Main";
        //return "net.minecraft.server.MinecraftServer";
        //return "com.ewized.EwizedManager"; // Testing using a wrapped Minecraft instance
    }

    @Override
    public String[] getLaunchArguments() {
        this.logger.log(Level.INFO, "getLaunchArguments");
        this.logger.log(Level.INFO, this.args.toString());

        this.args.add("--version");
        this.args.add(this.profile);

        return this.args.toArray(new String[]{});
    }
}


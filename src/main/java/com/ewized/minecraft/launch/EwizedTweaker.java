package com.ewized.minecraft.launch;

import com.ewized.minecraft.proxy.client.ProxyMinecraft;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.year4000.utilities.OS;
import net.year4000.utilities.Utils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class  EwizedTweaker implements ITweaker {
    private List<String> args = new ArrayList<>();
    private Logger logger = LogManager.getLogger("ewized");


    public EwizedTweaker() {
        logger.log(Level.INFO, OS.detect());
        Runnable running = () -> {
            File log = new File("log.log");
            //Logger logger = LogManager.getLogger("ewized");
            try {
                FileWriter writer = new FileWriter(log);
                while (true) {
                    try {
                        //logger.info("FINDING MC INSTANCE");
                        String string = "\ninstance finder\n" ;
                        //System.out.println("FINDING MC INSTANCE");
                        //logger.info(ProxyMinecraft.get().instance());
                        //System.out.println(ProxyMinecraft.get().instance());
                        ProxyMinecraft proxy = ProxyMinecraft.get();
                        string += (proxy.toString());
                        string += ("\n");
                        string += (proxy.instance().toString());
                        string += ("\n");
                        string += (proxy.instance().$this().toString());
                        string += ("\n");
                        string += (proxy.instance().debugProfilerName());

                        writer.write(string);
                        //writer.flush();

                    } catch (Exception opps) {
                        opps.printStackTrace(new PrintWriter(writer));
                        System.out.println("opps");
                    } finally {
                        try {
                            wait(5000);

                        } catch (Exception e) {}
                    }
                }
            } catch (IOException ioerror) {

            }

        };

        Thread thread = new Thread(running);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        logger.log(Level.INFO, "acceptOptions");
        this.args.addAll(args);
        logger.log(Level.INFO, args);
        logger.log(Level.INFO, gameDir);
        logger.log(Level.INFO, assetsDir);
        logger.log(Level.INFO, profile);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        logger.log(Level.INFO, "injectIntoClassLoader");
        //launchClassLoader.
    }

    @Override
    public String getLaunchTarget() {
        logger.log(Level.INFO, "getLaunchTarget");
        return "net.minecraft.client.main.Main";
        //return "net.minecraft.server.MinecraftServer";
        //return "com.ewized.EwizedManager"; // Testing using a wrapped Minecraft instance
    }

    @Override
    public String[] getLaunchArguments() {
        logger.log(Level.INFO, "getLaunchArguments");
        logger.log(Level.INFO, this.args.toString());

        this.args.add("--version");
        this.args.add("ewized");

        return this.args.toArray(new String[this.args.size()]);
    }
}


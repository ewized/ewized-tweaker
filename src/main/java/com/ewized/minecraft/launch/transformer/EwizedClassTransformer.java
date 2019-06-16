package com.ewized.minecraft.launch.transformer;

import com.ewized.ClientBrand;
import com.ewized.minecraft.launch.EwizedTweaker;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class EwizedClassTransformer implements IClassTransformer {
    private Logger logger = LogManager.getLogger("ewized");

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        this.logger.info("transform");
        this.logger.info(name);
//        this.logger.info(transformedName);
//        if ("net.minecraft.client.ClientBrandRetriever".equals(name)) {
//            this.logger.info("Injecting our client brand into client");
//            InputStream stream = ClassLoader.getSystemResourceAsStream("com.ewized.ClientBrand");
//            try {
//                return IOUtils.toByteArray(stream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if ("dlx".equals(name)) {
            this.logger.info("Injecting stuff...");
            try {
                Class<?> cvk = EwizedTweaker.classLoader.findClass("cvk");
                this.logger.error("clazz===========================");
                this.logger.info(cvk);
                Field field = cvk.getField("N");
                this.logger.info("N");
                this.logger.info(field);
                //ass<?> clazz = this.classLoader.findClass("com.ewized.minecraft.proxy.client.ProxyMinecraft");
                this.logger.info("clazz");
                //this.logger.info(clazz);
                //this.logger.info(Gateways.proxy(clazz));
            } catch (ClassNotFoundException | NoSuchFieldException error) {
                this.logger.error(error);
            }
        }
        return basicClass;
    }
}

/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.protocol;

import com.google.common.annotations.VisibleForTesting;
import net.year4000.utilities.Conditions;
import net.year4000.utilities.reflection.Reflections;
import net.year4000.utilities.value.TypeValue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/** Our packet class that bridges the minecraft type with our type */
public class Packet {
    private final PacketType type;
    private final Class<?> clazz;
    private Object injectedPacket;

    /** Create a packet from the type and fetch a new instance of the injected packet*/
    public Packet(PacketType type) {
        this(type, PacketTypes.fromType(type).get(), null);
    }

    /** Create a packet based on the object */
    public Packet(PacketType type, Object packet) {
        this(type, PacketTypes.fromType(type).get(), packet);
    }

    /** Used to create the packet instance */
    @VisibleForTesting
    Packet(PacketType type, Class<?> clazz, Object packet) {
        this.type = Conditions.nonNull(type, "type");
        this.clazz = Conditions.nonNull(clazz, "clazz");
        this.injectedPacket = (packet == null) ? Reflections.instance(clazz).getOrThrow() : packet;
        Conditions.condition(isOfClassType(injectedPacket), "packet");
    }

    /** The type this packet if for */
    public PacketType packetType() {
        return type;
    }

    /** Get the class type of the minecraft packet */
    public Class<?> mcPacketClass() {
        return clazz;
    }

    /** The instance of the injected packet */
    public Object mcPacket() {
        return injectedPacket;
    }

    /** Make sure the instance is of the class type */
    private boolean isOfClassType(Object instance) {
        return instance != null && mcPacketClass().isAssignableFrom(instance.getClass());
    }

    /** Transmute the packet's data */
    public Packet transmute(BiConsumer<Class<?>, Object> consumer) {
        consumer.accept(mcPacketClass(), mcPacket());
        return this;
    }

    /** Swap out the packet with a new instance, must be of the class type */
    public Packet inject(Object injectedPacket) {
        Conditions.condition(isOfClassType(injectedPacket), "injectedPacket"); // this will throw when instance is null
        this.injectedPacket = injectedPacket;
        return this;
    }

    /** Swap out the packet with a new instance, and provide the known class */
    public Packet inject(Function<Class<?>, Object> function) {
        return inject(Conditions.nonNull(function, "function").apply(mcPacketClass()));
    }

    /** Inject the map of fields into the instance */
    public Packet inject(Map<String, Object> fields) {
        return transmute((clazz, inst) -> fields.forEach((key, value) -> Reflections.setter(clazz, inst, key, value)));
    }

    /** Inject data of the packet using the packet builder */
    public Injector injector() {
        return new Injector(this);
    }

    /** A injector that will inject the data into the object's instance */
    public static class Injector {
        private final Packet packet;
        private final int length;
        private final Object[] values;
        private int fieldCounter;

        /** Create the values*/
        Injector(Packet packet) {
            this.packet = Conditions.nonNull(packet, "packet");
            this.length = packet.mcPacketClass().getDeclaredFields().length;
            this.values = new Object[length];
        }

        /** Allow skipping a ordered field */
        public Injector skip() {
            Conditions.isSmaller(fieldCounter++, length - 1);
            return this;
        }

        /** Set a field at the ordered the methods are chained at */
        public Injector add(Object object) {
            Conditions.isSmaller(fieldCounter, length - 1);
            add(fieldCounter++, object);
            return this;
        }

        /** Set a field at a specific instance */
        public Injector add(int index, Object object) {
            Conditions.inRange(index, 0, length - 1);
            values[index] = object;
            return this;
        }

        /** Inject the fields into the instance and return the injected instance */
        public Packet inject() {
            return packet.transmute((clazz, instance) -> {
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < length; i++) {
                    Object value = values[i];
                    if (value != null) {
                        Reflections.setter(instance, fields[i], value);
                    }
                }
            });
        }
    }

    /** Create a new accessor that will read the object */
    public Accessor accessor() {
        return new Accessor(mcPacket(), mcPacketClass());
    }

    /** Allow accessing fields from the packet */
    public static class Accessor {
        private final Object object;
        private final Field[] fields;
        private final int length;
        private int counter;

        Accessor(Object object, Class<?> clazz) {
            this.object = Conditions.nonNull(object, "object");
            this.fields = Conditions.nonNull(clazz, "clazz").getDeclaredFields();
            this.length = fields.length;
        }

        /** Allow skipping a ordered field */
        public Accessor skip() {
            Conditions.isSmaller(counter++, length - 1);
            return this;
        }

        /** Get the value from the order it was called */
        public TypeValue get() {
            Conditions.isSmaller(counter, length - 1);
            return get(counter++);
        }

        /** Get the field at the specific index */
        public TypeValue get(int index) {
            Conditions.inRange(index, 0, length - 1);
            return new TypeValue(Reflections.getter(object, fields[index]));
        }
    }
}

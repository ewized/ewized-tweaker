/*
 * Copyright 2016 Year4000. All Rights Reserved.
 */

package com.ewized.minecraft.proxy.entity;

import com.ewized.minecraft.proxy.network.datasync.ProxyEntityDataManager;
import net.year4000.utilities.Conditions;
import net.year4000.utilities.reflection.Gateways;
import net.year4000.utilities.reflection.annotations.Bridge;
import net.year4000.utilities.reflection.annotations.Getter;
import net.year4000.utilities.reflection.annotations.Invoke;
import net.year4000.utilities.reflection.annotations.Proxied;

@Proxied("aii") // net.minecraft.entity.Entity
public interface ProxyEntity {
    static ProxyEntity of(Object entity) {
        Conditions.nonNull(entity, "entity");
        return Gateways.proxy(ProxyEntity.class, entity);
    }

    /** Get the object that this proxy is using */
    Object $this();

    /** The hashcode of the entity is the hashCode of the object */
    @Invoke(value = "hashCode")
    int entityId();

    /** Get the data watcher for the entity */
    @Getter(signature = "Lqk;") // net/minecraft/network/datasync/EntityDataManager
    @Bridge(ProxyEntityDataManager.class)
    ProxyEntityDataManager dataManager();
}

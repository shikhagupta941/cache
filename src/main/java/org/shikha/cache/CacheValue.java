package org.shikha.cache;

import org.shikha.eviction.EvictionKeyHandle;

public class CacheValue<V> {
    private V value;

    private EvictionKeyHandle evictionKeyHandle;
    public void setValue(V value) {
        this.value = value;
    }

    public void setEvictionKeyHandle(EvictionKeyHandle evictionKeyHandle) {
        this.evictionKeyHandle = evictionKeyHandle;
    }

    public EvictionKeyHandle getEvictionKeyHandle() {
        return evictionKeyHandle;
    }

    public V getValue() {
        return value;
    }
}

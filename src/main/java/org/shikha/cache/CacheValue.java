package org.shikha.cache;

import org.shikha.eviction.EvictionKeyHandle;

/**#
 * This Class contains the cache value as well as different eviction key handler.
 * @param <V> Type of Value in cache.
 */
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
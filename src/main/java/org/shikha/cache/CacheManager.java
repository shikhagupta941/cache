package org.shikha.cache;

import org.shikha.configuration.CacheConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache Manger is used to create instances of Cache.
 */
public class CacheManager {

    private Map<String, Cache<?,?>> store = new HashMap<>();

    /**
     * This method creates a new instance of Cache.
     * @param cacheName cache name
     * @param cacheConfiguration configure cache e.g size of cache, eviction Policy and expiry policy
     * @return new instance of cache
     * @param <K> Type of Key
     * @param <V> Type of Value
     */
    public <K,V> Cache<K,V> createCache(String cacheName, CacheConfiguration cacheConfiguration) {
        Cache<K,V> cache = new Cache<>(cacheConfiguration);
        store.put(cacheName,cache);
        return cache;
    }

    /**
     * Returns the existing instance of Cache.
     * @param cacheName cache name
     * @return return the instance of cache.
     */
    public Cache<?,?> getCache(String cacheName) {
        return store.get(cacheName);
    }

    /**
     * Removes the cache instance.
     * @param cacheName: cache name.
     */
    public void removeCache(String cacheName) {
        store.remove(cacheName);
    }
}

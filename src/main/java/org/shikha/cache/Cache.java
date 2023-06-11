package org.shikha.cache;

import org.shikha.exception.CacheException;
import org.shikha.configuration.CacheConfiguration;
import org.shikha.events.EventType;
import org.shikha.eviction.EvictionKeyHandle;
import org.shikha.eviction.EvictionStrategy;
import org.shikha.eviction.LRUEvictionStrategy;
import org.shikha.expiry.ExpiryPolicy;
import org.shikha.expiry.NoExpiryPolicy;
import org.shikha.events.CacheEventListner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cache class is used to create the cache which can take any type of Key and Value.
 * Eviction Strategy is pluggable using the CacheConfiguration file and default Eviction is LRU.
 * Expiry policy is also pluggable using the CacheConfiguration file and by default there is no expiry policy.
 * @param <K> Type of Cache Key
 * @param <V> Type of Cache Value
 */
public class Cache<K,V> {
    private final CacheConfiguration cacheConfiguration;
    private final EvictionStrategy<K> evictionStrategy;

    private final ExpiryPolicy<K> expiryPolicy;


    //private Map<K,V> store = new HashMap<>();

    private final Map<K,CacheValue<V>> store = new HashMap<>();

    private final Map<String,CacheEventListner> listners = new HashMap<>();

    public Map<K, CacheValue<V>> getStore() {
        return store;
    }


    public Cache(CacheConfiguration cacheConfiguration) {
        if(cacheConfiguration.getEvictionStrategyFactory()!=null) {
            EvictionStrategy<K> evictionStrategy = cacheConfiguration.getEvictionStrategyFactory().newStrategy();
            this.evictionStrategy = evictionStrategy;
        } else {
            this.evictionStrategy = new LRUEvictionStrategy<>();
        }
        if(cacheConfiguration.getExpiryPolicyFactory() !=null) {
            ExpiryPolicy<K> expiryPolicy = cacheConfiguration.getExpiryPolicyFactory().newPolicy();
            this.expiryPolicy = expiryPolicy;
        } else {
            this.expiryPolicy = new NoExpiryPolicy<>();
        }
        this.cacheConfiguration = cacheConfiguration;
    }

    /**
     * Adding the value in cache.
     * If size is full then it checks if there is expiry policy then expire the keys as per policy.
     * If expiry policy is not configured then it checks for eviction strategy and evict the keys so that
     * a new element can be added into cache.
     * @param key cache key
     * @param value cache value
     */
    public void put(K key, V value) throws CacheException {
        CacheValue<V> cacheValue = new CacheValue<>();
        EvictionKeyHandle evictionKeyHandle=null;
        if(!store.containsKey(key)) {
            if(store.size() >= cacheConfiguration.getSize()) {
                List<K> evictedKeys = expiryPolicy.evict();
                if(evictedKeys.size()!=0) {
                    removeExpiredKeys(evictedKeys);
                }
                if(evictedKeys.size()==0) {
                    K evictedKey = evictionStrategy.evictKey();
                    if(evictedKey==null) {
                        throw new CacheException("Cache is full.");
                    }
                    notifyEvents(EventType.EVICTED);
                    store.remove(evictedKey);
                }
            }
            List<K> evictedKeys = expiryPolicy.add(key);
            notifyEvents(EventType.ADDED);
            removeExpiredKeys(evictedKeys);
            evictionKeyHandle = evictionStrategy.addKey(key);
        } else  {
            EvictionKeyHandle evictionKeyHandleTemp = store.get(key).getEvictionKeyHandle();
            evictionKeyHandle = evictionStrategy.updateKey(key,evictionKeyHandleTemp);
            List<K> evictedKeys = expiryPolicy.evict();
            removeExpiredKeys(evictedKeys);
            notifyEvents(EventType.UPDATED);
        }

        cacheValue.setValue(value);
        cacheValue.setEvictionKeyHandle(evictionKeyHandle);
        store.put(key,cacheValue);
    }


    /**
     * This function is called for every operation on cache like put(add/update), get.
     * So that data can be up to date.
     * @param keys : list of expired keys.
     */
    private void removeExpiredKeys(List<K> keys) {
        for(K key: keys) {
            store.remove(key);
            notifyEvents(EventType.EXPIRED);
        }

    }

    /**
     * Gets the value for a particular key in cache.
     * @param key key
     * @return it returns the cache value.
     */
    public V get(K key) {
        List<K> evictedKeys = expiryPolicy.evict();
        removeExpiredKeys(evictedKeys);
        if(store.get(key)!=null) {
            return store.get(key).getValue();
        }
        return null;
    }


    /**
     * This method is used to listen to events occurring on the cache.
     * Observer must implement the CacheEventListner interface.
     * @param name
     * @param lister
     */
    public void registerEventListner(String name, CacheEventListner lister) {
        listners.put(name,lister);
    }

    /**
     * This method is to deregister to cache events.
     * @param name : name of listener.
     */
    public void deregisterEventListner(String name) {

        listners.remove(name);
    }

    /**
     * This method is called to notify the different events after every operation of cached.
     * @param eventType It can be ADDED,UPDATE,EVICTED,EXPIRED.
     */
    private void notifyEvents(EventType eventType) {
        for(Map.Entry<String,CacheEventListner> entry: listners.entrySet()) {
            entry.getValue().notifyEvent(eventType);
        }
    }
}
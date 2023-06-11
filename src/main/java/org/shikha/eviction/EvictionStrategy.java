package org.shikha.eviction;

/**
 * This interface needs to be implemented to provide Eviction Strategy.
 * @param <K> Type of cache Key.
 */
public interface EvictionStrategy<K> {
        public K evictKey();
        public EvictionKeyHandle addKey(K key);

        public EvictionKeyHandle updateKey(K key, EvictionKeyHandle evictionKeyHandle);
}
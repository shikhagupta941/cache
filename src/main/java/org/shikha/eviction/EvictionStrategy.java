package org.shikha.eviction;

public interface EvictionStrategy<K> {
        public K evictKey();
        public EvictionKeyHandle addKey(K key);

        public EvictionKeyHandle updateKey(K key, EvictionKeyHandle evictionKeyHandle);
}

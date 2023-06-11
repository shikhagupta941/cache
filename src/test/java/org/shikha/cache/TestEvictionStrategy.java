package org.shikha.cache;

import org.shikha.eviction.EvictionKeyHandle;
import org.shikha.eviction.EvictionStrategy;

public class TestEvictionStrategy<K> implements EvictionStrategy<K> {


    public TestEvictionStrategy() {

    }


    @Override
    public K evictKey() {
       return null;
    }

    @Override
    public EvictionKeyHandle addKey(K key) {
      return null;
    }

    @Override
    public EvictionKeyHandle updateKey(K key, EvictionKeyHandle evictionKeyHandle) {
        return null;
    }
}

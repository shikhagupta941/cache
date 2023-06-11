package org.shikha.eviction;

/**
 * Factory class to provide the instance of LRU Eviction Strategy.
 */
public class LRUEvictionStrategyFactory implements EvictionStrategyFactory{
    @Override
    public <K> EvictionStrategy<K> newStrategy() {
        return new LRUEvictionStrategy<>();
    }
}
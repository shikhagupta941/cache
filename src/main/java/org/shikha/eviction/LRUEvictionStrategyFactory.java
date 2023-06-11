package org.shikha.eviction;

public class LRUEvictionStrategyFactory implements EvictionStrategyFactory{
    @Override
    public <K> EvictionStrategy<K> newStrategy() {
        return new LRUEvictionStrategy<>();
    }
}

package org.shikha.cache;

import org.shikha.eviction.EvictionStrategy;
import org.shikha.eviction.EvictionStrategyFactory;

public class TestEvictionStrategyFactory implements EvictionStrategyFactory {
    @Override
    public <K> EvictionStrategy<K> newStrategy() {
        return new TestEvictionStrategy<>();
    }
}

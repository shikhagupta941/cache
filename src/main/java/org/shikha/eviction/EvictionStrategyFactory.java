package org.shikha.eviction;

/**
 * This factory interface needs to implement to create the specific instance of Eviction Strategy.
 */
public interface EvictionStrategyFactory{
    public <K> EvictionStrategy<K> newStrategy() ;
}
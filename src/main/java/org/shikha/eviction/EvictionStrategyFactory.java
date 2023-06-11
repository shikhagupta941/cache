package org.shikha.eviction;

public interface EvictionStrategyFactory{
public <K> EvictionStrategy<K> newStrategy() ;
}

package org.shikha.eviction;

/**
 * This is used to handle the Eviction key as per specific implementation.
 */
public interface EvictionKeyHandle {
    public <T> T get();
}
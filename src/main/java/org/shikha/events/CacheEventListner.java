package org.shikha.events;

/**
 * This interface needs to be implemented to listen to cache events.
 */
public interface CacheEventListner {
    public void notifyEvent(EventType eventType);
}
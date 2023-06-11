package org.shikha.cache;

import org.shikha.events.CacheEventListner;
import org.shikha.events.EventType;

public class CacheListner implements CacheEventListner {

    private int totalEvents=0;
    @Override
    public void notifyEvent(EventType eventType) {
        totalEvents+=1;

    }
    public int getTotalEvents() {
        return totalEvents;
    }
}

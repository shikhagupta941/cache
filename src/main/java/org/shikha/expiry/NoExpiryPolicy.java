package org.shikha.expiry;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to provide no expiry policy and which is default configuration.
 * @param <K> type of key.
 */
public class NoExpiryPolicy<K> implements ExpiryPolicy<K> {
    public NoExpiryPolicy() {
    }
    @Override
    public List<K> add(K key) {
        return new ArrayList<>();
    }
    @Override
    public List<K> evict() {
        return new ArrayList<>();
    }

}
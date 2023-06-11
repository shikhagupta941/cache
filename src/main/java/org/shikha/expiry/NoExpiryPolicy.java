package org.shikha.expiry;

import java.util.ArrayList;
import java.util.List;

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

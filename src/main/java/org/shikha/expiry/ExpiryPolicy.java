package org.shikha.expiry;

import java.util.List;

public interface ExpiryPolicy<K> {
        public List<K> evict();
        public List<K> add(K key);
}

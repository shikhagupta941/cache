package org.shikha.expiry;

import java.util.List;
/**
 * This interface needs to be implemented to provide Expiry Policy.
 * @param <K> Type of cache Key.
 */
public interface ExpiryPolicy<K> {
        public List<K> evict();
        public List<K> add(K key);
}
package org.shikha.expiry;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class is used to provide TTL expiry policy.
 * @param <K> type of key.
 */
public class TTLExpiryPolicy<K> implements ExpiryPolicy<K> {

    Long ttl;
    public TTLExpiryPolicy(Long ttl) {
        this.ttl = ttl;
    }

    public Long getTtl() {
        return ttl;
    }

    private class DataNode {
        private K key;
        private long timestamp;

        public DataNode(K key, long timestamp) {
            this.key= key;
            this.timestamp= timestamp;
        }

        public K getKey() {
            return key;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
    private PriorityQueue<DataNode> queue = new PriorityQueue<>((DataNode o1, DataNode o2) ->
            (int) (o1.getTimestamp() - o2.getTimestamp()));

    /**
     * Key is added to priority queue with currentTimeStamp+ ttl/
     * @param key key
     * @return return list of expired keys after add operation.
     */
    @Override
    public List<K> add(K key) {
        queue.add(new DataNode(key,System.currentTimeMillis()+ttl));
        return evict();
    }

    /**
     * Evicts the expired keys from queue.
     * @return list of expired keys.
     */
    @Override
    public List<K> evict() {
        List<K> keys = new ArrayList<>();
        long currentTimestamp = System.currentTimeMillis();
        while(!queue.isEmpty()&& queue.peek().getTimestamp()<=currentTimestamp) {
            DataNode node = queue.poll();
            keys.add(node.getKey());
        }
        return keys;
    }

}
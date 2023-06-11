package org.shikha.eviction;

/**
 * LRUEvictionStrategy is used to evict the least recently keys.
 * @param <K>: type of cache key.
 */
public class LRUEvictionStrategy<K> implements  EvictionStrategy<K>{

    Node head,tail;

    public LRUEvictionStrategy() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    private class Node implements EvictionKeyHandle {
        private K key;
        Node prev;
        Node next;
        public Node(K key) {
            this.key = key;
        }
        public Node() {

        }

        @Override
        public <Node> Node get() {
            return (Node) this;
        }

        public K getKey() {
            return key;
        }
    }


    /**
     * this method remove the least recently used key from doubly linked List.
     * @return returns the evicted key so that cache can remove the entry from its map.
     */
    @Override
    public K evictKey() {
        Node temp = tail.prev;
        tail.prev.next=tail;
        tail.prev= temp.prev;
        return temp.getKey();
    }

    /**
     * This method is used to add the key in doubly linked List.
     * @param key key which needs to be tracked.
     * @return It returns the reference of added key in Node so that
     * it can be used to update key and user needs not to iterrate the whole list.
     */
    @Override
    public EvictionKeyHandle addKey(K key) {
        Node newNode = new Node(key);
        newNode.next = head.next;
        newNode.prev = head;
        newNode.next.prev = newNode;
        head.next = newNode;
        return newNode;
    }

    /**
     * This method is used to update the key.
     * @param key key
     * @param evictionKeyHandle to get the pointer of node which needs to be updated.
     * @return EvictionKeyHandle to refer again in Doubly Linked List.
     */
    @Override
    public EvictionKeyHandle updateKey(K key, EvictionKeyHandle evictionKeyHandle) {
        Node node = evictionKeyHandle.get();
        // removing link of node
        node.prev.next= node.next;
        node.next.prev= node.prev;
        //attaching node at the head
        node.next = head.next;
        node.prev = head;
        node.next.prev = node;
        head.next = node;
        return node;
    }
}
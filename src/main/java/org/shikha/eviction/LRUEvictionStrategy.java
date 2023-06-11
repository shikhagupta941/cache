package org.shikha.eviction;

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


    @Override
    public K evictKey() {
        Node temp = tail.prev;
        tail.prev.next=tail;
        tail.prev= temp.prev;
        return temp.getKey();
    }

    @Override
    public EvictionKeyHandle addKey(K key) {
        Node newNode = new Node(key);
        newNode.next = head.next;
        newNode.prev = head;
        newNode.next.prev = newNode;
        head.next = newNode;
        return newNode;
    }

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

package ru.ifmo.klepov.lru;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache<K, V>{
    private final LinkedList<K> queue = new LinkedList<>();
    private final HashMap<K, V> map = new HashMap<>();

    private final int cacheCapacity;

    LRUCache(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("maximum size must be positive");
        }
        cacheCapacity = cap;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    int size() {
        int ans = queue.size();

        assert ans <= cacheCapacity;

        return ans;
    }

    int maxSize() {
        return cacheCapacity;
    }

    Boolean isEmpty() {
        return size() == 0;
    }

    V get(K key) {
        final int sizeBefore = size();

        V ans = null;
        if (map.containsKey(key)) {
            ans = map.get(key);
            queue.remove(key);
            queue.addFirst(key);
            assert(queue.contains(key));
        }

        assert(size() == sizeBefore);
        return ans;
    }

    public V put(K key, V value) {
        final int sizeBefore = size();
        final boolean contained = contains(key);

        V ans = map.get(key);
        if (ans != null) {
            queue.remove(key);
        } else {
            if (queue.size() == cacheCapacity) {
                map.remove(queue.removeLast());
                assert (queue.size() == cacheCapacity - 1);
            }
        }
        assert(!queue.contains(key));

        map.put(key, value);
        queue.addFirst(key);

        assert(get(key) == value);
        assert(size() == sizeBefore && (contained || sizeBefore == maxSize()) ||
                sizeBefore < maxSize() && !contained && size() == sizeBefore + 1);

        return ans;
    }

    public V erase(K key) {
        final int sizeBefore = size();

        V ans = map.get(key);

        if (ans != null) {
            map.remove(key);
            queue.remove(key);
            assert(!map.containsKey(key));
        }

        assert(size() == sizeBefore - 1 || size() == sizeBefore);

        return ans;
    }
}

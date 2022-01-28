package ru.ifmo.klepov.lru;

import org.junit.Assert;
import org.junit.Test;

public class LRUCacheTest {
    final Integer k1 = 0xf314;
    final Integer k2 = 0xf315;
    final Integer k3 = 0xf316;
    final String v1 = "1";
    final String v2 = "2";
    final String v3 = "3";

    @Test
    public void Test0() {
        final int maxSize = 10;
        LRUCache<Integer, String> cache = new LRUCache<>(maxSize);

        Assert.assertEquals(cache.size(), 0);
        Assert.assertEquals(cache.maxSize(), maxSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Test0_1() {
        new LRUCache<>(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Test0_2() {
        new LRUCache<>(0);
    }

    @Test
    public void Test1() {
        LRUCache<Integer, String> cache = new LRUCache<>(10);

        Assert.assertTrue(!cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && cache.isEmpty() && cache.size() == 0);
        String prevVal = cache.erase(k1);
        Assert.assertNull(prevVal);

        prevVal = cache.put(k1, v1);
        Assert.assertTrue(cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && !cache.isEmpty() && cache.size() == 1);
        Assert.assertNull(prevVal);
        Assert.assertEquals(cache.get(k1), v1);

        prevVal = cache.put(k1, v2);
        Assert.assertTrue(cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && !cache.isEmpty() && cache.size() == 1);
        Assert.assertEquals(prevVal, v1);
        Assert.assertEquals(cache.get(k1), v2);
        Assert.assertTrue(cache.contains(k1));
    }

    @Test
    public void Test2() {
        final int maxSize = 10;
        LRUCache<Integer, String> cache = new LRUCache<>(maxSize);

        Assert.assertTrue(!cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && cache.isEmpty() && cache.size() == 0);

        cache.put(k3, v3);
        Assert.assertTrue(!cache.contains(k1) && !cache.contains(k2) && cache.contains(k3) && !cache.isEmpty() && cache.size() == 1);

        cache.put(k2, v2);
        Assert.assertTrue(!cache.contains(k1) && cache.contains(k2) && cache.contains(k3) && !cache.isEmpty() && cache.size() == 2);

        cache.put(k1, v1);
        Assert.assertTrue(cache.contains(k1) && cache.contains(k2) && cache.contains(k3) && !cache.isEmpty() && cache.size() == 3);

        String prevVal = cache.erase(k2);
        Assert.assertTrue(cache.contains(k1) && !cache.contains(k2) && cache.contains(k3) && !cache.isEmpty() && cache.size() == 2);
        Assert.assertEquals(prevVal, v2);

        prevVal = cache.erase(k2);
        Assert.assertTrue(cache.contains(k1) && !cache.contains(k2) && cache.contains(k3) && !cache.isEmpty() && cache.size() == 2);
        Assert.assertNull(prevVal);

        prevVal = cache.erase(k3);
        Assert.assertTrue(cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && !cache.isEmpty() && cache.size() == 1);
        Assert.assertEquals(prevVal, v3);

        prevVal = cache.erase(k1);
        Assert.assertTrue(!cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && cache.isEmpty() && cache.size() == 0);
        Assert.assertEquals(prevVal, v1);
    }

    @Test
    public void Test3() {
        final int maxSize = 2;
        LRUCache<Integer, String> cache = new LRUCache<>(maxSize);

        Assert.assertTrue(!cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && cache.isEmpty() && cache.size() == 0);

        cache.put(k1, v1);
        Assert.assertTrue(cache.contains(k1) && !cache.contains(k2) && !cache.contains(k3) && !cache.isEmpty() && cache.size() == 1);

        cache.put(k2, v2);
        Assert.assertTrue(cache.contains(k1) && cache.contains(k2) && !cache.contains(k3) && !cache.isEmpty() && cache.size() == 2);

        cache.get(k1);

        cache.put(k3, v3);
        Assert.assertTrue(cache.contains(k1) && !cache.contains(k2) && cache.contains(k3) && !cache.isEmpty() && cache.size() == 2);
    }
}
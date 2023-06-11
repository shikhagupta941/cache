package org.shikha.cache;

import org.junit.jupiter.api.Test;
import org.shikha.exception.CacheException;
import org.shikha.configuration.CacheConfiguration;
import org.shikha.expiry.TTLExpiryPolicyFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {


    /**
     * This test case is used to test when there is no eviction and expired policy configured
     * while creating cache then default LRU eviction Policy is applied.
     * when size of cache is full then least recently used key is removed which is key2.
     */
    @Test
    public void testCacheWithDefaultConfiguration() {
        CacheConfiguration cacheConfiguration= new CacheConfiguration.CacheConfigurationBuilder(5L)
                .build();
        CacheManager cacheManager = new CacheManager();
        Cache<String,String> cache  = cacheManager.createCache("cache1",cacheConfiguration);
        cache.put("key1","val1");
        cache.put("key2","val2");
        cache.put("key3","val3");
        cache.put("key4","val4");
        cache.put("key5","val5");
        cache.put("key1","val11");
        cache.put("key6","val6");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertAll(
                () -> assertEquals("val11",cache.get("key1")),
                () -> assertNull(cache.get("key2")),
                () -> assertNotNull(cache.get("key3")),
                () -> assertNotNull(cache.get("key4")),
                () -> assertNotNull(cache.get("key5")),
                () -> assertNotNull(cache.get("key6"))
        );

    }

    /**
     * This test case is used to test when TTL expired policy is configured
     * and default LRU eviction Policy is applied.
     * when size of cache is full then least recently used key is removed which is key2
     * as keys are not expired yet.
     * Once keys are expired those are also removed from cache.
     */
    @Test
    public void testCacheWithTTLExpiryPolicyAndDefaultEvictionStrategy() {
        CacheConfiguration cacheConfiguration= new CacheConfiguration.CacheConfigurationBuilder(5L)
                .setExpiryPolicy(new TTLExpiryPolicyFactory(100L)).build();
        CacheManager cacheManager = new CacheManager();
        Cache<String,String> cache  = cacheManager.createCache("cache1",cacheConfiguration);
        cache.put("key1","val1");
        cache.put("key2","val2");
        cache.put("key3","val3");
        cache.put("key4","val4");
        cache.put("key5","val5");
        cache.put("key1","val11");
        cache.put("key6","val6");
        assertAll(
                () -> assertEquals("val11", cache.get("key1")),
                () -> assertNull(cache.get("key2")),
                () -> assertNotNull(cache.get("key3")),
                () -> assertNotNull(cache.get("key4")),
                () -> assertNotNull(cache.get("key5")),
                () -> assertNotNull(cache.get("key6"))
        );
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertAll(
                () -> assertNull(cache.get("key1")),
                () -> assertNull(cache.get("key3")),
                () -> assertNull(cache.get("key4")),
                () -> assertNull(cache.get("key5")),
                () -> assertNull(cache.get("key6"))
        );

    }

    /**
     * This test case is used to test when TTL expired policy is configured
     * and No eviction Policy is configured.
     * when size of cache is full then it through exception as keys are not expired and
     * eviction policy is not doing anything.
     */
    @Test
    public void testCacheWithTTLExpiryPolicyAndCustomizedEvictionStrategy() {
        CacheConfiguration cacheConfiguration= new CacheConfiguration.CacheConfigurationBuilder(5L)
                .setEvictionStrategy(new TestEvictionStrategyFactory()).build();
        CacheManager cacheManager = new CacheManager();
        Cache<String,String> cache  = cacheManager.createCache("cache1",cacheConfiguration);
        cache.put("key1","val1");
        cache.put("key2","val2");
        cache.put("key3","val3");
        cache.put("key4","val4");
        cache.put("key5","val5");
        cache.put("key1","val11");

        CacheException exception= assertThrows(CacheException.class, () -> {
            cache.put("key6","val6");
        });

        String expectedMessage = "Cache is full.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * This test case tests that cache with different types of Key and Value can be created.
     */
    @Test
    public void testCacheWithDifferentTypes() {
        CacheConfiguration cacheConfiguration= new CacheConfiguration.CacheConfigurationBuilder(1L).build();
        CacheManager cacheManager = new CacheManager();
        Cache<String,String> cache  = cacheManager.createCache("cache1",cacheConfiguration);
        cache.put("key1","val1");
        assertEquals("val1",cache.get("key1"));

        Cache<String,Integer> cache1  = cacheManager.createCache("cache1",cacheConfiguration);
        cache1.put("key1",1);
        assertEquals(1,cache1.get("key1"));
    }

    /**
     *  This test case tests 6 events occurs as following:
     *  eventType:ADDED
     *  eventType:ADDED
     *  eventType:ADDED
     *  eventType:UPDATED
     *  eventType:EVICTED
     *  eventType:ADDED
     *  As first three put calls to cache generate add event and next put generate update event
     *  as key is already there.As size of cache is full so next put will first evict the LRU key
     *  and then add event is generated.
     */
    @Test
    public void testCacheEvents() {
        CacheConfiguration cacheConfiguration= new CacheConfiguration.CacheConfigurationBuilder(3L).build();
        CacheManager cacheManager = new CacheManager();
        Cache<String,String> cache  = cacheManager.createCache("cache1",cacheConfiguration);

        CacheListner listner = new CacheListner();
        cache.registerEventListner("listner1",listner);

        cache.put("key1","val1");
        cache.put("key2","val2");
        cache.put("key3","val3");
        cache.put("key1","val11");
        cache.put("key6","val6");
        assertAll(
                () -> assertEquals(6, listner.getTotalEvents())
        );
    }

}
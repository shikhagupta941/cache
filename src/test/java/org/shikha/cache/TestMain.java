package org.shikha.cache;

import org.junit.jupiter.api.Test;
import org.shikha.exception.CacheException;
import org.shikha.configuration.CacheConfiguration;
import org.shikha.expiry.TTLExpiryPolicyFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

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

    @Test
    /**
     *
     */
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

    @Test
    /**
     *
     */
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

    public void testCacheEvents() {
        CacheConfiguration cacheConfiguration= new CacheConfiguration.CacheConfigurationBuilder(3L)
                .setExpiryPolicy(new TTLExpiryPolicyFactory(100L)).build();
        CacheManager cacheManager = new CacheManager();
        Cache<String,String> cache  = cacheManager.createCache("cache1",cacheConfiguration);

        CacheListner listner = new CacheListner();
        cache.registerEventListner("listner1",listner);

        cache.put("key1","val1");
        cache.put("key2","val2");
        cache.put("key3","val3");
        cache.put("key1","val11");
        cache.put("key6","val6");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cache.get("key1");
        assertAll(
                () -> assertEquals(6, listner.getTotalEvents())
        );
    }

}

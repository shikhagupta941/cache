package org.shikha.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import org.shikha.configuration.CacheConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCache {

    private Cache<String,String> cache;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CacheConfiguration cacheConfiguration = new CacheConfiguration.CacheConfigurationBuilder(5L).build();
        cache = new Cache<>(cacheConfiguration);
    }

    @Test
    public void testPut_Successfully() {
        cache.put("key1","val1");
        assertTrue(cache.getStore().size()==1);

    }

    @Test
    public void testGet_Successfully() {
        cache.put("key1","val1");
        cache.put("key2","val2");
        String val = cache.get("key1");
        assertEquals(val,"val1");
    }
}

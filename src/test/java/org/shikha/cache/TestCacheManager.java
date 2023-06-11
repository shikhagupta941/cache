package org.shikha.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import org.shikha.configuration.CacheConfiguration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;

public class TestCacheManager {
    @InjectMocks
    private CacheManager cacheManager;

    private static Map<String,Cache<String,String>> store;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CacheConfiguration cacheConfiguration = new CacheConfiguration.CacheConfigurationBuilder(5L).build();
        Cache<String,String> cacheT = new Cache<>(cacheConfiguration);
        store = spy(new HashMap<>());
    }

    @Test
    public void testCreateCache_Successfully() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration.CacheConfigurationBuilder(5L).build();
        Cache<String,String> actual = cacheManager.createCache("cache1", cacheConfiguration);
        assertAll(
                () -> assertNotNull(actual)
        );
    }
    /*@Test
    public void testGetCache_Successfully() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration.CacheConfigurationBuilder(5L).build();
        Cache<String,String> cacheT = new Cache<>(cacheConfiguration);
        store.put("cache1",cacheT);
        Mockito.verify(store).put("cache1",cacheT);
        Cache<String,String> actual = (Cache<String, String>) cacheManager.getCache("cache1");
        assertAll(
                () -> assertNotNull(actual)
        );
    }*/


    @Test
    public void testRemoveCache_Successfully() {
        cacheManager.removeCache("cache1");
    }
}

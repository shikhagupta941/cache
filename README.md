# Javadoc
 Java doc can be found at path https://shikhagupta941.github.io/cache/javadoc/index.html
 
# CacheManager
  Cache Manager class can be used to create the cache.

# Cache
  Cache class is used to create the cache which can take any type of Key and Value.
  Eviction Strategy is pluggable using the CacheConfiguration and default Eviction Strategy is LRU.
  Expiry policy is also pluggable using the CacheConfiguration and by default there is no expiry policy.
 
# Eviction Strategy
  Eviction Strategy is pluggable using the CacheConfiguration. LRU implementation has been provided in the code.
  A new eviction strategy can be created by implementing the EvictionStrategy interface.
  An instance of eviction strategy can be created by implementing the EvictionStrategyFactory interface.
 
# Expiry Policy
  The expiry policy is also pluggable using the CacheConfiguration.TTL implementation has been provided in the code.
  A new expiry policy can be created by implementing the ExpiryPolicy interface.
  An instance of an expiry policy can be created by implementing the ExpiryPolicyFactory interface.
 
# CacheConfiguration
  The following properties can be configured for cache.
  a) Size of Cache.
  b) Expiry Policy
  c) Eviction Strategy.
 Size is the only mandatory property, other properties can be pluggable as per requirement.
 
 # Usage
 ```
        CacheConfiguration cacheConfiguration= new CacheConfiguration.CacheConfigurationBuilder(5L)
                .setEvictionStrategy(new LRUEvictionStrategyFactory()).setExpiryPolicy(new TTLExpiryPolicyFactory())
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
        Thread.sleep(1000);
        String val = cache.get("key1");
        System.out.println(val);
        String val1 = cache.get("key2");
        System.out.println(val1);
 ```
 
 # Test
 A few test cases have been added in TestMain class which is testing different combinations of Expiry policy and Eviction strategy and more.
 
 # Deployment
   A service could be built using this library where Rest Api's can be mapped to cache manager and cache class apis.
   Following are the examples:
   | Rest Api | Cache Api |
   | -------- | -------   |
   | Post /caches| cacheManager.createCache() |
   | Delete /caches/{cacheId}/ | cacheManager.removeCache() |
   | Post  /cahches/{cacheId}/ | cacheManager.getCache().put() |
   | Get  /cahches/{cacheId}/ | cacheManager.getCache().get() |
   
   

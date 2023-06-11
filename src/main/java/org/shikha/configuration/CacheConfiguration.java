package org.shikha.configuration;

import org.shikha.eviction.EvictionStrategyFactory;
import org.shikha.expiry.ExpiryPolicyFactory;

public class CacheConfiguration {
    private EvictionStrategyFactory evictionStrategyFactory;
    private Long size;

    private ExpiryPolicyFactory expiryPolicyFactory;

    private CacheConfiguration(CacheConfigurationBuilder cacheConfigurationBuilder) {
    this.evictionStrategyFactory = cacheConfigurationBuilder.evictionStrategyFactory;
    this.size = cacheConfigurationBuilder.size;
    this.expiryPolicyFactory = cacheConfigurationBuilder.expiryPolicyFactory;
    }

    public EvictionStrategyFactory getEvictionStrategyFactory() {
        return evictionStrategyFactory;
    }

    public Long getSize() {
        return size;
    }

    public ExpiryPolicyFactory getExpiryPolicyFactory() {
        return expiryPolicyFactory;
    }

    public static class CacheConfigurationBuilder {
        private EvictionStrategyFactory evictionStrategyFactory;
        private Long size;

        private ExpiryPolicyFactory expiryPolicyFactory;

        private Long ttl;
        public CacheConfigurationBuilder(Long size) {
            this.size = size;
        }

        public CacheConfigurationBuilder setEvictionStrategy(EvictionStrategyFactory  evictionStrategyFactory) {
            this.evictionStrategyFactory = evictionStrategyFactory;
            return this;
        }

        public CacheConfigurationBuilder setExpiryPolicy(ExpiryPolicyFactory  expiryPolicyFactory) {
            this.expiryPolicyFactory = expiryPolicyFactory;
            return this;
        }

        public CacheConfigurationBuilder setTtl(Long ttl) {
            this.ttl = ttl;
            return this;
        }

        public CacheConfiguration build(){
            return new CacheConfiguration(this);
        }
    }
}

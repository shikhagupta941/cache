package org.shikha.expiry;

/**
 * Factory class to provide the instance of TTL Expiry Policy.
 */
public class TTLExpiryPolicyFactory implements ExpiryPolicyFactory {

    private Long ttl;
    public TTLExpiryPolicyFactory(Long ttl) {
        this.ttl = ttl;
    }
    @Override
    public <K> ExpiryPolicy<K> newPolicy() {
        return new TTLExpiryPolicy<>(ttl);
    }
}
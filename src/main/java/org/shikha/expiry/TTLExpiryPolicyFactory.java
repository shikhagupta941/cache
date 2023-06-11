package org.shikha.expiry;

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

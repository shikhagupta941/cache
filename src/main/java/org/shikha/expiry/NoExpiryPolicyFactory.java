package org.shikha.expiry;

public class NoExpiryPolicyFactory implements ExpiryPolicyFactory {
    @Override
    public <K> ExpiryPolicy<K> newPolicy() {
        return new NoExpiryPolicy<>();
    }
}

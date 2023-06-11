package org.shikha.expiry;

/**
 * Factory class to provide the instance of No Expiry Policy.
 */
public class NoExpiryPolicyFactory implements ExpiryPolicyFactory {
    @Override
    public <K> ExpiryPolicy<K> newPolicy() {
        return new NoExpiryPolicy<>();
    }
}
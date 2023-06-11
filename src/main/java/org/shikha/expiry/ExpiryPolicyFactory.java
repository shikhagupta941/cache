package org.shikha.expiry;

/**
 * This factory interface needs to implement to create the specific instance of Expiry Policy.
 */
public interface ExpiryPolicyFactory {
    public <K> ExpiryPolicy<K> newPolicy() ;
}
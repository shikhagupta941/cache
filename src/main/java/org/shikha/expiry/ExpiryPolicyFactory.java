package org.shikha.expiry;

public interface ExpiryPolicyFactory {
public <K> ExpiryPolicy<K> newPolicy() ;
}

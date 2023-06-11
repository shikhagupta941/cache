package org.shikha.exception;

/**
 * Cache Exception is thrown to handle exceptions.
 */
public class CacheException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CacheException() {
        super();
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
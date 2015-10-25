package com.github.teenak77.hrdemo.exception;

/**
 * Created by teenak on 2015/02/11.
 */
public class JdbcOptimisticLockException extends AppException {

    public JdbcOptimisticLockException() {
        super();
    }

    public JdbcOptimisticLockException(String message) {
        super(message);
    }

    public JdbcOptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcOptimisticLockException(Throwable cause) {
        super(cause);
    }

    protected JdbcOptimisticLockException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

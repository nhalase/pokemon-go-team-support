package org.nhalase.slack;

public final class InvalidRequestContextException extends Exception {

    public InvalidRequestContextException() {
        super();
    }

    public InvalidRequestContextException(String message) {
        super(message);
    }

    public InvalidRequestContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestContextException(Throwable cause) {
        super(cause);
    }

    protected InvalidRequestContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

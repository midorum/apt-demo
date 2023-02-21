package org.example.exceptions;

public class InvalidBeanException extends Exception {
    public InvalidBeanException(final String message) {
        super(message);
    }

    public InvalidBeanException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

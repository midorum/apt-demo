package org.example.exceptions;

public class DuplicateBeanException extends Exception {
    public DuplicateBeanException(final String message) {
        super(message);
    }

    public DuplicateBeanException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

package com.example.Attyre.Assignment.Exception;

public class UserPreferenceNotFoundException extends RuntimeException{
    public UserPreferenceNotFoundException() {
    }

    public UserPreferenceNotFoundException(String message) {
        super(message);
    }

    public UserPreferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserPreferenceNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserPreferenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

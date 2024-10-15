package com.example.Attyre.Assignment.Exception;

public class UserPreferenceNotRegisteredException extends RuntimeException{
    public UserPreferenceNotRegisteredException() {
    }

    public UserPreferenceNotRegisteredException(String message) {
        super(message);
    }

    public UserPreferenceNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserPreferenceNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public UserPreferenceNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

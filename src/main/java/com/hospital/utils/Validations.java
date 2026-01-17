package com.hospital.utils;

public class Validations {
    public static <T> T checkNotNull(T value, String errorMessage) {
        if (value == null) {
            throw new RuntimeException(errorMessage);
        }
        return value;
    }
}

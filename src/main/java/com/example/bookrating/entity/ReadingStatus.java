package com.example.bookrating.entity;

public enum ReadingStatus {
    READY(0),
    READING(1),
    STOPPED(2),
    FINISHED(3);

    private int code;

    ReadingStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ReadingStatus fromCode(int code) {
        for (ReadingStatus status : ReadingStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status code: " + code);
    }
}

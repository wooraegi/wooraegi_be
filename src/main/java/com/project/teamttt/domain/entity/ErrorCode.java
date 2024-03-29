package com.project.teamttt.domain.entity;

public enum ErrorCode {
    EMPTY_FILE_EXCEPTION("Empty file exception"),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("IO exception on image upload"),
    NO_FILE_EXTENTION("No file extension"),
    INVALID_FILE_EXTENTION("Invalid file extension"),
    PUT_OBJECT_EXCEPTION("Put object exception"),
    IO_EXCEPTION_ON_IMAGE_DELETE("IO exception on image delete");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package com.project.sanhak.util.s3;

public enum ErrorCode {
    EMPTY_FILE_EXCEPTION("File is empty", 400),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("I/O exception occurred while uploading image", 500),
    IO_EXCEPTION_ON_IMAGE_DELETE("I/O exception occurred while deleting image", 500),
    IO_EXCEPTION_ON_FILE_UPLOAD("I/O exception occurred while uploading file", 500),
    IO_EXCEPTION_ON_FILE_DELETE("I/O exception occurred while deleting file", 500),
    NO_FILE_EXTENSION("File has no extension", 400),
    INVALID_FILE_EXTENSION("Invalid file extension", 400),
    PUT_OBJECT_EXCEPTION("Exception occurred while putting object to S3", 500), IO_EXCEPTION_ON_FILE_DOWNLOAD("download error", 500);
    private final String message;
    private final int statusCode;

    ErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
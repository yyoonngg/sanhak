package com.project.sanhak.util.s3;


public class S3Exception extends RuntimeException {
    private final ErrorCode errorCode;

    public S3Exception(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {
        return errorCode.getStatusCode();
    }

    @Override
    public String toString() {
        return "S3Exception{" +
                "errorCode=" + errorCode +
                ", message=" + getMessage() +
                ", statusCode=" + getStatusCode() +
                '}';
    }
}

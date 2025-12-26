package com.s4r.ghorbari.core.exception;

public class ServiceException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] messageArgs;

    public ServiceException(ErrorCode errorCode, Object... messageArgs) {
        super(errorCode.formatMessage(messageArgs));
        this.errorCode = errorCode;
        this.messageArgs = messageArgs;
    }

    public ServiceException(ErrorCode errorCode, Throwable cause, Object... messageArgs) {
        super(errorCode.formatMessage(messageArgs), cause);
        this.errorCode = errorCode;
        this.messageArgs = messageArgs;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public int getCode() {
        return errorCode.getCode();
    }
}

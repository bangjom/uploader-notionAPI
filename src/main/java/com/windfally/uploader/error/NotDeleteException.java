package com.windfally.uploader.error;

public class NotDeleteException extends BusinessException {
    public NotDeleteException(String message) { super(message, GlobalErrorCode.NOT_DELETE_ERROR); }
}

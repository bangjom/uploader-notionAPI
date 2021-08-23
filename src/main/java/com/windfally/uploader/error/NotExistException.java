package com.windfally.uploader.error;

public class NotExistException extends  BusinessException{
    public NotExistException(String message) {
        super(message,GlobalErrorCode.NOT_EXIST);
    }
}

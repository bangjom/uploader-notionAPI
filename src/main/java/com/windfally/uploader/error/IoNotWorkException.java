package com.windfally.uploader.error;

public class IoNotWorkException extends  BusinessException{
    public IoNotWorkException(String message) {
        super(message,GlobalErrorCode.IO_NOT_WORK);
    }
}

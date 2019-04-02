package com.deng.common.utils;

import com.deng.common.exception.AppException;
import com.deng.common.exception.ErrorCode;

public class PageFuckerException extends AppException{
    private static final long serialVersionUID = 1L;

    /**
     * 无参构造函数
     */
    public PageFuckerException() {
        super();
    }
    public PageFuckerException(Throwable e) {
        super(e);
    }
    public PageFuckerException(ErrorCode errorType) {
        super(errorType);
    }

    public PageFuckerException(ErrorCode errorCode, String ... errMsg) {
        super(errorCode, errMsg);
    }
    /**
     * 封装异常
     * @param errorCode
     * @param errMsg
     * @param isTransfer 是否转换异常信息，如果为false,则直接使用errMsg信息
     */
    public PageFuckerException(ErrorCode errorCode, String errMsg,Boolean isTransfer) {
        super(errorCode, errMsg,isTransfer);
    }

    public PageFuckerException(ErrorCode errCode, Throwable cause,String ... errMsg) {
        super(errCode,cause, errMsg);
    }
}


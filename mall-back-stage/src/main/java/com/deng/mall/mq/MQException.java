package com.deng.mall.mq;


import com.deng.common.exception.AppException;
import com.deng.common.exception.ErrorCode;

public class MQException extends AppException {
    private static final long serialVersionUID = 1L;


    /**
     * 无参构造函数
     */
    public MQException() {
        super();
    }
    public MQException(Throwable e) {
        super(e);
    }
    public MQException(ErrorCode errorType) {
        super(errorType);
    }

    public MQException(ErrorCode errorCode, String ... errMsg) {
        super(errorCode, errMsg);
    }
    /**
     * 封装异常
     * @param errorCode
     * @param errMsg
     * @param isTransfer 是否转换异常信息，如果为false,则直接使用errMsg信息
     */
    public MQException(ErrorCode errorCode, String errMsg,Boolean isTransfer) {
        super(errorCode, errMsg,isTransfer);
    }

    public MQException(ErrorCode errCode, Throwable cause,String ... errMsg) {
        super(errCode,cause, errMsg);
    }
}

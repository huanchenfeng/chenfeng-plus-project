package com.chenfeng.base.execption;

public class chenfengPlusException extends RuntimeException {
    private String errMessage;
    public chenfengPlusException() {
        super();
    }
    public chenfengPlusException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }
    public String getErrMessage() {
        return errMessage;
    }
    public static void cast(CommonError commonError){
        throw new chenfengPlusException(commonError.getErrMessage());
    }
    public static void cast(String errMessage){
        throw new chenfengPlusException(errMessage);
    }
}
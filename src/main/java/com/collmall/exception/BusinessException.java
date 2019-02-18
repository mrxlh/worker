package com.collmall.exception;

import com.collmall.result.ErrorCode;

/**
* Title: BusinessException.java<br>
* @author xulihui
 *@date  2019年01月25日
*/
public   class BusinessException extends ServiceException {

private static final long serialVersionUID = -6003868869041167435L;

public BusinessException(String errorMsg) {
super(ConstantUtil.OpCode.BUSINESS_EXCEPTION,errorMsg);
}

public BusinessException(int code, String errorMsg) {
super(code,errorMsg);
}

public BusinessException(ErrorCode code, Exception ex) {
super(code.getCode(),ex.getMessage());
}


public BusinessException(ErrorCode code, String errorMsg) {
super(code.getCode(),errorMsg);
}
}


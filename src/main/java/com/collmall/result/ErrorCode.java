package com.collmall.result;

public enum ErrorCode {

	OK(0, "操作成功"),
	NOT_LOGIN(101, "未登陆"),
	LOGINED(102, "已登陆"),
	RESOURCE_NOT_EXIST(201, "资源不存在"),
	RESOURCE_EXIST(202, "资源已存在"),
	RESOURCE_LOCK(203, "资源被占用"),
	REPEAT_OPERATE(204, "重复操作"),
	INVALID_PARAM(301, "参数错误"),
	BUSINESS_ERROR(302, "业务错误"),
	DB_ERROR(303, "数据库错误"),
	IO_ERROR(304, "网络错误"),
	PERMISSION_DENIED(401, "权限不足"),
	SERVER_ERROR(500, "服务器内部错误"),
	SERVER_BUSY(501, "服务器繁忙"),
	REGULAR_ERROR(999, "操作失败");

	private int code;
	private String message;

	private ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static ErrorCode getByCode(int code) {
		ErrorCode[] values = ErrorCode.values();
		for (ErrorCode value : values) {
			if (value.getCode() == code)
				return value;
		}
		return null;
	}

}

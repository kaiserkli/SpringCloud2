package com.example.common.web.response;

/**
 * 消息表示枚举类
 * @author Lit
 * @since 2017/05/08
 */
public enum ResponseMessage {
	
	INVOKE_SUCCESS("0000", "访问成功"),
	INVOKE_FAILURE("1000", "访问失败"),
    TOKEN_TIMEOUT("2000", "TOKEN超时");
	
	private String code;
	private String message;
	
	private ResponseMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}

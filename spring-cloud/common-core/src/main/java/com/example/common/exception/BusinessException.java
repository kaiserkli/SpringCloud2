package com.example.common.exception;

import com.example.common.web.response.ResponseMessage;

/**
 * 自定义运行时异常处理类.
 * @author Lit
 * @since 2017/05/08
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -8324192027482171489L;
	private String code;

	public BusinessException(ResponseMessage responseMessage) {
		super(responseMessage.getMessage());
		this.code = responseMessage.getCode();
	}

	public BusinessException(String message) {
		super(message);
		this.code = ResponseMessage.INVOKE_FAILURE.getCode();
	}

	public String getCode() {
		return code;
	}
}

package com.example.common.web.response;

/**
 * 返回消息体结构.
 * @author Lit
 * @since 2017/05/04
 * @param <E> 泛型实体
 */
public class ResponseEntity<E> {
	/**
	 * 信息编码.
	 * <p>一般数据定义及来源于{@link ResponseMessage}.
	 * <p>同时也可自定义,建议相关信息定义到{@link ResponseMessage}类中.
	 */
	private String code;
	
	/**
	 * 信息详情.
	 * <p>一般数据定义及来源于{@link ResponseMessage}.
	 * <p>同时也可自定义,建议相关信息定义到{@link ResponseMessage}类中.
	 */
	private String message;
	
	/**
	 * 实际返回数据，用泛型自定义其返回结构.
	 * <p>当返回错误或抛异常信息时，该属性为NULL,否则根据业务需要返回相应结构.
	 */
	private E data;
	
	public ResponseEntity() {
		this.code = ResponseMessage.INVOKE_SUCCESS.getCode();
		this.message = ResponseMessage.INVOKE_SUCCESS.getMessage();
	}
	
	public ResponseEntity(ResponseMessage responseMessage) {
		super();
		this.code = responseMessage.getCode();
		this.message = responseMessage.getMessage();
	}
	
	public ResponseEntity(ResponseMessage responseMessage, E data) {
        super();
        this.code = responseMessage.getCode();
        this.message = responseMessage.getMessage();
        this.data = data;
    }
	
	public ResponseEntity(E data) {
		super();
		if (null == data) {
			this.code = ResponseMessage.INVOKE_FAILURE.getCode();
			this.message = ResponseMessage.INVOKE_FAILURE.getMessage();
		} else {
			this.code = ResponseMessage.INVOKE_SUCCESS.getCode();
			this.message = ResponseMessage.INVOKE_SUCCESS.getMessage();
		}
		this.data = data;
	}
	
	public ResponseEntity(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResponseEntity(String code, String message, E data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ResponseEntity(ResponseMessage responseMessage, String message, E data) {
		super();
		this.code = responseMessage.getCode();
		this.message = message;
		this.data = data;
	}

	public ResponseEntity(ResponseMessage responseMessage, String message) {
		super();
		this.code = responseMessage.getCode();
		this.message = message;
	}

	public static ResponseEntity factory() {
		return new ResponseEntity();
	}

	public static ResponseEntity factory(ResponseMessage responseMessage) {
		return new ResponseEntity(responseMessage);
	}

	public static ResponseEntity factory(String code, String message) {
		return new ResponseEntity(code, message);
	}

	public static ResponseEntity factory(ResponseMessage responseMessage, String message) {
		return new ResponseEntity(responseMessage, message);
	}

	/**
	 * 获取信息编码.
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code 信息编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message 信息详情
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return E 泛型实体
	 */
	public E getData() {
		return data;
	}

	/**
	 * 
	 * @param data 数据结构
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * 重写toString()方法.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Response Entity [code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append(", data=");
		if (null != data) {
			builder.append(data.toString());
		}
		builder.append("]");
		return builder.toString();
	}
}

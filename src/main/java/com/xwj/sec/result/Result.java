package com.xwj.sec.result;

public class Result<T> {

	private int code;

	private String msg;

	private T data;

	/**
	 * 成功
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(data);
	}

	/**
	 * 失败
	 */
	public static <T> Result<T> error(CodeMsg msgCode) {
		return new Result<T>(msgCode);
	}

	private Result(T data) {
		this.code = 0;
		this.msg = "success";
		this.data = data;
	}

	private Result(CodeMsg msgCode) {
		this.code = msgCode.getCode();
		this.msg = msgCode.getMsg();
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

}

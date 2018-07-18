package com.xwj.sec.exception;

import com.xwj.sec.result.CodeMsg;

/**
 * 全局异常
 * 
 * @author xuwenjin
 */
public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private CodeMsg cm;

	public GlobalException(CodeMsg cm) {
		super(cm.toString());
		this.cm = cm;
	}

	public CodeMsg getCm() {
		return cm;
	}

}

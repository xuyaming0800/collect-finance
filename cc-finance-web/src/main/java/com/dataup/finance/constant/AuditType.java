package com.dataup.finance.constant;

public enum AuditType {
	// 1财务审核
	FINANCETYPE(1);
	private int code;
	
	public int getCode() {
		return code;
	}

	private AuditType(int code) {
		this.code = code;
	}

}

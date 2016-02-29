package com.dataup.finance.constant;

public enum TaskStatus {
	// 0 提交申请 1审核通过 2审核不通过
	SUMITAPPLY(0),AUDITING(0),AUDITPASS(1), AUDITNOPASS(2);
	private int code;
	
	public int getCode() {
		return code;
	}

	private TaskStatus(int code) {
		this.code = code;
	}

}

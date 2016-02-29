package com.dataup.finance.constant;

public enum ApplyType {
	// 1 充值 2垫付 3退款 4 余额阀值修改 5单价设置 6扣款（扣客户款）
	RECHARGE(1),ADVANCED(2), REFUND(3),THRESHOLD(4),UNITPRICE(5),DEBIT(6);
	private int code;
	
	public int getCode() {
		return code;
	}

	private ApplyType(int code) {
		this.code = code;
	}

}

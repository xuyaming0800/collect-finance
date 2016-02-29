package com.dataup.finance.entity;

import java.io.Serializable;
import java.util.List;

import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;

public class ApplyDetailEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4477649899718604125L;
	private ApplyRecord applyRecord;
	private List<?> details;
	
	public ApplyRecord getApplyRecord() {
		return applyRecord;
	}
	public void setApplyRecord(ApplyRecord applyRecord) {
		this.applyRecord = applyRecord;
	}
	public List<?> getDetails() {
		return details;
	}
	public void setDetails(List<?> details) {
		this.details = details;
	}

}

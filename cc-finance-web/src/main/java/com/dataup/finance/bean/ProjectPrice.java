package com.dataup.finance.bean;

import java.io.Serializable;

public class ProjectPrice  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6568510978510456371L;
	
	private String id;//主键
	private String ownerId;//项目Id
	private String customId;//客户Id
	private double advanceAmount;//垫付金额
	private double thresholdAmount;//阀值金额
	private double balanceAmount;//余额
	private long createTime;//创建时间
	private long updateTime;//更新时间
	
	private double totalPay;//累计支付金额
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public double getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public double getThresholdAmount() {
		return thresholdAmount;
	}
	public void setThresholdAmount(double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public double getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:");
		sb.append(this.getId());
		sb.append(",customId:");
		sb.append(this.getCustomId());
		sb.append(",ownerId:");
		sb.append(this.getOwnerId());
		sb.append(",advanceAmount:");
		sb.append(this.getAdvanceAmount());
		sb.append(",thresholdAmount:");
		sb.append(this.getThresholdAmount());
		sb.append(",balanceAmount:");
		sb.append(this.getBalanceAmount());
		sb.append(",createTime:");
		sb.append(this.getCreateTime());
		sb.append(",updateTime:");
		sb.append(this.getUpdateTime());
		sb.append("}");
		return sb.toString();
	}

}

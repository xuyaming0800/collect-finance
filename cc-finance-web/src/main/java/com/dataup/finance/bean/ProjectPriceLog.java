package com.dataup.finance.bean;

import java.io.Serializable;

public class ProjectPriceLog  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7392588384955227616L;
	
	private String id;//主键
	private String ownerId;//项目Id
	private String customId;//客户Id
	private Double advanceAmount;//垫付金额
	private Double thresholdAmount;//阀值金额
	private Double balanceAmount;//余额
	private String operateId;//操作关联的ID
	private Double operateAmount;//此次操作变化金额
	private int operateType;//操作类型:(1:充值,2:垫付,3:退款,4:阀值修改,5:单价设置,6:扣款)
	private int status;//审核状态（1:审核通过,2:不通过）
	private long createTime;//创建时间

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

	public Double getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public Double getThresholdAmount() {
		return thresholdAmount;
	}
	public void setThresholdAmount(Double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getOperateId() {
		return operateId;
	}
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	public Double getOperateAmount() {
		return operateAmount;
	}
	public void setOperateAmount(Double operateAmount) {
		this.operateAmount = operateAmount;
	}
	public int getOperateType() {
		return operateType;
	}
	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


}

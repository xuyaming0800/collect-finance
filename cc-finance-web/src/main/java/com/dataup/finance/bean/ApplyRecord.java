package com.dataup.finance.bean;

import java.io.Serializable;
/**
 * 申请记录  （充值申请，垫付申请，退款申请，余额阀值修改申请）
 * @author wenpeng.jin
 *
 */
public class ApplyRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6818233418053231618L;
	
	private String id;//主键
	private String applyId; // 申请人ID
	private String applyName;//申请人名称
	private String auditId;//审核人ID
	private String auditName;//审核人名称
	private String customId;//   客户ID
	private String customName;//   客户名称
	private String ownerId;//项目ID
	private String collectClassParentId;//采集品类ID
	private String collectClassParentName;//采集品类名称
	private String projectName;//项目名称
	private double money; //金额
	private double originalMoney;//原始阀值金额（目前此字段适用于保存修改之前阀值大小）
	
	private int type;//类型(0:未知,1:充值,2:垫付,3:退款 ,4:余额阀值修改,5:单价设置)
	private int status;//状态(0:审核中,1 :审核通过 2:未通过)
	private String remark;//申请原因
	private Long createTime;//创建时间（申请时间）
	private Long auditTime;//审核时间
	private Long updateTime;//更新时间
	private double thresholdAmount;//阀值
	private Double advanceAmount;//垫付金额
	private Double balanceAmount;//余额
	
	private String projectLeaderName;//项目负责人
	
	private String processDefinitionId;// （工作流）流程定义ID
	private String processInstanceId;// （工作流）流程实例ID
	private String auditOpinion;//审核意见
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(double thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

	public Double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getCollectClassParentId() {
		return collectClassParentId;
	}

	public void setCollectClassParentId(String collectClassParentId) {
		this.collectClassParentId = collectClassParentId;
	}

	public String getCollectClassParentName() {
		return collectClassParentName;
	}

	public void setCollectClassParentName(String collectClassParentName) {
		this.collectClassParentName = collectClassParentName;
	}

	public String getProjectLeaderName() {
		return projectLeaderName;
	}

	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}

	public double getOriginalMoney() {
		return originalMoney;
	}

	public void setOriginalMoney(double originalMoney) {
		this.originalMoney = originalMoney;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:");
		sb.append(this.getId());
		sb.append(",applyId:");
		sb.append(this.getApplyId());
		sb.append(",applyName:");
		sb.append(this.getApplyName());
		sb.append(",auditId:");
		sb.append(this.getAuditId());
		sb.append(",auditName:");
		sb.append(this.getAuditName());
		sb.append(",customId:");
		sb.append(this.getCustomId());
		sb.append(",customName:");
		sb.append(this.getCustomName());
		sb.append(",ownerId:");
		sb.append(this.getOwnerId());
		sb.append(",collectClassParentId:");
		sb.append(this.getCollectClassParentId());
		sb.append(",collectClassParentName:");
		sb.append(this.getCollectClassParentName());
		sb.append(",projectName:");
		sb.append(this.getProjectName());
		sb.append(",money:");
		sb.append(this.getMoney());
		sb.append(",type:");
		sb.append(this.getType());
		sb.append(",status:");
		sb.append(this.getStatus());
		sb.append(",remark:");
		sb.append(this.getRemark());
		sb.append(",createTime:");
		sb.append(this.getCreateTime());
		sb.append(",auditTime:");
		sb.append(this.getAuditTime());
		sb.append(",thresholdAmount:");
		sb.append(this.getThresholdAmount());
		sb.append(",advanceAmount:");
		sb.append(this.getAdvanceAmount());
		sb.append(",balanceAmount:");
		sb.append(this.getBalanceAmount());
		sb.append("}");
		return sb.toString();
	}
	
	

}

package com.dataup.finance.bean;

import java.io.Serializable;

public class CollectClassPrice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4147578724878596858L;
	private String id;//主键
	private String applyRecordId;
	private String ownerId;//项目ID
	private String collectClassParentId;//采集品类父ID
	private String collectClassParentName;//采集品类父名称
	private String collectClassId;//采集品类ID
	private String collectClassName;//采集品类名称
	private double userMoneyMin;//应付用户最小金额
	private double userMoneyMax;//应付用户最大金额
	private double customMoneyMin;//应扣客户最小金额
	private double customMoneyMax;//应扣客户最大金额
	private long createTime;//创建时间
	private long updateTime;//更新时间
	
	private double originalUserMoneyMin;//原应付用户最小金额
	private double originalUserMoneyMax;//原应付用户最大金额
	private double originalCustomMoneyMin;//原应扣客户最小金额
	private double originalCustomMoneyMax;//原应扣客户最大金额
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyRecordId() {
		return applyRecordId;
	}
	public void setApplyRecordId(String applyRecordId) {
		this.applyRecordId = applyRecordId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getCollectClassParentId() {
		return collectClassParentId;
	}
	public void setCollectClassParentId(String collectClassParentId) {
		this.collectClassParentId = collectClassParentId;
	}
	public String getCollectClassId() {
		return collectClassId;
	}
	public void setCollectClassId(String collectClassId) {
		this.collectClassId = collectClassId;
	}
	public String getCollectClassName() {
		return collectClassName;
	}
	public void setCollectClassName(String collectClassName) {
		this.collectClassName = collectClassName;
	}
	public double getUserMoneyMin() {
		return userMoneyMin;
	}
	public void setUserMoneyMin(double userMoneyMin) {
		this.userMoneyMin = userMoneyMin;
	}
	public double getUserMoneyMax() {
		return userMoneyMax;
	}
	public void setUserMoneyMax(double userMoneyMax) {
		this.userMoneyMax = userMoneyMax;
	}
	public double getCustomMoneyMin() {
		return customMoneyMin;
	}
	public void setCustomMoneyMin(double customMoneyMin) {
		this.customMoneyMin = customMoneyMin;
	}
	public double getCustomMoneyMax() {
		return customMoneyMax;
	}
	public void setCustomMoneyMax(double customMoneyMax) {
		this.customMoneyMax = customMoneyMax;
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
	
	public double getOriginalUserMoneyMin() {
		return originalUserMoneyMin;
	}
	public void setOriginalUserMoneyMin(double originalUserMoneyMin) {
		this.originalUserMoneyMin = originalUserMoneyMin;
	}
	public double getOriginalUserMoneyMax() {
		return originalUserMoneyMax;
	}
	public void setOriginalUserMoneyMax(double originalUserMoneyMax) {
		this.originalUserMoneyMax = originalUserMoneyMax;
	}
	public double getOriginalCustomMoneyMin() {
		return originalCustomMoneyMin;
	}
	public void setOriginalCustomMoneyMin(double originalCustomMoneyMin) {
		this.originalCustomMoneyMin = originalCustomMoneyMin;
	}
	public double getOriginalCustomMoneyMax() {
		return originalCustomMoneyMax;
	}
	public void setOriginalCustomMoneyMax(double originalCustomMoneyMax) {
		this.originalCustomMoneyMax = originalCustomMoneyMax;
	}
	public String getCollectClassParentName() {
		return collectClassParentName;
	}
	public void setCollectClassParentName(String collectClassParentName) {
		this.collectClassParentName = collectClassParentName;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:");
		sb.append(this.getId());
		sb.append(",applyRecordId:");
		sb.append(this.getApplyRecordId());
		sb.append(",ownerId:");
		sb.append(this.getOwnerId());
		sb.append(",collectClassParentId:");
		sb.append(this.getCollectClassParentId());
		sb.append(",collectClassParentName:");
		sb.append(this.getCollectClassParentName());
		sb.append(",collectClassId:");
		sb.append(this.getCollectClassId());
		sb.append(",collectClassName:");
		sb.append(this.getCollectClassName());
		sb.append(",userMoneyMin:");
		sb.append(this.getUserMoneyMin());
		sb.append(",userMoneyMax:");
		sb.append(this.getUserMoneyMax());
		sb.append(",customMoneyMin:");
		sb.append(this.getCustomMoneyMin());
		sb.append(",customMoneyMax:");
		sb.append(this.getCustomMoneyMax());
		sb.append(",createTime:");
		sb.append(this.getCreateTime());
		sb.append(",updateTime:");
		sb.append(this.getUpdateTime());
		sb.append("}");
		return sb.toString();
	}
}

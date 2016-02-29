package com.dataup.finance.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class TaskFlow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 816887157300342596L;
	
	private long id;//主键
	private String taskId;//任务ID
	private String ownerId;//项目ID
	private String collectClassParentId;//采集品类大类ID
	private String collectClassId;//采集品类小类ID
	private double userMoney;//支付给用户的金额
	private double customMoneyMin;//扣除客户的最小金额
	private double customMoneyMax;//扣除客户的最大金额
	private int status;//状态0:失败,1:成功
	private long createTime;//创建时间
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public double getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}

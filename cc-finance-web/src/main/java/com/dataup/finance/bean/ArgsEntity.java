package com.dataup.finance.bean;

public class ArgsEntity   implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258181578489998436L;
	private int id = 1;
	private String systemId;//项目id
	private String zone; //区域
	private String type; //类型
	
	private Integer pageNo;//当前页码
	private Integer pageSize;//页大小
	
	
	//--------------------
	private String classis; //统计类型(dw接口使用)
	private String classisId; //统计类型(dw接口使用)
	
	private String status; //数据展示(dw接口使用)
	
	private String payCondition; //支付查询:天月
	private String auditDate; //支付查询:审核时间
	
	/**
	 * 执行的方法		1:分类统计和区域统计列表;	2:分类统计和区域统计明细;	3:支付统计列表
	 */
	private Integer funType;
	
	public String getPayCondition() {
		return payCondition;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getSystemId() {
		return systemId;
	}
	public String getType() {
		return type;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassis() {
		return classis;
	}
	public void setClassis(String classis) {
		this.classis = classis;
	}
	
	public void setClassisId(String classisId) {
		this.classisId = classisId;
	}
	public String getClassisId() {
		return classisId;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getFunType() {
		return funType;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public void setFunType(Integer funType) {
		this.funType = funType;
	}
	@Override
	public String toString() {
		return "ArgsEntity [id=" + id + ", systemId=" + systemId + ", zone="
				+ zone + ", type=" + type + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", classis=" + classis
				+ ", classisId=" + classisId + ", status=" + status
				+ ", payCondition=" + payCondition + ", auditDate=" + auditDate
				+ ", funType=" + funType + "]";
	}
	public String getStatus() {
		return status;
	}
}

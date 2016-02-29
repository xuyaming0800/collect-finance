package com.dataup.finance.entity;

import java.util.List;

import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;

/**
 * 请求参数
 * @author wenpeng.jin
 *
 */
public class RequestEntity {
	
	private String ownerId;//项目ID
	private String projectName;//项目名称
	private String searchContent;//搜索内容 （申请人/客户）
	private int type;//默认值 方便识别
	private int status;//默认值 方便识别
	private  int pageNo;//当前页码
	private int  limit;//每页记录数
	
	private String collectClassParentId;//采集品类父类Id
	
	private List<CollectClassPrice> collectClassPrices;//采集品类价格信息list
	
	private ApplyRecord applyRecord;
	
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
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
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
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public String getCollectClassParentId() {
		return collectClassParentId;
	}
	public void setCollectClassParentId(String collectClassParentId) {
		this.collectClassParentId = collectClassParentId;
	}
	public List<CollectClassPrice> getCollectClassPrices() {
		return collectClassPrices;
	}
	public void setCollectClassPrices(List<CollectClassPrice> collectClassPrices) {
		this.collectClassPrices = collectClassPrices;
	}
	public ApplyRecord getApplyRecord() {
		return applyRecord;
	}
	public void setApplyRecord(ApplyRecord applyRecord) {
		this.applyRecord = applyRecord;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("ownerId");
		sb.append(this.getOwnerId());
		sb.append("projectName:");
		sb.append(this.getProjectName());
		sb.append("searchContent:");
		sb.append(this.getSearchContent());
		sb.append("type:");
		sb.append(this.getType());
		sb.append("status:");
		sb.append(this.getStatus());
		sb.append("collectClassParentId:");
		sb.append(this.getCollectClassParentId());
		sb.append("collectClassPrices:");
		sb.append(this.getCollectClassPrices());
		sb.append("applyRecord:");
		sb.append(this.getApplyRecord());
		sb.append("pageNo:");
		sb.append(this.getPageNo());
		sb.append("limit:");
		sb.append(this.getLimit());
		sb.append("}");
		return sb.toString();
	}
}

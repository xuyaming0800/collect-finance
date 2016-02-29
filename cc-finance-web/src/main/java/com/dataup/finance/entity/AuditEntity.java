package com.dataup.finance.entity;

/**
 * 请求参数
 * @author wenpeng.jin
 *
 */
public class AuditEntity {
	private String searchContent;//搜索内容 （申请人/客户）
	private int type;//默认值 方便识别
	private int status;//默认值 方便识别
	private  int pageNo;//当前页码
	private int  limit;//每页记录数
	
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("searchContent:");
		sb.append(this.getSearchContent());
		sb.append("type:");
		sb.append(this.getType());
		sb.append("status:");
		sb.append(this.getStatus());
		sb.append("pageNo:");
		sb.append(this.getPageNo());
		sb.append("limit:");
		sb.append(this.getLimit());
		sb.append("}");
		return sb.toString();
	}
}

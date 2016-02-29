package com.dataup.finance.entity;

public class ResultEntity implements java.io.Serializable {

	private static final long serialVersionUID = 883511084525128741L;

	public ResultEntity() {

	}

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 返回信息
	 */
	private Object info;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("success:");
		sb.append(this.isSuccess());
		sb.append(",code:");
		sb.append(this.getCode());
		sb.append(",desc:");
		sb.append(this.getDesc());
		sb.append(",info:");
		sb.append(this.getInfo());
		sb.append("}");
		return sb.toString();
	}

}

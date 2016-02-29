package com.dataup.finance.bean;

import java.io.Serializable;

public class Notice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6136351354512143004L;
	
	private String typeName;
	private int typeCount;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}
	@Override
	public String toString() {
		return "{typeName:"+this.getTypeName()+",typeCount:"+this.getTypeCount()+"}";
	}

}

package com.dataup.finance.entity;

import java.util.ArrayList;
import java.util.List;

public class TaskClazzMenuEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -963415384413371297L;
	private String collectClassId;
	private String collectClassName;
	private Integer collectClassCount = null;
	private Integer collectClassNearCount = null;
	private Integer collectClassFarCount = null;
	private Double collectClassPay = null;
	private Integer collectClassPayType = null;
	private Boolean isItem = true;
	private String pid;
	private List<TaskClazzMenuEntity> collectClasses = new ArrayList<TaskClazzMenuEntity>();
	private Integer index;
	private String ownerId;

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

	public Integer getCollectClassCount() {
		return collectClassCount;
	}

	public void setCollectClassCount(Integer collectClassCount) {
		this.collectClassCount = collectClassCount;
	}

	public Boolean getIsItem() {
		return isItem;
	}

	public void setIsItem(Boolean isItem) {
		this.isItem = isItem;
	}

	public List<TaskClazzMenuEntity> getCollectClasses() {
		return collectClasses;
	}

	public void setCollectClasses(List<TaskClazzMenuEntity> collectClasses) {
		this.collectClasses = collectClasses;
	}

	public Double getCollectClassPay() {
		return collectClassPay;
	}

	public void setCollectClassPay(Double collectClassPay) {
		this.collectClassPay = collectClassPay;
	}

	public Integer getCollectClassNearCount() {
		return collectClassNearCount;
	}

	public void setCollectClassNearCount(Integer collectClassNearCount) {
		this.collectClassNearCount = collectClassNearCount;
	}

	public Integer getCollectClassFarCount() {
		return collectClassFarCount;
	}

	public void setCollectClassFarCount(Integer collectClassFarCount) {
		this.collectClassFarCount = collectClassFarCount;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getCollectClassPayType() {
		return collectClassPayType;
	}

	public void setCollectClassPayType(Integer collectClassPayType) {
		this.collectClassPayType = collectClassPayType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	

}

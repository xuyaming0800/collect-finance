package com.dataup.finance.service;

import java.util.Map;

import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.Pagination;
import com.dataup.finance.entity.ApplyDetailEntity;
import com.dataup.finance.entity.RequestEntity;

public interface ApplyRecordService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月16日
	 * @description 分页条件查询申请记录
	 * @param requestEntity
	 * @return
	 * @throws Exception
	 */
	public Pagination queryAppyRecords(RequestEntity requestEntity)  throws Exception;

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 保存申请记录
	 * @param applyRecord
	 */
	public String saveApplyRecord(ApplyRecord applyRecord) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 申请是否未被审核
	 * @param customId
	 * @param ownerId
	 * @param collectClassParentId
	 * @return true:已审核;false:未被审核
	 * @throws Exception
	 */
	public boolean checkApplyIsAudit(String customId,String ownerId,String collectClassParentId) throws Exception;

	
//	/**
//	 * 
//	 * @author wenpeng.jin
//	 * @date 2015年9月14日
//	 * @description 更新申请记录基本信息
//	 * @param applyRecord
//	 */
//	public void updateAppayRecord(ApplyRecord applyRecord) throws Exception;
//	/**
//	 * 
//	 * @author wenpeng.jin
//	 * @date 2015年9月14日
//	 * @description 更改申请记录状态
//	 * @param applyRecord
//	 */
//	public void updateAppayRecordStatus(ApplyRecord applyRecord) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 查询通告信息
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> queryNotice()   throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 根据申请记录ID查询品类价格详细
	 * @param applyRecordId
	 * @return
	 * @throws Exception
	 */
	public ApplyDetailEntity queryCollectClassPrices(String applyRecordId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 根据Id 查询审核记录
	 * @param applyRecordId
	 * @return
	 * @throws Exception
	 */
	public ApplyRecord queryApplyRecord(String applyRecordId)  throws Exception;
	
}

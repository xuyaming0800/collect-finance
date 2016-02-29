package com.dataup.finance.audit.service;

import java.util.Map;

import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.Pagination;
import com.dataup.finance.entity.ApplyDetailEntity;
import com.dataup.finance.entity.AuditEntity;

public interface AuditService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 条件查询审核记录
	 * @param auditEntity
	 * @return
	 * @throws Exception
	 */
	public Pagination queryAuditRecords(AuditEntity auditEntity)  throws Exception;
	
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
	public ApplyRecord queryAuditRecord(String applyRecordId)  throws Exception;
	
	
	/**
	 * 更新主表状态事件-更新主表状态
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @param executionId
	 */
	public void updateStatusEvent(String processInstanceId, String executionId,final int status)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 审核
	 * @param applyRecordId  
	 * @param status
	 * @param auditOpinion
	 * @throws Exception
	 */
	public void doAudit(String applyRecordId,int status,String auditOpinion)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 查询通告信息
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> queryNotice()   throws Exception;

}

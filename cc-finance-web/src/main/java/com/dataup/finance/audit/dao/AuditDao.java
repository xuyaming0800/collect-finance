package com.dataup.finance.audit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dataup.finance.base.mybatis.annotation.MyBatisRepository;
import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.Notice;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.bean.ProjectPriceLog;
import com.dataup.finance.entity.ActivitiEntity;

@MyBatisRepository
public interface AuditDao {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 条件查询审核记录
	 * @param applyRecord
	 * @param customIdList
	 * @param ownerIdList
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<ApplyRecord> queryAuditRecords(@Param(value = "applyRecord") ApplyRecord applyRecord,@Param(value = "customIdList") List<String> customIdList,@Param(value = "ownerIdList") List<String> ownerIdList,@Param(value = "start") int start,@Param(value = "limit") int limit)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 查询总记录数
	 * @param applyRecord
	 * @param customIdList
	 * @param ownerIdList
	 * @return
	 * @throws Exception
	 */
	public long queryAuditRecordsCount(@Param(value = "applyRecord") ApplyRecord applyRecord,@Param(value = "customIdList") List<String> customIdList,@Param(value = "ownerIdList") List<String> ownerIdList)  throws Exception;
	

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 根据申请记录ID 查询品类价格
	 * @param applyRecordId
	 * @return
	 * @throws Exception
	 */
	public List<CollectClassPrice> queryCollectClassPricesByApplyRecordId(
			@Param(value = "applyRecordId") String applyRecordId
			)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 根据Id 查询审核记录
	 * @param applyRecordId
	 * @return
	 * @throws Exception
	 */
	public ApplyRecord queryAuditRecord(String applyRecordId) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 更改状态
	 * @param bsTaskId 业务ID  (申请记录ID)
	 * @param status
	 * @throws Exception
	 */
	public void updateStatus(@Param(value = "bsTaskId") String bsTaskId,@Param(value = "status") int status) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 更新审核意见
	 * @param bsTaskId
	 * @param auditOpinion
	 * @throws Exception
	 */
	public void updateAuditOpinion(@Param(value = "bsTaskId") String bsTaskId,@Param(value = "auditOpinion") String auditOpinion) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 更新审核完成时间
	 * @param bsTaskId
	 * @param auditTime
	 * @throws Exception
	 */
	public void updateAuditTime(@Param(value = "bsTaskId") String bsTaskId,@Param(value = "auditTime") long auditTime) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 根据任务ID taskID 查询activity信息
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public ActivitiEntity queryActivitiCompleteByTaskId(@Param(value = "taskId") String taskId) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 根据流程实例ID 查询activity信息
	 * @param procInstId
	 * @param actName
	 * @return
	 * @throws Exception
	 */
	public ActivitiEntity queryActivitiCompleteByprocInstId(@Param(value = "procInstId") String procInstId,@Param(value = "actName") String actName) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 查询项目价格信息
	 * @param ownerId
	 * @throws Exception
	 */
	public ProjectPrice queryProjectPrice(@Param(value = "ownerId") String ownerId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 更新项目价格信息
	 * @param projectPrice
	 * @throws Exception
	 */
	public void updateProjectPrice(ProjectPrice projectPrice) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 保存历史记录
	 * @param projectPriceLog
	 * @throws Exception
	 */
	public void saveProjectPriceLog(ProjectPriceLog projectPriceLog) throws  Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 查询品类价格 根据ownerId ,collectClassParentId,collectClassId
	 * @param collectClassPrice
	 * @return
	 * @throws Exception
	 *//*
	public CollectClassPrice queryCollectClassPrice(CollectClassPrice collectClassPrice)  throws  Exception;
	
	*//**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 更新品类价格
	 * @param collectClassPrice
	 * @throws Exception
	 */
	public void updateCollectClassPrice(CollectClassPrice collectClassPrice)  throws  Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 保存采集品类价格信息
	 * @param collectClassPrices
	 */
	public void saveCollectClassPrices(@Param(value = "collectClassPrices") List<CollectClassPrice> collectClassPrices)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 删除采集品类价格历史信息
	 * @param ownerId
	 * @param collectClassParentId
	 */
	public void deleteCollectClassPrices(
			@Param(value = "ownerId") String ownerId,
			@Param(value = "collectClassParentId") String collectClassParentId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 查询当前用户的通告信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Notice> queryNotice(@Param(value = "userId")  String userId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月9日
	 * @description 更新申请记录的当前余额
	 * @param applyRecord
	 * @throws Exception
	 */
	public void updateCurrentBalanceAmount(ApplyRecord applyRecord)   throws Exception;
	
}

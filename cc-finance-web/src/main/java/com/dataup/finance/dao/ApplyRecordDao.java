package com.dataup.finance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dataup.finance.base.mybatis.annotation.MyBatisRepository;
import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.Notice;

@MyBatisRepository
public interface ApplyRecordDao {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 条件查询申请记录
	 * @param applyRecord
	 * @param applyIdList
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<ApplyRecord> queryAppyRecords(@Param(value = "applyRecord") ApplyRecord applyRecord,@Param(value = "applyIdList") List<String> applyIdList,@Param(value = "start") int start,@Param(value = "limit") int limit)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 条件查询总记录数
	 * @param applyRecord
	 * @param applyIdList
	 * @return
	 * @throws Exception
	 */
	public Long queryAppyRecordsCount(@Param(value = "applyRecord") ApplyRecord applyRecord,@Param(value = "applyIdList") List<String> applyIdList)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 保存申请记录
	 * @param applyRecord
	 */
	public void saveApplyRecord(ApplyRecord applyRecord) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 未被审核的采集品类价格申请数量
	 * @param customId
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 * @throws Exception
	 */
	public Long checkApplyIsAudit(@Param(value = "customId") String customId,@Param(value = "ownerId") String ownerId,@Param(value = "collectClassParentId") String collectClassParentId)  throws Exception;

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
//	public void updateAppayRecordStatus(ApplyRecord applyRecord)  throws Exception;
	
//	public void deleteApplyRecord(@Param(value = "id") String id)  throws Exception;
	
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

}

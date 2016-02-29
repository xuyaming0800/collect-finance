package com.dataup.finance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dataup.finance.base.mybatis.annotation.MyBatisRepository;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.bean.ProjectPriceLog;
import com.dataup.finance.bean.TaskFlow;

@MyBatisRepository
public interface CollectClassPriceDao {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 查询品类价格
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @return
	 */
	public CollectClassPrice queryCollectClassPrice(
			@Param(value = "ownerId") String ownerId,
			@Param(value = "collectClassParentId") String collectClassParentId,
			@Param(value = "collectClassId") String collectClassId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 查询品类价格
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @return
	 */
	public List<CollectClassPrice> queryCollectClassPrices(
			@Param(value = "ownerId") String ownerId,
			@Param(value = "collectClassParentId") String collectClassParentId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询所有的品类价格 
	 * @return
	 * @throws Exception
	 */
	public List<CollectClassPrice> queryAllCollectClassPrices()  throws Exception;
	
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
	 * @date 2015年9月18日
	 * @description 保存采集品类价格信息 暂未使用
	 * @param collectClassPrices
	 */
	public void saveCollectClassPrices(@Param(value = "collectClassPrices") List<CollectClassPrice> collectClassPrices)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 删除采集品类价格信息 暂未使用
	 * @param ownerId
	 * @param collectClassParentId
	 */
	public void deleteCollectClassPrices(
			@Param(value = "ownerId") String ownerId,
			@Param(value = "collectClassParentId") String collectClassParentId)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 保存采集品类价格历史信息
	 * @param collectClassPrices
	 */
	public void saveCollectClassPriceLogs(@Param(value = "collectClassPrices") List<CollectClassPrice> collectClassPrices)  throws Exception;
	

	
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
	 * @date 2015年9月25日
	 * @description 根据项目ID或者客户ID查询项目价格信息
	 * @param ownerId
	 * @param customId
	 * @return
	 * @throws Exception
	 */
	public List<ProjectPrice> queryProjectPrices(@Param(value = "ownerId") String ownerId,@Param(value = "customId") String customId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 保存项目价格信息
	 * @param projectPrice
	 * @throws Exception
	 */
	public void saveProjectPrice(ProjectPrice projectPrice)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 更新项目价格信息 暂未使用
	 * @param projectPrice
	 * @throws Exception
	 */
	public void updateProjectPrice(ProjectPrice projectPrice) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 保存任务流水
	 * @param taskFlows
	 * @throws Exception
	 */
	public void saveTaskFlows(@Param(value = "taskFlows") List<TaskFlow> taskFlows)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 查询项目累计支付金额
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	public Double queryTotalPay(@Param(value = "ownerId") String ownerId)  throws Exception;
	
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
	 * @date 2015年10月30日
	 * @description 获取任务扣款记录日志 防止同一任务多次支付
	 * @return
	 * @throws Exception
	 */
	public long getCustomDebitLog(@Param(value = "ownerId") String ownerId,@Param(value = "taskId") String taskId) throws Exception;
}

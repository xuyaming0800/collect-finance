package com.dataup.finance.service;

import java.util.List;

import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.entity.RequestEntity;
import com.dataup.finance.entity.TaskClazzMenuEntity;

public interface CollectClassPriceService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 从redis缓存中获取采集品类大类信息
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	public List<TaskClazzMenuEntity> queryCollectClassParent(String ownerId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 查询品类价格信息价
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 * @throws Exception
	 */
	public List<CollectClassPrice> queryCollectClassPrices(String ownerId,String collectClassParentId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 根据申请记录ID查询品类价格详细
	 * @param applyRecordId
	 * @return
	 * @throws Exception
	 */
	public List<CollectClassPrice> queryCollectClassPrices(String applyRecordId)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 保存和更新采集品类价格信息
	 * @param requestEntity
	 * @throws Exception
	 */
	public void saveCollectClassPrices(RequestEntity requestEntity)  throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 查询项目价格信息
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	public ProjectPrice queryProjectPrice(String ownerId)  throws Exception;


	


}

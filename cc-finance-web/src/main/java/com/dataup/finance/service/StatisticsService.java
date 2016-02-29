package com.dataup.finance.service;

import com.dataup.finance.bean.ArgsEntity;
import com.dataup.finance.bean.Pagination;
import com.dataup.finance.entity.ResultEntity;

public interface StatisticsService {
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月23日
	 * @description 扣支明细列表
	 * @param pageNo
	 * @param limit
	 * @param ownerId
	 * @param payCondition
	 * @return
	 * @throws Exception
	 */
	public ResultEntity queryBuckleList(ArgsEntity argsEntity) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月23日
	 * @description 扣支明细详情
	 * @param pageNo
	 * @param limit
	 * @param ownerId
	 * @param auditDate
	 * @param funType
	 * @return
	 * @throws Exception
	 */
	public ResultEntity queryBuckleDetail(ArgsEntity argsEntity) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月23日
	 * @description 账单详情
	 * @param pageNo
	 * @param limit
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	public ResultEntity queryBillList(ArgsEntity argsEntity) throws Exception;

}

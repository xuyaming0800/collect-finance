package com.dataup.finance.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dataup.finance.bean.ArgsEntity;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.service.StatisticsService;
import com.dataup.finance.util.HttpRequestUtil;
import com.dataup.finance.util.PropConstants;
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
	private Logger logger = LogManager.getLogger(this.getClass());

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
	@Override
	public ResultEntity queryBuckleList(ArgsEntity argsEntity) throws Exception {
		return HttpRequestUtil.getStatiscs(get_buckle_list_url, argsEntity);
	}

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
	@Override
	public ResultEntity queryBuckleDetail(ArgsEntity argsEntity) throws Exception {
		return HttpRequestUtil.getStatiscs(get_buckle_detail_url, argsEntity);
	}
	

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
	@Override
	public ResultEntity queryBillList(ArgsEntity argsEntity)
			throws Exception {
		
		return HttpRequestUtil.getStatiscs(get_bill_list_url, argsEntity);
	}
	
	
	@Value("${" + PropConstants.GET_BUCKLE_LIST_URL + "}")
	private String get_buckle_list_url;
	@Value("${" + PropConstants.GET_BUCKLE_DETAIL_URL + "}")
	private String get_buckle_detail_url;
	@Value("${" + PropConstants.GET_BILL_LIST_URL + "}")
	private String get_bill_list_url;

}

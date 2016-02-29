package com.dataup.finance.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataup.finance.bean.ArgsEntity;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.service.StatisticsService;
import com.dataup.finance.util.PropertiesConfig;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("/buckleDetail")
	public String buckleDetail() {
		return "project/buckleDetail";
	}
	
	@RequestMapping("/billDetail")
	public String billDetail() {
		return "project/billDetail";
	}

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
	 */
	@RequestMapping("/queryBuckleList")
	public @ResponseBody ResultEntity queryBuckleList(ArgsEntity argsEntity) {
		logger.info("进入queryBuckleList方法,条件查询扣支明细列表记录------start");
		ResultEntity result = new ResultEntity();
		try{
			result = statisticsService.queryBuckleList(argsEntity);
			if(result == null) {
				result = new ResultEntity();
				result.setSuccess(true);
			}
			logger.info("进入queryBuckleList方法,条件查询扣支明细列表记录------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询扣支明细列表记录业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询扣支明细列表记录系统异常",e);
		}
		logger.info("结果集为:"+result);
		return result;
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月23日
	 * @description 扣支明细详细
	 * @param pageNo
	 * @param limit
	 * @param ownerId
	 * @param payCondition
	 * @return
	 */
	@RequestMapping("/queryBuckleDetail")
	public @ResponseBody ResultEntity queryBuckleDetail(ArgsEntity argsEntity) {
		logger.info("进入queryBuckleDetail方法,条件查询扣支明细详情记录------start");
		ResultEntity result = new ResultEntity();
		try{
			result = statisticsService.queryBuckleDetail(argsEntity);
			if(result == null) {
				result = new ResultEntity();
				result.setSuccess(true);
			}
			logger.info("进入queryBuckleDetail方法,条件查询扣支明细详情记录------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询扣支明细详情记录业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询扣支明细详情记录系统异常",e);
		}
		logger.info("结果集为:"+result);
		return result;
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月23日
	 * @description 账单详细
	 * @param pageNo
	 * @param limit
	 * @param ownerId
	 * @param payCondition
	 * @return
	 */
	@RequestMapping("/queryBillList")
	public @ResponseBody ResultEntity queryBillList(ArgsEntity argsEntity) {
		logger.info("进入queryBillList方法,条件查询账单详情列表记录------start");
		ResultEntity result = new ResultEntity();
		try{
			result = statisticsService.queryBillList(argsEntity);
			if(result == null) {
				result = new ResultEntity();
				result.setSuccess(true);
			}
			logger.info("进入queryBillList方法,条件查询账单详情列表记录------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询账单详情列表记录业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询账单详情列表记录系统异常",e);
		}
		logger.info("结果集为:"+result);
		return result;
		
	}
}

package com.dataup.finance.openapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.openapi.service.ProjectService;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.JsonBinder;
import com.dataup.finance.util.PropertiesConfig;
import com.dataup.finance.util.RequestParams;
import com.dataup.finance.util.RequestParamsHandler;

/**
 * 对外接口 提供查询项目相关信息
 * @author wenpeng.jin
 *
 */
@Controller
public class ProjectController {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 查询采集品类的价格
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/openapi",params={"serviceid=999001"})
	public @ResponseBody String queryCollectClassPrice(HttpServletRequest request) {
		logger.info("进入openapi-queryCollectClassPrice方法，查询小采集品类价格-start");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
			String ownerId = (String)params.getValue("ownerId") ;
			String collectClassParentId = (String)params.getValue("collectClassParentId");
			String collectClassId =  (String)params.getValue("collectClassId");
			String check=CommonUtil.checkNull(new String[]{ownerId,collectClassParentId,collectClassId},new String[]{"ownerId","collectClassParentId","collectClassId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				CollectClassPrice collectClassPrice = projectService.queryCollectClassPrice(ownerId, collectClassParentId, collectClassId);
				result.setSuccess(true);
				result.setCode(BusinessCode.QUERY_SUCC+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
				result.setInfo(collectClassPrice);
			}
			logger.info("进入openapi-queryCollectClassPrice方法，查询小采集品类价格-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-queryCollectClassPrice方法，查询小采集品类价格-业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("进入openapi-queryCollectClassPrice方法，查询小采集品类价格-系统异常",e);
		}
		logger.info("完成openapi-queryCollectClassPrice方法方法操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description  根据项目ID或者客户ID查询项目价格信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/openapi",params={"serviceid=999002"})
	public @ResponseBody String queryProjectPrices(HttpServletRequest request) {
		logger.info("进入openapi-queryProjectPrices方法，查询项目价格信息-start");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
			String ownerId = (String)params.getValue("ownerId");
			String customId = (String)params.getValue("customId");

			List<ProjectPrice> projectPrices = projectService.queryProjectPrices(ownerId, customId);
			result.setSuccess(true);
			result.setCode(BusinessCode.QUERY_SUCC+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			result.setInfo(projectPrices);
			logger.info("进入openapi-queryProjectPrices方法，查询项目价格信息-end");
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-queryProjectPrices方法，查询项目价格信息-业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("进入openapi-queryProjectPrices方法，查询项目价格信息-系统异常",e);
		}
		logger.info("完成openapi-queryProjectPrices方法方法操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 客户扣款
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/openapi",params={"serviceid=999003"})
	public @ResponseBody String customDebit(HttpServletRequest request) {
		logger.info("进入openapi-customDebit方法，客户扣款-start");
		JsonBinder jsonBinder = JsonBinder.buildNormalBinder(false);
		String jsoncallback = null;
		ResultEntity result = new ResultEntity();
		try{
			RequestParams params = RequestParamsHandler.handleRequest(request);
			logger.entry(params);
			jsoncallback = (String)params.getValue("jsoncallback");
			String content = (String)params.getValue("content");
			String check=CommonUtil.checkNull(new String[]{content},new String[]{"content"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				String message =projectService.customDebit(content);
				if(StringUtils.isEmpty(message)) {
					result.setSuccess(true);
					result.setCode(BusinessCode.CUSTOM_DEBIT_SUCC+"");
					result.setDesc(PropertiesConfig.getProperty(result.getCode()));
				}else {
					result.setSuccess(false);
					result.setCode(BusinessCode.CUSTOM_DEBIT_FAIL+"");
					result.setDesc(message);
				}
				logger.info("进入openapi-customDebit方法，客户扣款-end");
			}
		}catch(BusinessException e){
			result.setSuccess(false);  
			result.setCode(e.getErrorCode()+"");
			result.setDesc(e.getErrorMessage());
			logger.error("进入openapi-customDebit方法，客户扣款-业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("进入openapi-queryProjectPrices方法，客户扣款-系统异常",e);
		}
		logger.info("完成openapi-customDebit方法方法操作");
		logger.info(jsonBinder.toJson(result));
		if (jsoncallback != null && !jsoncallback.equals(""))
			return jsoncallback + "(" + jsonBinder.toJson(result) + ")";
		else
			return jsonBinder.toJson(result);
	}

}

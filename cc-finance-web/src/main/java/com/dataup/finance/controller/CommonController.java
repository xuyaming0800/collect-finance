package com.dataup.finance.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.service.CommonService;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.PropertiesConfig;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取客户信息 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getCustoms")
	public @ResponseBody ResultEntity getCustoms(@RequestParam(value="userName",required=false,defaultValue="")  String userName) {
		logger.info("进入getCustoms方法,条件查询客户信息,条件:userName="+userName+"------start");
		ResultEntity result = new ResultEntity();
		try{
			List<Object> objectList = commonService.getCustomList(userName);
			result.setSuccess(true);
			result.setInfo(objectList);
			logger.info("进入getCustoms方法,条件查询客户信息,条件:userName="+userName+"------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询客户信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询客户信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
 
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取审核人信息 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getAudits")
	public @ResponseBody ResultEntity getAudits(@RequestParam(value="userName",required=false,defaultValue="")  String userName) {
		logger.info("进入getAudits方法,条件查询审核人信息,条件:userName="+userName+"------start");
		ResultEntity result = new ResultEntity();
		try{
			List<Object> objectList = commonService.getAuditList(userName);
			result.setSuccess(true);
			result.setInfo(objectList);
			logger.info("进入getAudits方法,条件查询审核人信息,条件:userName="+userName+"------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询审核人信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询审核人信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取申请人信息 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getApplys")
	public @ResponseBody ResultEntity getApplys(@RequestParam(value="userName",required=false,defaultValue="") String userName) {
		logger.info("进入getApplys方法,条件查询申请人信息,条件:userName="+userName+"------start");
		ResultEntity result = new ResultEntity();
		try{
			List<Object> objectList = commonService.getApplyList(userName);
			result.setSuccess(true);
			result.setInfo(objectList);
			logger.info("进入getApplys方法,条件查询申请人信息,条件:userName="+userName+"------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询申请人信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询申请人信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取项目负责人 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/getProjectLeaders")
	public @ResponseBody ResultEntity getProjectLeaders(@RequestParam(value="userName",required=false,defaultValue="")  String userName) {
		logger.info("进入getProjectLeaders方法,条件查询项目负责人信息,条件:userName="+userName+"------start");
		ResultEntity result = new ResultEntity();
		try{
			List<Object> objectList = commonService.getProjectLeaderList(userName);
			result.setSuccess(true);
			result.setInfo(objectList);
			logger.info("进入getProjectLeaders方法,条件查询项目负责人信息,条件:userName="+userName+"------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询项目负责人信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询项目负责人信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取项目信息 
	 * @param customId
	 * @return
	 */
	@RequestMapping("/getProjects")
	public @ResponseBody ResultEntity getProjects(@RequestParam("customId")  String customId,@RequestParam(value="status",required=false,defaultValue="0")  int status) {
		logger.info("进入getProjects方法,条件查询项目信息,条件:customId="+customId+",status="+status+"------start");
		ResultEntity result = new ResultEntity();
		try{
			
			String check=CommonUtil.checkNull(new String[]{customId},new String[]{"customId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				List<Object> objectList = commonService.getProjectList(customId,status);
				result.setSuccess(true);
				result.setInfo(objectList);
				logger.info("进入getProjects方法,条件查询项目信息,条件:customId="+customId+",status="+status+"------end");
			}
			
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询项目信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询项目信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月21日
	 * @description 获取所有项目
	 * @return
	 */
	@RequestMapping("/getAllProjects")
	public @ResponseBody ResultEntity getAllProjects(@RequestParam(value="status",required=false,defaultValue="0")  int status) {
		logger.info("进入getAllProjects方法,查询所有项目信息,status="+status+"------start");
		ResultEntity result = new ResultEntity();
		try{
			List<Object> objectList = commonService.getAllProjectList(status);
			result.setSuccess(true);
			result.setInfo(objectList);
			logger.info("进入getAllProjects方法,查询所有项目信息,status="+status+"------end");
		
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("查询所有项目信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("查询所有项目信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
}

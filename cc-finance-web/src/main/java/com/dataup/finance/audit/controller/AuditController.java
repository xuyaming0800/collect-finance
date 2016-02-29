package com.dataup.finance.audit.controller;

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

import com.dataup.finance.audit.service.AuditService;
import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.Pagination;
import com.dataup.finance.entity.ApplyDetailEntity;
import com.dataup.finance.entity.AuditEntity;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.PropertiesConfig;

@Controller
@RequestMapping("/audit")
public class AuditController {
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private AuditService auditService;

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 审核列表页
	 * @return
	 */
	@RequestMapping("/main")
	public String main() {
		return "audit/main";
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 条件查询审核列表
	 * @param auditEntity
	 * @return
	 */
	@RequestMapping("/queryAuditRecords")
	public @ResponseBody ResultEntity queryAppyRecords(@RequestBody AuditEntity auditEntity) {
		logger.info("进入queryAuditRecords方法,条件查询审核申请记录------start");
		logger.info("查询参数为:"+auditEntity.toString());
		ResultEntity result = new ResultEntity();
		try{
			Pagination page = auditService.queryAuditRecords(auditEntity);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryAuditRecords方法,条件查询审核申请记录------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询审核申请记录业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询申请审核记录系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 根据申请记录ID查询品类价格详细
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 */
	@RequestMapping("/queryCollectClassPricesByApplyRecordId")
	public @ResponseBody ResultEntity queryCollectClassPrices(@RequestParam("applyRecordId") String applyRecordId) {
		logger.info("进入queryCollectClassPrices方法,根据applyRecordId:"+applyRecordId+"查询采集品类价格信息------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{applyRecordId},new String[]{"applyRecordId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				ApplyDetailEntity applyDetailEntity  = auditService.queryCollectClassPrices(applyRecordId);
				result.setSuccess(true);
				result.setInfo(applyDetailEntity);
			}
			logger.info("进入queryCollectClassPrices方法,根据applyRecordId:"+applyRecordId+"查询采集品类价格信息------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("根据applyRecordId:"+applyRecordId+"查询采集品类价格信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("根据applyRecordId:"+applyRecordId+"查询采集品类价格信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 查询审核记录详情
	 * @param applyRecordId
	 * @return
	 */
	@RequestMapping("/queryAuditRecord")
	public @ResponseBody ResultEntity queryAuditRecord (@RequestParam("applyRecordId") String applyRecordId) {
		logger.info("进入queryAuditRecord方法,根据applyRecordId:"+applyRecordId+"查询申请记录审核信息------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{applyRecordId},new String[]{"applyRecordId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				ApplyRecord  applyRecord = auditService.queryAuditRecord(applyRecordId);
				result.setSuccess(true);
				result.setInfo(applyRecord);
			}
			logger.info("进入queryAuditRecord方法,根据applyRecordId:"+applyRecordId+"查询申请记录审核信息------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("根据applyRecordId:"+applyRecordId+"查询申请记录审核信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("根据applyRecordId:"+applyRecordId+"查询申请记录审核信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 审核
	 * @param applyRecordId
	 * @param status
	 * @param auditOpinion
	 * @return
	 */
	@RequestMapping("/doAudit")
	public @ResponseBody ResultEntity doAudit (
			@RequestParam("applyRecordId") String applyRecordId,
			@RequestParam("status")int status,
			@RequestParam(value="auditOpinion",required=false) String auditOpinion) {
		logger.info("进入doAudit方法,审核记录ID:"+applyRecordId+",审核状态:"+status+",审核意见:"+auditOpinion+"------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{applyRecordId,String.valueOf(status)},new String[]{"applyRecordId","status"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				auditService.doAudit(applyRecordId, status, auditOpinion);
				result.setSuccess(true);
				result.setCode(BusinessCode.AUDIT_SUCC+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}
			logger.info("进入doAudit方法,审核记录ID:"+applyRecordId+",审核状态:"+status+",审核意见:"+auditOpinion+"------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("根据applyRecordId:"+applyRecordId+"查询申请记录审核信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("根据applyRecordId:"+applyRecordId+"查询申请记录审核信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 查询当前用户的通告信息
	 * @return
	 */
	@RequestMapping("/queryNotice")
	public @ResponseBody ResultEntity queryNotice () {
		logger.info("进入queryNotice方法,查询通告信息------start");
		ResultEntity result = new ResultEntity();
		try{
			Map<String,Integer> noticeMap = auditService.queryNotice();
			result.setSuccess(true);
			result.setInfo(noticeMap);
			logger.info("进入queryNotice方法,查询通告信息------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("查询通告信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("查询通告信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
	}
	
}

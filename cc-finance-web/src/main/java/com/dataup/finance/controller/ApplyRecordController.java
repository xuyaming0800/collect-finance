package com.dataup.finance.controller;

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
import com.dataup.finance.bean.Pagination;
import com.dataup.finance.entity.ApplyDetailEntity;
import com.dataup.finance.entity.RequestEntity;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.service.ApplyRecordService;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.PropertiesConfig;

@Controller
@RequestMapping("/applyRecord")
public class ApplyRecordController {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ApplyRecordService applyRecordService;
	
	@RequestMapping("/main")
	public String main() {
		return "apply/main";
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 充值申请页面
	 * @return
	 */
	@RequestMapping("/rechargeMain")
	public String rechargeMain() {
		return "apply/rechargeMain";
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 垫付申请页面
	 * @return
	 */
	@RequestMapping("/ advanceMain")
	public String advanceMain() {
		return "apply/advanceMain";
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 退款申请页面
	 * @return
	 */
	@RequestMapping("/refundMain")
	public String refundMain() {
		return "apply/refundMain";
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 阀值修改页面
	 * @return
	 */
	@RequestMapping("/ thresholdMain")
	public String thresholdMain() {
		return "apply/thresholdMain";
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 申请记录列表页面
	 * @return
	 */
	@RequestMapping("/applyList")
	public String applyList() {
		return "apply/applyList";
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 申请记录列表
	 * @param requestEntity
	 * @return
	 */
	@RequestMapping("/queryAppyRecords")
	public @ResponseBody ResultEntity queryAppyRecords(@RequestBody RequestEntity requestEntity) {
		logger.info("进入queryAppyRecords方法,条件查询申请记录------start");
		logger.info(requestEntity.toString());
		ResultEntity result = new ResultEntity();
		try{
			Pagination page = applyRecordService.queryAppyRecords(requestEntity);
			result.setSuccess(true);
			result.setInfo(page);
			logger.info("进入queryAppyRecords方法,条件查询申请记录------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("条件查询申请记录业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("条件查询申请记录系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
		
	}
 
	
	@RequestMapping("/saveApplyRecord")
	public @ResponseBody ResultEntity saveApplyRecord(@RequestBody ApplyRecord applyRecord) {
		logger.info("进入saveApplyRecord方法,保存申请记录------start");
		logger.info(applyRecord.toString());
		ResultEntity result = new ResultEntity();
		try{
			applyRecordService.saveApplyRecord(applyRecord);
			result.setSuccess(true);
			result.setCode(BusinessCode.SAVE_SUCC+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.info("进入saveApplyRecord方法,保存申请记录------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("保存申请记录业务异常",e);
			return result;
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("保存申请记录系统异常",e);
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
			Map<String,Integer> noticeMap = applyRecordService.queryNotice();
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
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 验证品类价格申请是否已经被审核，如果未被审核则不能新建
	 * @param customId
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 */
	@RequestMapping("/checkApplyIsAudit")
	public @ResponseBody ResultEntity checkApplyIsAudit(@RequestParam("customId") String customId,@RequestParam("ownerId") String ownerId,@RequestParam("collectClassParentId") String collectClassParentId) {
		logger.info("进入checkApplyIsAudit方法,根据customId:"+customId+",ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询申请信息是否被审核------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{ownerId,collectClassParentId},new String[]{"ownerId","collectClassParentId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				boolean flag  = applyRecordService.checkApplyIsAudit(customId, ownerId, collectClassParentId);
				result.setSuccess(true);
				if(flag) {
					result.setCode(BusinessCode.IS_AUDIT+"");
				}else {
					result.setCode(BusinessCode.NO_AUDIT+"");
				}
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}
			logger.info("进入checkApplyIsAudit方法,根据customId:"+customId+",ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询申请信息是否被审核------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("根据customId:"+customId+",ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询申请信息是否被审核业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("根据customId:"+customId+",ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询申请信息是否被审核系统异常",e);
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
				ApplyDetailEntity applyDetailEntity  = applyRecordService.queryCollectClassPrices(applyRecordId);
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
	@RequestMapping("/queryApplyRecord")
	public @ResponseBody ResultEntity queryApplyRecord (@RequestParam("applyRecordId") String applyRecordId) {
		logger.info("进入queryApplyRecord方法,根据applyRecordId:"+applyRecordId+"查询申请记录审核信息------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{applyRecordId},new String[]{"applyRecordId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				ApplyRecord  applyRecord = applyRecordService.queryApplyRecord(applyRecordId);
				result.setSuccess(true);
				result.setInfo(applyRecord);
			}
			logger.info("进入queryApplyRecord方法,根据applyRecordId:"+applyRecordId+"查询申请记录审核信息------end");
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
	
}

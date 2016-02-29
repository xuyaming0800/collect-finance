package com.dataup.finance.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.entity.RequestEntity;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.entity.TaskClazzMenuEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.service.CollectClassPriceService;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.PropertiesConfig;

@Controller
@RequestMapping("/ccPrice")
public class CollectClassPriceController {
private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private CollectClassPriceService collectClassPriceService;
	
	@RequestMapping("/main")
	public String main() {
		return "project/main";
	}
	
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
	 * @date 2015年9月18日
	 * @description 根据项目ID查询大品类信息
	 * @param ownerId
	 * @return
	 */
	@RequestMapping("/queryCollectClassParent")
	public @ResponseBody ResultEntity queryCollectClassParent(@RequestParam("ownerId") String ownerId) {
		logger.info("进入queryCollectClassParent方法,根据ownerId:"+ownerId+"查询采集品类大类------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{ownerId},new String[]{"ownerId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				List<TaskClazzMenuEntity> menuList  = collectClassPriceService.queryCollectClassParent(ownerId);
				result.setSuccess(true);
				result.setInfo(menuList);
			}
			logger.info("进入queryCollectClassParent方法,根据ownerId:"+ownerId+"查询采集品类大类------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("根据ownerId:"+ownerId+"查询采集品类大类业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("根据ownerId:"+ownerId+"查询采集品类大类系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 查询采集品类价格信息
	 * @param ownerId
	 * @param collectClassParentId
	 * @return
	 */
	@RequestMapping("/queryCollectClassPrices")
	public @ResponseBody ResultEntity queryCollectClassPrices(@RequestParam("ownerId") String ownerId,@RequestParam("collectClassParentId") String collectClassParentId) {
		logger.info("进入queryCollectClassPrices方法,根据ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询采集品类价格信息------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{ownerId,collectClassParentId},new String[]{"ownerId","collectClassParentId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				List<CollectClassPrice> ccpList  = collectClassPriceService.queryCollectClassPrices(ownerId, collectClassParentId);
				result.setSuccess(true);
				result.setInfo(ccpList);
			}
			logger.info("进入queryCollectClassPrices方法,根据ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询采集品类价格信息------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("根据ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询采集品类价格信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("根据ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询采集品类价格信息系统异常",e);
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
				List<CollectClassPrice> ccpList  = collectClassPriceService.queryCollectClassPrices(applyRecordId);
				result.setSuccess(true);
				result.setInfo(ccpList);
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
	 * @date 2015年9月18日
	 * @description 保存采集品类价格信息
	 * @param requestEntity
	 * @return
	 */
	@RequestMapping("/saveCollectClassPrices")
	public @ResponseBody ResultEntity saveCollectClassPrices(@RequestBody RequestEntity requestEntity) {
		logger.info(requestEntity.toString());
		logger.info("进入saveCollectClassPrices方法,保存采集品类价格信息------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{requestEntity.getOwnerId(),requestEntity.getCollectClassParentId()},new String[]{"ownerId","collectClassParentId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				collectClassPriceService.saveCollectClassPrices(requestEntity);
				result.setSuccess(true);
				result.setCode(BusinessCode.SUBMIT_APPLY_SUCC+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}
			logger.info("进入saveCollectClassPrices方法,保存采集品类价格信息------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("保存采集品类价格信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("保存采集品类价格信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
	}
	
	@RequestMapping("/queryProjectPrice")
	public @ResponseBody ResultEntity queryProjectPrice(@RequestParam("ownerId") String ownerId) { 
		logger.info("进入queryProjectPrice方法,根据ownerId:"+ownerId+"查询项目价格信息------start");
		ResultEntity result = new ResultEntity();
		try{
			String check=CommonUtil.checkNull(new String[]{ownerId},new String[]{"ownerId"});
			if(!check.equals("")){
				logger.error(check);
				result.setSuccess(false);
				result.setCode(BusinessCode.MISS_REQUIRED_PARAMS+"");
				result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			}else {
				ProjectPrice projectPrice = collectClassPriceService.queryProjectPrice(ownerId);
				result.setSuccess(true);
				result.setInfo(projectPrice);
			}
			logger.info("进入queryCollectClassPrices方法,根据ownerId:"+ownerId+"查询项目价格信息------end");
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setCode(String.valueOf(e.getErrorCode()));
			result.setDesc(e.getErrorMessage());
			logger.error("根据ownerId:"+ownerId+"查询项目价格信息业务异常",e);
		}catch(Exception e){
			result.setSuccess(false);
			result.setCode(BusinessCode.SYS_ERROR_INFO+"");
			result.setDesc(PropertiesConfig.getProperty(result.getCode()));
			logger.error("根据ownerId:"+ownerId+"查询项目价格信息系统异常",e);
		}
		logger.info("结果集为:"+result.toString());
		return result;
	}
}

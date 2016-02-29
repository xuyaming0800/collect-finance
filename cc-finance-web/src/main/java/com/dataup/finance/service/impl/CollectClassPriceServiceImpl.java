package com.dataup.finance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.CollectTaskClazz;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.componet.TaskClazzCacheComponent;
import com.dataup.finance.constant.ApplyType;
import com.dataup.finance.constant.TaskStatus;
import com.dataup.finance.dao.CollectClassPriceDao;
import com.dataup.finance.entity.RequestEntity;
import com.dataup.finance.entity.TaskClazzMenuEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.service.ApplyRecordService;
import com.dataup.finance.service.CollectClassPriceService;
import com.dataup.finance.util.HttpRequestUtil;
import com.dataup.finance.util.PrimaryByRedis;
import com.dataup.finance.util.PropConstants;
import com.dataup.finance.util.PropertiesConfig;
@Service("collectClassPriceService")
public class CollectClassPriceServiceImpl implements CollectClassPriceService {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private TaskClazzCacheComponent taskClazzCacheComponent;
	@Autowired
	private CollectClassPriceDao collectClassPriceDao;
	@Autowired
	private ApplyRecordService applyRecordService;
	@Autowired
	private PrimaryByRedis primaryByRedis;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 从redis缓存中获取采集品类大类信息
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TaskClazzMenuEntity> queryCollectClassParent(String ownerId)
			throws Exception {
		logger.info("根据ownerId:"+ownerId+"从redis缓存中查询采集品类大类信息--start--service");
		List<TaskClazzMenuEntity> menuList =new ArrayList<TaskClazzMenuEntity>();
		List<TaskClazzMenuEntity> menuListInitiative = taskClazzCacheComponent.getCollectClazzTree(ownerId, false);
		List<TaskClazzMenuEntity> menuListPassive = taskClazzCacheComponent.getCollectClazzTree(ownerId,true);
		if(menuListInitiative != null) {
			menuList.addAll(menuListInitiative);
		}
		if(menuListPassive != null) {
			menuList.addAll(menuListPassive);
		}
		if(menuList.size() > 0) {
			logger.info("从redis缓存中获取大品类数量为:"+menuList.size());
		}else {
			logger.error("未从redis缓存中获取大品类");//0 被动 1主动
			menuListInitiative = HttpRequestUtil.getCollectClazzTree(get_collectclazz_tree_url, "ownerId="+ownerId+"&taskType=1");
			menuListPassive = HttpRequestUtil.getCollectClazzTree(get_collectclazz_tree_url, "ownerId="+ownerId+"&taskType=0");
			if(menuListInitiative != null) {
				menuList.addAll(menuListInitiative);
			}
			if(menuListPassive != null) {
				menuList.addAll(menuListPassive);
			}
		}
		
		logger.info("根据ownerId:"+ownerId+"从redis缓存中查询采集品类大类信息--end--service");
		return menuList;
	}
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
	@Override
	public List<CollectClassPrice> queryCollectClassPrices(String ownerId,
			String collectClassParentId) throws Exception {
		
		List<CollectClassPrice>  ccpList =  null;
		logger.info("根据项目Id:"+ownerId+"从缓存中获取此项目下的大采集品类--start");
//		List<TaskClazzMenuEntity> menuList = taskClazzCacheComponent.getCollectTaskClazzMenuList(ownerId);
		
		List<TaskClazzMenuEntity> menuList = queryCollectClassParent(ownerId);
		logger.info("根据项目Id:"+ownerId+"从缓存中获取此项目下的大采集品类--end");
		if(menuList.size() > 0) {
			logger.info("从redis缓存中获取大品类数量为:"+menuList.size());
			List<TaskClazzMenuEntity> menuChildsList  = null;
			logger.info("循环对比，获取当前采集品类父类:"+collectClassParentId+"下的子品类信息--start");
			for(TaskClazzMenuEntity menu : menuList) {
				if(collectClassParentId.equals(String.valueOf(menu.getCollectClassId()))) {
					menuChildsList = menu.getCollectClasses();
					break;
				}
			}
			logger.info("循环对比，获取当前采集品类父类:"+collectClassParentId+"下的子品类信息--end");
			if(menuChildsList == null) {
				logger.error("循环对比，未获取当前采集品类父类:"+collectClassParentId+"下的子品类信息");
				throw new BusinessException(BusinessCode.NO_EXIST_IN＿REDIS+"",PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
			}
			logger.info("从redis缓存中获取子品类数量为:"+menuChildsList.size());
			logger.info("组装从缓存中获取的数据--start");
			ccpList = new ArrayList<CollectClassPrice>();
			
			logger.info("根据ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"从数据库查询采集品类价格信息--start--service");
			List<CollectClassPrice> collectClassPriceList  = collectClassPriceDao.queryCollectClassPrices(ownerId, collectClassParentId);
			Map<String,CollectClassPrice>collectClassPriceMap = null;
			if(collectClassPriceList != null && collectClassPriceList.size() > 0) {
				logger.info("从数据库查询采集品类价格信息为:"+collectClassPriceList.toString());
				collectClassPriceMap = new HashMap<String,CollectClassPrice>();
				for(CollectClassPrice ccp: collectClassPriceList) {
					collectClassPriceMap.put(ccp.getCollectClassId(), ccp);
				}
			}
			logger.info("根据ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"从数据库查询采集品类价格信息--end--service");
			
			for(TaskClazzMenuEntity menu : menuChildsList) {
				CollectClassPrice ccp = new CollectClassPrice();
				ccp.setOwnerId(ownerId);
				ccp.setCollectClassParentId(collectClassParentId);
				ccp.setCollectClassId(menu.getCollectClassId()+"");
				ccp.setCollectClassName(menu.getCollectClassName());
				if(collectClassPriceMap != null) {//获取原始价格信息
					CollectClassPrice  collectClassPrice = collectClassPriceMap.get(ccp.getCollectClassId());
					if(collectClassPrice != null) {
						ccp.setUserMoneyMin(collectClassPrice.getUserMoneyMin());
						ccp.setUserMoneyMax(collectClassPrice.getUserMoneyMax());
						ccp.setCustomMoneyMin(collectClassPrice.getCustomMoneyMin());
						ccp.setCustomMoneyMax(collectClassPrice.getCustomMoneyMax());
					}
					
				}
				ccpList.add(ccp);
			}
			logger.info("组装从缓存中获取的数据--end");
		}else {
			logger.error("根据项目Id:"+ownerId+"从缓存中未获取此项目下的大采集品类");
			throw new BusinessException(BusinessCode.NO_EXIST_IN＿REDIS+"",PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
		}
		return ccpList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月21日
	 * @description 根据申请记录ID查询品类价格详细
	 * @param applyRecordId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CollectClassPrice> queryCollectClassPrices(String applyRecordId)  throws Exception{
		logger.info("根据applyRecordId:"+applyRecordId+"查询采集品类价格信息--start--service");
		List<CollectClassPrice> ccpList  = collectClassPriceDao.queryCollectClassPricesByApplyRecordId(applyRecordId);
		logger.info("根据applyRecordId:"+applyRecordId+"查询采集品类价格信息--end--service");
		if(ccpList != null && ccpList.size() > 0) {
			logger.info("从数据库获取的数据为："+ccpList.toString());
			logger.info("根据品类Id从缓存中获取采集品类基本信息--start");
			for(CollectClassPrice ccp : ccpList) {
				CollectTaskClazz ctc = taskClazzCacheComponent.getCollectTaskClazz(Long.valueOf(ccp.getCollectClassId()));
				if(ctc == null) {
					logger.error("根据品类Id:"+ccp.getCollectClassId()+"未从换成中获取导数据！");
				}
				ccp.setCollectClassName(ctc.getClazzName());
			}
			logger.info("根据品类Id从缓存中获取采集品类基本信息--end");
		}
		return ccpList;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 保存和更新采集品类价格信息
	 * @param requestEntity
	 * @throws Exception
	 */
	@Override
	public void saveCollectClassPrices(RequestEntity requestEntity) throws Exception {
		String ownerId = requestEntity.getOwnerId();
		String collectClassParentId = requestEntity.getCollectClassParentId();
		
		logger.info("申请记录对象初始化属性---start");
		ApplyRecord applyRecord = requestEntity.getApplyRecord();
		if(applyRecord == null) {
			throw new BusinessException(BusinessCode.PARAM_VALUE_ERROR+"", PropertiesConfig.getProperty(BusinessCode.PARAM_VALUE_ERROR+""));
		}
		applyRecord.setOwnerId(ownerId);
		applyRecord.setCollectClassParentId(collectClassParentId);
		applyRecord.setType(ApplyType.UNITPRICE.getCode());
//		applyRecord.setCreateTime(new Date().getTime());
		logger.info("申请记录对象初始化属性---end:"+applyRecord.toString());
		String applyRecordId = applyRecordService.saveApplyRecord(applyRecord);
		logger.info("成功保存申请记录---yearh---yeah");
		//客户ID
		String customId = applyRecord.getCustomId();
		
		logger.info("查询品类的原始价格--start");
		List<CollectClassPrice> originalCCPs =  queryCollectClassPrices(ownerId, collectClassParentId);
		Map<String,CollectClassPrice> ccpMap = null;
		if(originalCCPs != null && originalCCPs.size() > 0) {
			ccpMap = new HashMap<String,CollectClassPrice>();
			for(CollectClassPrice ccp: originalCCPs) {
				ccpMap.put(ccp.getCollectClassId(), ccp);
			}
		}
		logger.info("查询品类的原始价格--end");
		
		List<CollectClassPrice> collectClassPrices = requestEntity.getCollectClassPrices();
		if(collectClassPrices != null && collectClassPrices.size() > 0) {
			logger.info("采集品类价格对象进行status初始化(SUMITAPPLY)---start");
			for(CollectClassPrice collectClassPrice : collectClassPrices) {
				Long id = primaryByRedis.generateEcode();
				collectClassPrice.setId(String.valueOf(id));
				collectClassPrice.setApplyRecordId(applyRecordId);
				collectClassPrice.setOwnerId(ownerId);
				collectClassPrice.setCollectClassParentId(collectClassParentId);
//				collectClassPrice.setCreateTime(new Date().getTime());
				if(ccpMap != null) {
					CollectClassPrice orignalCCp = ccpMap.get(collectClassPrice.getCollectClassId());
					if(orignalCCp != null) {
						collectClassPrice.setOriginalCustomMoneyMin(orignalCCp.getCustomMoneyMin());
						collectClassPrice.setOriginalCustomMoneyMax(orignalCCp.getCustomMoneyMax());
						collectClassPrice.setOriginalUserMoneyMin(orignalCCp.getUserMoneyMin());
						collectClassPrice.setOriginalUserMoneyMax(orignalCCp.getUserMoneyMax());
					}else {
						collectClassPrice.setOriginalCustomMoneyMin(0d);
						collectClassPrice.setOriginalCustomMoneyMax(0d);
						collectClassPrice.setOriginalUserMoneyMin(0d);
						collectClassPrice.setOriginalUserMoneyMax(0d);
					}
				}
			}
			logger.info("采集品类价格对象进行status初始化(AUDITING)---end");
			collectClassPriceDao.saveCollectClassPriceLogs(collectClassPrices);
		}else {
			logger.info("没有要保存的采集品类价格信息---ai--ai");
		}
		
		//检查是否初始化过项目价格信息 ,如果没初始化 则需要初始化
		ProjectPrice projectPrice = collectClassPriceDao.queryProjectPrice(ownerId);
		logger.info("根据项目ID:"+ownerId+"获取项目价格信息:"+projectPrice);
		if(projectPrice == null) {
			logger.info("根据项目ID:"+ownerId+"未获取项目价格信息,需要新增项目价格信息");
			projectPrice = new ProjectPrice();
			Long id = primaryByRedis.generateEcode();
			logger.info("生成Id:"+id);
			projectPrice.setId(String.valueOf(id));
			projectPrice.setCustomId(customId);
			projectPrice.setOwnerId(ownerId);
			projectPrice.setThresholdAmount(0d);
			projectPrice.setBalanceAmount(0d);
			projectPrice.setAdvanceAmount(0d);
//			projectPrice.setCreateTime(new Date().getTime());
			collectClassPriceDao.saveProjectPrice(projectPrice);
			logger.info("保存项目价格信息成功----oh--yeah");
		}
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月18日
	 * @description 查询项目价格信息
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProjectPrice queryProjectPrice(String ownerId) throws Exception {
		logger.info("根据项目ID:"+ownerId+"获取项目价格信息--start");
		ProjectPrice projectPrice = collectClassPriceDao.queryProjectPrice(ownerId);
		logger.info("根据项目ID:"+ownerId+"获取项目价格信息:"+projectPrice+"--end");
		if(projectPrice == null) {
			logger.error("根据项目ID:"+ownerId+"未获取到项目价格信息");
			throw new BusinessException(BusinessCode.PROJECT_PRICE_NO_INIT+"", PropertiesConfig.getProperty(BusinessCode.PROJECT_PRICE_NO_INIT+""));
		}
		return projectPrice;
	}
	
	@Value("${" + PropConstants.GET_COLLECTCLAZZ_TREE_URL + "}")
	private String get_collectclazz_tree_url;
}

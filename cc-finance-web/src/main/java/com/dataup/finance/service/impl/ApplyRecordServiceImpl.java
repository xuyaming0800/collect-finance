package com.dataup.finance.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.TaskService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.CollectTaskClazz;
import com.dataup.finance.bean.Notice;
import com.dataup.finance.bean.Pagination;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.componet.CommonComponent;
import com.dataup.finance.componet.TaskClazzCacheComponent;
import com.dataup.finance.constant.ApplyType;
import com.dataup.finance.constant.AuditType;
import com.dataup.finance.constant.TaskStatus;
import com.dataup.finance.dao.ApplyRecordDao;
import com.dataup.finance.dao.CollectClassPriceDao;
import com.dataup.finance.entity.ApplyDetailEntity;
import com.dataup.finance.entity.RequestEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.service.ApplyRecordService;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.HttpRequestUtil;
import com.dataup.finance.util.PrimaryByRedis;
import com.dataup.finance.util.PropConstants;
import com.dataup.finance.util.PropertiesConfig;
import com.dataup.finance.workflow.AuditProcess;

@Service("applyRecordService")
public class ApplyRecordServiceImpl implements ApplyRecordService {
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ApplyRecordDao applyRecordDao;
	@Autowired
	private PrimaryByRedis primaryByRedis;
	@Autowired
	private TaskClazzCacheComponent taskClazzCacheComponent;
	@Autowired
	private CollectClassPriceDao collectClassPriceDao;
	
	@Autowired
	private AuditProcess auditProcess ;
	@Autowired
	private TaskService taskService ;
	@Autowired
	private CommonComponent commonComponent;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月16日
	 * @description 分页条件查询申请记录
	 * @param requestEntity
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination queryAppyRecords(RequestEntity requestEntity)
			throws Exception {
		logger.info("条件查询申请记录,queryAppyRecords--service-start");
		ApplyRecord applyRecord = new ApplyRecord();
		applyRecord.setOwnerId(requestEntity.getOwnerId());
		applyRecord.setStatus(requestEntity.getStatus());
		applyRecord.setType(requestEntity.getType());
		applyRecord.setApplyName(requestEntity.getSearchContent());
		logger.info("查询信息为:"+applyRecord.toString());
//		String searchContent = requestEntity.getSearchContent();
//		Map<String,Object> customMap = getUsers(get_custom_url,searchContent);
		int pageNo = requestEntity.getPageNo();
		int limit = requestEntity.getLimit();
		Pagination page = null;
		List<ApplyRecord> applyRecords =  null;
		
		List<String> applyIdList = null;
		if(applyRecord.getApplyName() != null && !"".equals(applyRecord.getApplyName())) {
			applyIdList = commonComponent.getApplyIdList(applyRecord.getApplyName(), PropertiesConfig.getProperty("applyRoleCode",""), PropertiesConfig.getProperty("applyBsId",""), get_apply_url);
//			applyIdList = new ArrayList<String>();
//			//根据申请名称获取申请人Map
//			Map<String,Object> applyMapAll = HttpRequestUtil.getUsers(get_apply_url,applyRecord.getApplyName());
//			if(applyMapAll != null) {
//				for(Entry<String,Object > entry : applyMapAll.entrySet()) {
//					Map user = (Map)entry.getValue();
//					applyIdList.add((String)user.get("id"));
//				}
//			}
		}
		logger.info("申请人查询条件为:"+applyIdList);
		if(pageNo == 0) {//不分页
			page = new Pagination();
			applyRecords = applyRecordDao.queryAppyRecords(applyRecord,applyIdList, 0, 0);
		}else {//分页
			if(limit == 0) {
				limit = Integer.valueOf(PropertiesConfig.getProperty(PropConstants.LIMIT));
			}
			page = new Pagination(requestEntity.getPageNo(),requestEntity.getLimit());
			applyRecords = applyRecordDao.queryAppyRecords(applyRecord,applyIdList, page.getStart(), page.getLimit());
			long totalCount = applyRecordDao.queryAppyRecordsCount(applyRecord,applyIdList);
			page.setTotalCount(totalCount);
			logger.info("获取的总记录数为:"+totalCount);
		}
		logger.info("初始化申请记录基本信息--start");
		if(applyRecords != null) {
			//获取客户Map
			Map<String, Object> customMapAll = null;
			//获取审核人Map
			Map<String, Object> auditMapAll = null;
			//获取申请人Map
			Map<String, Object> applyMapAll = null;
			Map  user = null;
			for(ApplyRecord ar : applyRecords) {
				//返显中文名称
				Object applyUser = commonComponent.getUser(ar.getApplyId(), applyMapAll, get_apply_url);
				if(applyUser == null) {
					ar.setApplyName("");
				}else {
					user = (Map)applyUser;
					ar.setApplyName((String)user.get("name"));
				}
				
				Object auditUser = commonComponent.getUser(ar.getAuditId(), auditMapAll, get_audit_url);
				if(auditUser == null) {
					ar.setAuditName("");
				}else {
					user = (Map)auditUser;
					ar.setAuditName((String)user.get("name"));
				}
				
				Object customUser = commonComponent.getUser(ar.getCustomId(), customMapAll, get_custom_url);
				if(customUser == null) {
					ar.setCustomName("");
				}else {
					user = (Map)customUser;
					ar.setCustomName((String)user.get("name"));
				}
				
				if(ApplyType.UNITPRICE.getCode() == applyRecord.getType()) {//单价设置 查询品类信息
					CollectTaskClazz ctc = taskClazzCacheComponent.getCollectTaskClazz(Long.valueOf(ar.getCollectClassParentId()));
					if(ctc == null) {
						logger.error("根据品类Id:"+ar.getCollectClassParentId()+"未从缓存中获取到数据！");
					}
					ar.setCollectClassParentName(ctc.getClazzName());
				}
				
			}
		}
		logger.info("初始化申请记录基本信息--end");
		logger.info("查询结果集为:"+applyRecords);
		logger.info("条件查询申请记录,queryAppyRecords--service-end");
		page.setObjectList(applyRecords);
		return page;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月14日
	 * @description 保存申请记录
	 * @param applyRecord
	 */
	@Override
	public String saveApplyRecord(ApplyRecord applyRecord) throws Exception {
		logger.info("保存申请记录--service--start");
		//只有类型不是设置单价 才验证项目是否存在
		if(applyRecord.getType() != ApplyType.UNITPRICE.getCode()) {
			if(!checkProjectPriceExist(applyRecord.getOwnerId())) {
				//项目不存在，则不能申请充值,垫付,退款,阀值修改,先设置项目品类单价 初始化项目
				throw new BusinessException(BusinessCode.PROJECT_PRICE_NO_INIT+"",PropertiesConfig.getProperty(BusinessCode.PROJECT_PRICE_NO_INIT+""));
			}
		}
		
		Long id = primaryByRedis.generateEcode();
		logger.info("生成Id:"+id);
		applyRecord.setId(String.valueOf(id));
//		applyRecord.setCreateTime(new Date().getTime());
		ProjectPrice projectPrice = collectClassPriceDao.queryProjectPrice(applyRecord.getOwnerId());
		if(projectPrice != null) {
			applyRecord.setBalanceAmount(CommonUtil.sub(projectPrice.getBalanceAmount(), projectPrice.getAdvanceAmount()));
			applyRecord.setAdvanceAmount(projectPrice.getAdvanceAmount());	
		}
		if(ApplyType.THRESHOLD.getCode() == applyRecord.getType()) {
			logger.info("阀值修改，需要保存修改之前的阀值！");
			if(projectPrice != null) {
				applyRecord.setOriginalMoney(projectPrice.getThresholdAmount());
			}else {
				logger.error("项目Id:"+applyRecord.getOwnerId()+" 还未初始化！");
			}
		}
		logger.info("申请记录ID:"+id+"启动流程---start");
		
		Map<String, Object> variableMap = PropertyUtils.describe(applyRecord);
		variableMap.put("auditType", AuditType.FINANCETYPE.getCode());
		Map<String, String> resultMap = auditProcess.startAuditProcess(null,String.valueOf(id),variableMap);// 插入相关数据
		logger.entry(resultMap);
		logger.info("resultMap.get(\"processDefinitionId\")="+ resultMap.get("processDefinitionId"));
		logger.info("申请记录ID:"+id+"启动流程---end");
		
		applyRecord.setProcessDefinitionId(resultMap.get("processDefinitionId"));// 流程定义ID
		logger.info("resultMap.get(\"processInstanceId\")="+ resultMap.get("processInstanceId"));
		applyRecord.setProcessInstanceId(resultMap.get("processInstanceId"));// 流程定义ID
		logger.info("TaskStatus.AUDITING="+ TaskStatus.AUDITING.getCode());
		applyRecord.setStatus(TaskStatus.AUDITING.getCode());// 状态为审批中
		
		applyRecordDao.saveApplyRecord(applyRecord);
		logger.info("保存申请记录--service--end");

		return String.valueOf(id);
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 验证当前项目是否存在
	 * @param ownerId
	 * @return true 存在,false 不存在
	 * @throws Exception
	 */
	public boolean checkProjectPriceExist(String ownerId) throws Exception {
		//检查是否初始化过项目价格信息 ,如果没初始化 则需要初始化
		ProjectPrice projectPrice = collectClassPriceDao.queryProjectPrice(ownerId);
		logger.info("根据项目ID:"+ownerId+"获取项目价格信息:"+projectPrice);
		if(projectPrice == null) {
			logger.error("根据项目ID:"+ownerId+"未获取项目价格信息,则不能申请充值，垫付，退款，阀值修改");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 申请是否未被审核
	 * @param customId
	 * @param ownerId
	 * @param collectClassParentId
	 * @return true:已审核;false:未被审核
	 * @throws Exception
	 */
	@Override
	public boolean checkApplyIsAudit(String customId, String ownerId,
			String collectClassParentId) throws Exception {
		logger.info("根据customId:"+customId+",ownerId:"+ownerId+"和collectClassParentId:"+collectClassParentId+"查询采集品类价格申请信息数量.......");

		boolean result = true;
		long count = applyRecordDao.checkApplyIsAudit(customId, ownerId, collectClassParentId);
		if(count > 0) {
			result  = false;
		}
		return result;
	}

	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月24日
	 * @description 查询通告信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String,Integer> queryNotice() throws Exception {
		// 从session中得到用户信息
		Map<String, Object> userInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("userInfo");// 从session里获取用户名
		String userId = (String)userInfo.get("id");
		String name = (String)userInfo.get("name");
		logger.info("查询用户名称:"+name+",用户ID:"+userId+"的通告信息");
		List<Notice> notices = applyRecordDao.queryNotice(userId);
		logger.info("通告信息结果集:"+notices);
		Map<String,Integer> noticeMap = new HashMap<String,Integer>();
		if(notices != null && notices.size() > 0) {
			for(Notice notice : notices) {
				noticeMap.put(notice.getTypeName(), notice.getTypeCount());
			}
		}
		return noticeMap;
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
	public ApplyDetailEntity queryCollectClassPrices(String applyRecordId)  throws Exception{
		logger.info("根据applyRecordId:"+applyRecordId+"查询采集品类价格信息--start--service");
		ApplyRecord applyRecord = applyRecordDao.queryAuditRecord(applyRecordId);
		List<CollectClassPrice> ccpList  = applyRecordDao.queryCollectClassPricesByApplyRecordId(applyRecordId);
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
				CollectTaskClazz ctcp = taskClazzCacheComponent.getCollectTaskClazz(Long.valueOf(ccp.getCollectClassParentId()));
				if(ctcp == null) {
					logger.error("根据品类父Id:"+ccp.getCollectClassParentId()+"未从换成中获取导数据！");
				}
				ccp.setCollectClassParentName(ctcp.getClazzName());
			}
			logger.info("根据品类Id从缓存中获取采集品类基本信息--end");
		}else {
			throw new BusinessException(BusinessCode.APPLY_RECORD_NO_EXIST+"", PropertiesConfig.getProperty(BusinessCode.APPLY_RECORD_NO_EXIST+""));
		}
		ApplyDetailEntity applyDetailEntity = new ApplyDetailEntity();
		applyDetailEntity.setApplyRecord(applyRecord);
		applyDetailEntity.setDetails(ccpList);
		return applyDetailEntity;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 根据Id 查询审核记录
	 * @param applyRecordId
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApplyRecord queryApplyRecord(String applyRecordId) throws Exception {
		logger.info("根据applyRecordId:"+applyRecordId+"查询申请记录审核信息--start--service");
		ApplyRecord  applyRecord = applyRecordDao.queryAuditRecord(applyRecordId);
		logger.info("根据applyRecordId:"+applyRecordId+"查询申请记录审核信息--end--service");
		logger.info("初始化申请记录基本信息--start");
		if(applyRecord != null) {
			//获取客户Map
			Map<String, Object> customMapAll = null;
			//获取审核人Map
			Map<String, Object> auditMapAll = null;
			//获取申请人Map
			Map<String, Object> applyMapAll = null;
			//获取项目Map
			Map<String,Object> projectMapAll = null;
			//项目负责人
			Map<String,Object> projectLeaderMapAll = null;
			//返显中文名称
			getName(applyRecord, customMapAll, auditMapAll, applyMapAll, projectMapAll, projectLeaderMapAll);
		}else {
			throw new BusinessException(BusinessCode.APPLY_RECORD_NO_EXIST+"", PropertiesConfig.getProperty(BusinessCode.APPLY_RECORD_NO_EXIST+""));
		}
		logger.info("初始化申请记录基本信息--end");
		return applyRecord;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月21日
	 * @description 返显中文名称 
	 * @param applyRecord
	 * @param customMapAll
	 * @param auditMapAll
	 * @param applyMapAll
	 * @param projectMapAll
	 * @param projectLeaderMapAll
	 * @throws Exception
	 */
	public void getName(ApplyRecord applyRecord,Map<String, Object> customMapAll,Map<String, Object> auditMapAll,Map<String, Object> applyMapAll,Map<String,Object> projectMapAll,Map<String,Object> projectLeaderMapAll) throws Exception{
		Map  user = null;
		//返显中文名称
		Object applyUser = commonComponent.getUser(applyRecord.getApplyId(), applyMapAll, get_apply_url);
		if(applyUser == null) {
			applyRecord.setApplyName("");
		}else {
			user = (Map)applyUser;
			applyRecord.setApplyName((String)user.get("name"));
		}
		
		Object auditUser = commonComponent.getUser(applyRecord.getAuditId(), auditMapAll, get_audit_url);
		if(auditUser == null) {
			applyRecord.setAuditName("");
		}else {
			user = (Map)auditUser;
			applyRecord.setAuditName((String)user.get("name"));
		}
		
		Object customUser = commonComponent.getUser(applyRecord.getCustomId(), customMapAll, get_custom_url);
		if(customUser == null) {
			applyRecord.setCustomName("");
		}else {
			user = (Map)customUser;
			applyRecord.setCustomName((String)user.get("name"));
		}
		
		Object projectInfo = commonComponent.getProjectInfo(applyRecord.getOwnerId(), projectMapAll, get_projects_url);
		if(projectInfo == null) {
			applyRecord.setProjectName("");
			applyRecord.setProjectLeaderName("");
		}else {
			Map project = (Map)projectInfo;
			applyRecord.setProjectName((String)project.get("projectName"));
			String projectLeaderId = (String)project.get("projectLeaderId");//获取项目负责人ID
			Object projectLeaderUser = commonComponent.getUser(projectLeaderId, projectLeaderMapAll, get_project_leader_url);
			if(projectLeaderUser == null) {
				applyRecord.setProjectLeaderName("");
			}else {
				user = (Map)projectLeaderUser;
				applyRecord.setProjectLeaderName((String)user.get("name"));
			}
		}
	}
	

	@Value("${" + PropConstants.GET_CUSTOM_URL + "}")
	private String get_custom_url;
	@Value("${" + PropConstants.GET_AUDIT_URL + "}")
	private String get_audit_url;
	@Value("${" + PropConstants.GET_APPLY_URL + "}")
	private String get_apply_url;
	@Value("${" + PropConstants.GET_PROJECTS_URL + "}")
	private String get_projects_url;
	@Value("${" + PropConstants.GET_PROJECT_LEADER_URL + "}")
	private String get_project_leader_url;
	
}

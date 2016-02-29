package com.dataup.finance.audit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dataup.finance.audit.dao.AuditDao;
import com.dataup.finance.audit.service.AuditService;
import com.dataup.finance.bean.ApplyRecord;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.CollectTaskClazz;
import com.dataup.finance.bean.Notice;
import com.dataup.finance.bean.Pagination;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.bean.ProjectPriceLog;
import com.dataup.finance.componet.CommonComponent;
import com.dataup.finance.componet.TaskClazzCacheComponent;
import com.dataup.finance.constant.ApplyType;
import com.dataup.finance.constant.AuditType;
import com.dataup.finance.constant.TaskStatus;
import com.dataup.finance.entity.ActivitiEntity;
import com.dataup.finance.entity.ApplyDetailEntity;
import com.dataup.finance.entity.AuditEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.exception.TaskNotFoundException;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.HttpRequestUtil;
import com.dataup.finance.util.PropConstants;
import com.dataup.finance.util.PropertiesConfig;
import com.dataup.finance.workflow.AuditProcess;
@Service("auditService")
public class AuditServiceImpl implements AuditService {

	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private AuditDao auditDao;
	
	@Autowired
	private AuditProcess auditProcess ;
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskClazzCacheComponent taskClazzCacheComponent;
	@Autowired
	private CommonComponent commonComponent;
	@Override
	public Pagination queryAuditRecords(AuditEntity auditEntity) throws Exception {
		logger.info("条件查询审核申请记录,queryAuditRecords--service-start");
		Map<String, Object> userInfoMap = (Map<String, Object>)SecurityUtils.getSubject().getSession().getAttribute("userInfo");
		logger.info("当前用户信息:"+userInfoMap);
		ApplyRecord applyRecord = new ApplyRecord();
		applyRecord.setStatus(auditEntity.getStatus());
		applyRecord.setType(auditEntity.getType());
		applyRecord.setAuditId((String)userInfoMap.get("id"));
		logger.info("查询信息为:"+applyRecord.toString());
		
		String serachContent = auditEntity.getSearchContent();
		int pageNo = auditEntity.getPageNo();
		int limit = auditEntity.getLimit();
		Pagination page = null;
		List<ApplyRecord> applyRecords =  null;
		List<String> customIdList = null;
		if(serachContent != null && !"".equals(serachContent)) {
			customIdList = commonComponent.getCustomIdList(serachContent, PropertiesConfig.getProperty("customUsertype",""), get_custom_url);
			/*customIdList = new ArrayList<String>();
			//根据申请名称获取客户Map
			Map<String,Object> customMap = HttpRequestUtil.getUsers(get_custom_url,serachContent);
			if(customMap != null) {
				for(Entry<String,Object > entry : customMap.entrySet()) {
					Map user = (Map)entry.getValue();
					customIdList.add((String)user.get("id"));
				}
			}*/
		}
		logger.info("客户查询信息为:"+customIdList);
		List<String> ownerIdList = null;
		if(serachContent != null && !"".equals(serachContent)) {
			ownerIdList = commonComponent.getOwnerIdList(serachContent, get_projects_url);
			/*ownerIdList = new ArrayList<String>();
			//根据申请名称获取客户Map
			Map<String,Object> projectMap = HttpRequestUtil.getProjects(get_projects_url,"&projectName="+serachContent);
			if(projectMap != null) {
				for(Entry<String,Object > entry : projectMap.entrySet()) {
					Map project = (Map)entry.getValue();
					ownerIdList.add((String)project.get("id"));
				}
			}*/
		}
		logger.info("项目查询信息为:"+customIdList);
		if(pageNo == 0) {//不分页
			page = new Pagination();
			applyRecords = auditDao.queryAuditRecords(applyRecord, customIdList, ownerIdList, 0, 0);
			
		}else {//分页
			if(limit == 0) {
				limit = Integer.valueOf(PropertiesConfig.getProperty(PropConstants.LIMIT));
			}
			page = new Pagination(auditEntity.getPageNo(),auditEntity.getLimit());
			applyRecords = auditDao.queryAuditRecords(applyRecord, customIdList, ownerIdList,page.getStart(), page.getLimit());
			long totalCount = auditDao.queryAuditRecordsCount(applyRecord, customIdList, ownerIdList);
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
			//获取项目Map
			Map<String,Object> projectMapAll = null;
			//项目负责人
			Map<String,Object> projectLeaderMapAll = null;
			
			for(ApplyRecord ar : applyRecords) {
				//返显中文名称
				getName(ar, customMapAll, auditMapAll, applyMapAll, projectMapAll, projectLeaderMapAll);
			}
		}
		logger.info("初始化申请记录基本信息--end");
		
		page.setObjectList(applyRecords);
		logger.info("查询结果集为:"+applyRecords);
		logger.info("条件查询审核申请记录,queryAuditRecords--service-end");
		return page;
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
		ApplyRecord applyRecord = auditDao.queryAuditRecord(applyRecordId);
		List<CollectClassPrice> ccpList  = auditDao.queryCollectClassPricesByApplyRecordId(applyRecordId);
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
	 * 更新主表状态事件-更新主表状态
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @param executionId
	 */
	public void updateStatusEvent(String processInstanceId, String executionId,final int status) throws Exception{
		// 查询业务ID
		String bsTaskId = auditProcess.findBusinessKeyByProcessInstanceId(processInstanceId);
		auditProcess.setVariables(executionId, new HashMap() {
			{
				put("status", status);
			}
		});
		// 更新主表
		auditDao.updateStatus(bsTaskId, status);
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
	public ApplyRecord queryAuditRecord(String applyRecordId) throws Exception {
		logger.info("根据applyRecordId:"+applyRecordId+"查询申请记录审核信息--start--service");
		ApplyRecord  applyRecord = auditDao.queryAuditRecord(applyRecordId);
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
	 * @date 2015年9月23日
	 * @description 审核(认领和审核)
	 * @param applyRecordId  任务Id
	 * @param status
	 * @param auditOpinion
	 * @throws Exception
	 */
	@Override
	public void doAudit(String applyRecordId, int status, String auditOpinion)
			throws Exception {
		// 从session中得到用户名
		String userName = (String) SecurityUtils.getSubject().getSession()
				.getAttribute("userName");// 从session里获取用户名
		Map<String, Object> userInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("userInfo");// 从session里获取用户名
		String userId = (String)userInfo.get("id");
		logger.info("userName=" + userName+",userId="+userId);
		Map<String, Object> map = findTaskByApplyRecordId(applyRecordId);
		logger.info("内存变量："+map+";executionId:"+map.get("executionId"));
		
		String taskId = "";
		if(map != null) {
			if (map.containsKey("taskId")) {
				taskId = (String) map.get("taskId");
			}else {
				throw new BusinessException(BusinessCode.TASK_ID_NO_EXIST+"",PropertiesConfig.getProperty(BusinessCode.TASK_ID_NO_EXIST+""));
			}
		}else {
			throw new BusinessException(BusinessCode.TASK_ID_NO_EXIST+"",PropertiesConfig.getProperty(BusinessCode.TASK_ID_NO_EXIST+""));
		}
		logger.info("认领任务,任务ID:"+taskId+"--start");
		taskService.setAssignee(taskId,userId);
		logger.info("认领任务,任务ID:"+taskId+"--end");

		// 更新审核意见和状态
		boolean flag = true;
		if(status == TaskStatus.AUDITPASS.getCode()) {
			flag = true;
		}else if(status == TaskStatus.AUDITNOPASS.getCode()) {
			flag = false;
		}
		auditDao.updateAuditOpinion(applyRecordId, auditOpinion);
		logger.info("更新审核意见完成,审核意见为:"+auditOpinion);
		
		logger.info("更新activiti的comment--start");
		auditProcess.addComment(taskId, String.valueOf(map.get("processInstanceId")), userId, auditOpinion);
		logger.info("更新activiti的comment--end");
		
		doAuditProcess(taskId,flag);
		logger.info("审核完成,审核状态为:"+status);
		
		
		if (auditProcess.isFinished(String.valueOf(map.get("processInstanceId")))) {
			ActivitiEntity activitiEntity = auditDao.queryActivitiCompleteByTaskId(taskId);
			logger.info("更新审核完成时间,完成时间为:"+activitiEntity.getEndTime());
			auditDao.updateAuditTime(applyRecordId, activitiEntity.getEndTime().getTime());
		}
		//去金额变更
		setProjectPrice(applyRecordId,flag);
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 更新项目价格
	 * @param operateId
	 * @throws Exception
	 */
	public void setProjectPrice(String operateId,boolean flag) throws Exception {
		ApplyRecord applyRecord = auditDao.queryAuditRecord(operateId);
		int type = applyRecord.getType();
		if(flag) {//审核通过
			logger.info("申请金额变更,申请类型:"+type+"--start");
			if(type == ApplyType.UNITPRICE.getCode()) {//单价设置
			
				logger.info("更新品类价格--start");
				List<CollectClassPrice> collectClassPrices = auditDao.queryCollectClassPricesByApplyRecordId(applyRecord.getId());
				String ownerId = "";
				String collectClassParentId = "";
				if(collectClassPrices != null && collectClassPrices.size() > 0) {
					CollectClassPrice collectClassPrice = collectClassPrices.get(0);
					ownerId = collectClassPrice.getOwnerId();
					collectClassParentId = collectClassPrice.getCollectClassParentId();
					logger.info("非第一次设置单价先删除原有的价格再新增");
					//先删除原有的价格再新增
					auditDao.deleteCollectClassPrices(ownerId, collectClassParentId);
				}
				logger.info("操作结果:"+collectClassPrices.toString());
				auditDao.saveCollectClassPrices(collectClassPrices);
				logger.info("更新品类价格--end");
				
			}else {
				ProjectPrice projectPrice = auditDao.queryProjectPrice(applyRecord.getOwnerId());
				//保存历史记录
				saveProjectPriceLog(applyRecord, projectPrice,TaskStatus.AUDITPASS.getCode());
				Double money = applyRecord.getMoney();
				if(type == ApplyType.RECHARGE.getCode()) {//充值
					logger.info("充值类型==>如果有垫付金额,先填补垫付（即先扣除垫付金额,如果充值金额大于垫付金额,垫付金额执为0,剩余金额用来充值余额,如果充值金额小于垫付金额,只能先填补垫付,余额不能增加）");
					//充值,如果有垫付金额,先填补垫付（即先扣除垫付金额,如果充值金额大于垫付金额,垫付金额执为0,剩余金额用来充值余额,如果充值金额小于垫付金额,只能先填补垫付,余额不能增加）
					Double advanceAmount = projectPrice.getAdvanceAmount();
					if(money > advanceAmount) {
						projectPrice.setAdvanceAmount(0d);
						projectPrice.setBalanceAmount(CommonUtil.sub(CommonUtil.add(projectPrice.getBalanceAmount(), money), advanceAmount));//现有余额先加上充值金额再减去垫付金额,即为更新后的余额
					}else {
						projectPrice.setAdvanceAmount(CommonUtil.sub(advanceAmount,money));//填补垫付金额
					}
				}else if(type == ApplyType.ADVANCED.getCode()) {//垫付
					logger.info("垫付类型==>垫付金额增加余额相应增加");
					//垫付金额增加余额相应增加
					projectPrice.setAdvanceAmount(CommonUtil.add(projectPrice.getAdvanceAmount(), money));
					projectPrice.setBalanceAmount(CommonUtil.add(projectPrice.getBalanceAmount(),money));
					
				}else if(type == ApplyType.REFUND.getCode()) {//退款
					logger.info("退款类型==>如果垫付为0,余额相应减少,如果垫付不为0,退款金额最大不超过余额减去垫付金额！");
					if(CommonUtil.sub(money,CommonUtil.sub(projectPrice.getBalanceAmount(),projectPrice.getAdvanceAmount())) > 0) {
						logger.error("退款金额不能大于有效余额（余额-垫付）,退款金额为:"+money+",有效余额为:"+CommonUtil.sub(projectPrice.getBalanceAmount(),projectPrice.getAdvanceAmount()));
						throw new BusinessException(BusinessCode.REFUND_MORE_BIG+"", PropertiesConfig.getProperty(BusinessCode.REFUND_MORE_BIG+""));
					}
					//退款,余额相应减少
					projectPrice.setBalanceAmount(CommonUtil.sub(projectPrice.getBalanceAmount(), money));
					
				}else if(type == ApplyType.THRESHOLD.getCode()) {//阀值修改
					logger.info("阀值修改类型==>阀值相应变化");
					//阀值修改,阀值相应变化
					projectPrice.setThresholdAmount(money);
				}
				logger.info("操作结果为:"+projectPrice.toString());
				auditDao.updateProjectPrice(projectPrice);
//				logger.info("修改当前申请记录的当前余额.......");
//				applyRecord.setCurrentBalanceAmount(CommonUtil.sub(projectPrice.getBalanceAmount(), projectPrice.getAdvanceAmount()));
//				applyRecord.setUpdateTime(new Date().getTime());
//				auditDao.updateCurrentBalanceAmount(applyRecord);
			}
			logger.info("申请金额变更,申请类型:"+type+"--end");
			
		}else {//审核不通过,也记录日志
			if(type != ApplyType.UNITPRICE.getCode()) {//非单价设置审核记录日志
				logger.warn("申请金额变更审核不通过,申请类型:"+type);
				ProjectPrice projectPrice = auditDao.queryProjectPrice(applyRecord.getOwnerId());
				saveProjectPriceLog(applyRecord, projectPrice, TaskStatus.AUDITNOPASS.getCode());
			}
		}
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 插入项目金额历史记录
	 * @param applyRecord
	 * @param projectProject
	 * @throws Exception
	 */
	public void saveProjectPriceLog(ApplyRecord applyRecord,ProjectPrice projectPrice,int status) throws Exception {
		logger.info("插入项目金额历史记录");
		ProjectPriceLog projectPriceLog = new ProjectPriceLog();
		projectPriceLog.setCustomId(projectPrice.getCustomId());
		projectPriceLog.setOwnerId(projectPrice.getOwnerId());
		projectPriceLog.setAdvanceAmount(projectPrice.getAdvanceAmount());
		projectPriceLog.setBalanceAmount(projectPrice.getBalanceAmount());
		projectPriceLog.setThresholdAmount(projectPrice.getThresholdAmount());
		projectPriceLog.setOperateId(applyRecord.getId());
		projectPriceLog.setOperateAmount(applyRecord.getMoney());
		projectPriceLog.setOperateType(applyRecord.getType());
		projectPriceLog.setStatus(status);
		auditDao.saveProjectPriceLog(projectPriceLog);
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 更新采集品类价格（只更新价格变化的品类）,以便后期支持单条或者多条定向修改
	 * @param collectClassPrices
	 */
	public void updateCollectClassPrice(List<CollectClassPrice> collectClassPrices)  throws Exception {
		if(collectClassPrices != null) {
			for(CollectClassPrice collectClassPrice:collectClassPrices) {
				if(collectClassPrice.getCustomMoneyMin() != collectClassPrice.getOriginalUserMoneyMin() || collectClassPrice.getCustomMoneyMax() != collectClassPrice.getOriginalUserMoneyMax() || collectClassPrice.getCustomMoneyMin() != collectClassPrice.getOriginalCustomMoneyMin() || collectClassPrice.getCustomMoneyMax() != collectClassPrice.getOriginalCustomMoneyMax()) {
					logger.info("变化采集品类为:"+collectClassPrice.toString());
					auditDao.updateCollectClassPrice(collectClassPrice);	
				}
			}
		}
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月23日
	 * @description 获取当前任务的内容变量
	 * @param applyRecordId
	 * @return
	 */
	public Map<String, Object> findTaskByApplyRecordId(String applyRecordId) {
		Map<String, Object> variableValue = new HashMap<String, Object>();
		variableValue.put("auditType", AuditType.FINANCETYPE.getCode());
		// 从session中得到角色名
		List<String> roles = (List<String>) SecurityUtils.getSubject().getSession().getAttribute("roles");
		// 查询代办任务
		List<Map<String, Object>> taskList = auditProcess.findTaskByUserOrGroup(null, roles, variableValue);
		if (taskList != null && !taskList.isEmpty()) {
			logger.info("通过业务ID查询业务详情开始");
			for (Map<String, Object> map : taskList) {
				logger.info("遍历任务");
				if(applyRecordId.equals(String.valueOf(map.get("bsTaskId")))) {
					return map;
				}
			}
		}
		return null;
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
		List<Notice> notices = auditDao.queryNotice(userId);
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
	 * @Description: 走动流程,重构方法
	 * @author 刘旭升
	 * @date 2015年8月7日 下午5:08:14 
	 * @version V1.0 
	 * @param taskId
	 * @param auditFlag
	 * @throws TaskNotFoundException
	 */
	private void doAuditProcess(String taskId, boolean auditFlag )
			throws Exception {
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("flag", auditFlag);// 是否通过的标记
		variableMap.put("beConfirmed", false);// 待确认标记为false
		auditProcess.complete(taskId, variableMap);// 通过
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

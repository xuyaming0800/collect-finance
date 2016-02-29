package com.dataup.finance.openapi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.MailInfo;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.bean.ProjectPriceLog;
import com.dataup.finance.bean.TaskFlow;
import com.dataup.finance.componet.CommonComponent;
import com.dataup.finance.componet.SendMail;
import com.dataup.finance.componet.SendMailThread;
import com.dataup.finance.constant.ApplyType;
import com.dataup.finance.dao.CollectClassPriceDao;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;
import com.dataup.finance.openapi.service.ProjectService;
import com.dataup.finance.util.CommonUtil;
import com.dataup.finance.util.HttpRequestUtil;
import com.dataup.finance.util.JsonBinder;
import com.dataup.finance.util.PropConstants;
import com.dataup.finance.util.PropertiesConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 对外接口 提供查询项目相关信息 和 任务扣款
 * @author wenpeng.jin
 *
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private CollectClassPriceDao collectClassPriceDao;
	@Autowired
	private SendMail sendMail;
	@Autowired
	private CommonComponent commonComponent;

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 查询采集品类的价格
	 * @param ownerId
	 * @param collectClassParentId
	 * @param collectClassId
	 * @return
	 * @throws Exception
	 */
	@Override
	public CollectClassPrice queryCollectClassPrice(String ownerId,String collectClassParentId, String collectClassId)
			throws Exception {
		logger.info("进入queryCollectClassPrice方法查询品类价格,查询条件为-->ownerId:"+ownerId+",collectClassParentId:"+collectClassParentId+",collectClassId:"+collectClassId+"--service--start");
		CollectClassPrice  collectClassPrice = collectClassPriceDao.queryCollectClassPrice(ownerId, collectClassParentId, collectClassId);
		if(collectClassPrice == null) {
			logger.error("Error---查询无此品类价格,查询条件为-->ownerId:"+ownerId+",collectClassParentId:"+collectClassParentId+",collectClassId:"+collectClassId);
			throw new BusinessException(BusinessCode.NO_EXIST_COLLECT_CLASS_PRIECT+"", PropertiesConfig.getProperty(BusinessCode.NO_EXIST_COLLECT_CLASS_PRIECT+""));
		}
		logger.info(collectClassPrice.toString());
		logger.info("进入queryCollectClassPrice方法查询品类价格,查询条件为-->ownerId:"+ownerId+",collectClassParentId:"+collectClassParentId+",collectClassId:"+collectClassId+"--service--end");
		
		return collectClassPrice;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 根据项目ID或者客户ID查询项目价格信息
	 * @param ownerId
	 * @param customId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ProjectPrice> queryProjectPrices(String ownerId, String customId)  throws Exception{
		logger.info("进入queryProjectPrices方法查询项目价格信息,查询条件为-->ownerId:"+ownerId+",customId:"+customId+"--service--start");
		List<ProjectPrice> projectPrices = null;
		if(StringUtils.isEmpty(ownerId) && StringUtils.isEmpty(customId) ) {
			logger.error("Error---请输入至少一个有效参数");
			throw new BusinessException(BusinessCode.PARAM_VALUE_ERROR+"", PropertiesConfig.getProperty(BusinessCode.PARAM_VALUE_ERROR+""));
		}else {
			projectPrices = collectClassPriceDao.queryProjectPrices(ownerId, customId);
		}
		if(projectPrices == null) {
			logger.error("Error---查询无此项目价格信息,查询条件为-->ownerId:"+ownerId+",customId:"+customId);
			throw new BusinessException(BusinessCode.NO_EXIST_COLLECT_CLASS_PRIECT+"", PropertiesConfig.getProperty(BusinessCode.NO_EXIST_COLLECT_CLASS_PRIECT+""));
		}
		for(ProjectPrice projectPrice : projectPrices){
			Double totalPay = collectClassPriceDao.queryTotalPay(ownerId);
			if(totalPay == null) {
				totalPay = 0d;
			}
			projectPrice.setTotalPay(totalPay);
		}
		logger.info("查询结果集为:"+projectPrices.toString());
		logger.info("进入queryProjectPrices方法查询项目价格信息,查询条件为-->ownerId:"+ownerId+",customId:"+customId+"--service--end");
		return projectPrices;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 客户扣款
	 * @param content 需要扣款的任务信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public String customDebit(String content)  throws Exception{
		String message = "";
		logger.info("进入customDebit方法,客户扣款--service--start");
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		List<TaskFlow> taskFlows = jb.fromJson(content, List.class,jb.getCollectionType(List.class, TaskFlow.class));
		String ownerId = "";
		String collectClassParentId="";
		String taskId = "";
		if(taskFlows != null && taskFlows.size() > 0) {
			TaskFlow taskFlow = taskFlows.get(0);
			ownerId = taskFlow.getOwnerId();
			collectClassParentId = taskFlow.getCollectClassParentId();
			taskId = taskFlow.getTaskId();
			logger.info("获取的项目ID为:"+ownerId+",采集品类父类ID为:"+collectClassParentId);
		}else {
			throw new BusinessException(BusinessCode.NO_TASK_FLOW+"", PropertiesConfig.getProperty(BusinessCode.NO_TASK_FLOW+""));//无任务可以扣款
		}
		//支付记录日志  如果count > 0 此任务已被支付 不能再次支付
		long count = collectClassPriceDao.getCustomDebitLog(ownerId, taskId);
		if(count > 0) {
			throw new BusinessException(BusinessCode.TASK_PAY+"", PropertiesConfig.getProperty(BusinessCode.TASK_PAY+""));//此任务已被支付,不能重复支付
		}
		//获取项目信息
		ProjectPrice projectPrice = collectClassPriceDao.queryProjectPrice(ownerId);
		//验证项目金额是否充足
		logger.info("验证项目金额是否充足......");

		boolean flag = validateBalanceAmount(projectPrice);
		flag = true;
		if(!flag) {
			message = "该项目已暂停！";
		}
		Map<String,List<TaskFlow>> taskMap = new HashMap<String,List<TaskFlow>>();
		logger.info("按采集品类小类,组装数据........");
		for(TaskFlow taskFlow : taskFlows) {
			if(taskMap.get(taskFlow.getCollectClassId()) == null) {
				List<TaskFlow> tfList = new ArrayList<TaskFlow>();
				taskMap.put(taskFlow.getCollectClassId(), tfList);
			}
			taskMap.get(taskFlow.getCollectClassId()).add(taskFlow);
		}
		logger.info("任务扣款前的项目信息:"+projectPrice.toString());
		double totalOperateMoney = 0.00d;
		double originalBalanceAmount = projectPrice.getBalanceAmount();//扣款钱的余额
		for(Entry<String, List<TaskFlow>> entry : taskMap.entrySet()) {
			String collectClassId = entry.getKey();
			CollectClassPrice ccp = collectClassPriceDao.queryCollectClassPrice(ownerId, collectClassParentId, collectClassId);
			if(ccp == null) {
				throw new BusinessException(BusinessCode.NO_COLLECT_CLASS_PRICE+"", PropertiesConfig.getProperty(BusinessCode.NO_COLLECT_CLASS_PRICE+""));
			}
			List<TaskFlow> tfList = entry.getValue();
			for(TaskFlow tf : tfList) {
				tf.setCustomMoneyMax(ccp.getCustomMoneyMax());
				tf.setCustomMoneyMin(ccp.getCustomMoneyMin());
				tf.setStatus(flag ? 1:0);
//				tf.setCreateTime(new Date().getTime());
			}
			logger.info("采集品类ID为:"+collectClassId+"共扣款金额为:"+CommonUtil.mul(ccp.getCustomMoneyMax(), (double)tfList.size())+",扣款状态为(0:失败,1:成功):"+(flag ? 1:0));
			if(flag) {
				totalOperateMoney = CommonUtil.add(totalOperateMoney, CommonUtil.mul(ccp.getCustomMoneyMax(), (double)tfList.size()));
				projectPrice.setBalanceAmount(CommonUtil.sub(projectPrice.getBalanceAmount(), CommonUtil.mul(ccp.getCustomMoneyMax(), (double)tfList.size())));
			}
		}
		logger.info("任务扣款后的项目信息:"+projectPrice.toString());
		logger.info("记录任务流水日志");
		//保存流水记录
		collectClassPriceDao.saveTaskFlows(taskFlows);
		//记录操作项目金额日志
		saveProjectPriceLog(projectPrice, flag ? 1:0, taskId, originalBalanceAmount, totalOperateMoney, ApplyType.DEBIT.getCode());
		if(flag) {
			collectClassPriceDao.updateProjectPrice(projectPrice);
		}
		logger.info("进入customDebit方法,客户扣款--service--end");
		return message;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月21日
	 * @description 记录历史日志
	 * @param projectPrice
	 * @param status
	 * @param taskId
	 * @param originalBalanceAmount
	 * @param operateMoney
	 * @param operateType
	 * @throws Exception
	 */
	public void saveProjectPriceLog(ProjectPrice projectPrice,int status,String taskId,double originalBalanceAmount,double operateMoney,int operateType) throws Exception {
		logger.info("插入项目金额历史记录ownerId="+projectPrice.getOwnerId()+",status="+status+",taskId="+taskId+",originalBalanceAmount="+originalBalanceAmount+",operateMoney="+operateMoney+",operateType="+operateType);
		ProjectPriceLog projectPriceLog = new ProjectPriceLog();
		projectPriceLog.setCustomId(projectPrice.getCustomId());
		projectPriceLog.setOwnerId(projectPrice.getOwnerId());
		projectPriceLog.setAdvanceAmount(projectPrice.getAdvanceAmount());
		projectPriceLog.setBalanceAmount(originalBalanceAmount);
		projectPriceLog.setThresholdAmount(projectPrice.getThresholdAmount());
		projectPriceLog.setOperateId(taskId);
		projectPriceLog.setOperateAmount(operateMoney);
		projectPriceLog.setOperateType(operateType);
		projectPriceLog.setStatus(status);
		collectClassPriceDao.saveProjectPriceLog(projectPriceLog);
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 验证余额是否足够可以继续扣款
	 * @param projectPrice
	 * @return true:可以扣款,false:暂停项目,不可以继续扣款
	 * @throws Exception 
	 */
	public boolean validateBalanceAmount(ProjectPrice projectPrice) throws Exception {
		if(projectPrice == null) {
			throw new BusinessException(BusinessCode.PROJ_INFO_ERROR+"", PropertiesConfig.getProperty(BusinessCode.PROJ_INFO_ERROR+""));//项目信息有误
		}
		double balanceAmount = projectPrice.getBalanceAmount();//总余额(包括垫付金额)
		double thresholdAmount = projectPrice.getThresholdAmount();//阀值
		boolean flag = false;
		if(CommonUtil.compare(balanceAmount, CommonUtil.mul(thresholdAmount, 2d)) == 1) {//金额充足[总余额>2倍阈值],项目继续采集
			logger.info("金额充足[总余额>2倍阈值],项目继续采集");
			flag = true;
		}else {
			String type = "";
			String warnInfo = "";
			String messTitle = "";
			if(CommonUtil.compare(balanceAmount, CommonUtil.mul(thresholdAmount, 2d)) <= 0 && CommonUtil.compare(balanceAmount, thresholdAmount)  == 1) {//金额到达预警值[1倍阈值<余额+垫付值≤2倍阈值 ],(邮件提醒-->给项目负责人发邮件提醒),项目继续采集
				logger.info("金额到达预警值[1倍阈值<余额+垫付值≤2倍阈值 ],项目继续采集");
				flag = true;
				type = "1";
				messTitle = PropertiesConfig.getProperty("greenMessTitle");
				warnInfo = PropertiesConfig.getProperty("greenWarnInfo");
			}else if(CommonUtil.compare(balanceAmount, thresholdAmount) <= 0 && balanceAmount > 0) {//金额到达阈值[0<余额+垫付值≤阈值],(邮件提醒-->给项目负责人/领导/客户发邮件提醒),项目继续采集或者项目手工暂停，采集暂停/已认领或采集任务走正常流程
				logger.info("金额到达阈值[0<余额+垫付值≤阈值],项目继续采集");
				flag = true;
				type = "2";
				messTitle = PropertiesConfig.getProperty("blueMessTitle");
				warnInfo = PropertiesConfig.getProperty("blueWarnInfo");
			}else if(balanceAmount <= 0) {//金额不足[余额+垫付值≤0],(邮件提醒-->给项目负责人/领导/客户发邮件提醒),项目自动暂停,采集暂停/已认领或采集任务走正常流程
				logger.info("金额不足[余额+垫付值≤0],项目暂停");
				flag = true;
				type = "2";
				messTitle = PropertiesConfig.getProperty("redMessTitle");
				warnInfo = PropertiesConfig.getProperty("redWarnInfo");
				boolean isSuccess = HttpRequestUtil.updateProjects(update_project_status_url, "&customId="+projectPrice.getCustomId()+"&ownerId="+projectPrice.getOwnerId()+"&status=0");
				if(isSuccess) {
					warnInfo+="暂停项目成功！";
					logger.info("暂停项目成功！参数为:"+"&customId="+projectPrice.getCustomId()+"&ownerId="+projectPrice.getOwnerId()+"&status=0");
				}else{
					warnInfo+="暂停项目失败！";
					logger.error("暂停项目失败！参数为:"+"&customId="+projectPrice.getCustomId()+"&ownerId="+projectPrice.getOwnerId()+"&status=0");
				}
				
			}
			logger.info("发送提醒邮件信息......");
			startSendMailThread(projectPrice, type, warnInfo,messTitle);

		} 
		return flag;
		
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 启动发送邮件线程
	 * @param projectPrice
	 * @param type
	 * @throws Exception
	 */
	public void startSendMailThread(ProjectPrice projectPrice,String type,String mess,String messTitle)  throws Exception {
		new Thread(new SendMailThread(projectPrice,type,mess,messTitle)).start();
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 获取项目相关信息并发送邮件
	 * @param projectPrice
	 * @throws Exception
	 */
	@Override
	public void sendMail(ProjectPrice projectPrice,String type,String mess,String messTitle) throws Exception {
		//获取项目信息
		Object projectInfo = commonComponent.getProjectInfo(projectPrice.getOwnerId(), null, get_projects_url+"&ownerId="+projectPrice.getOwnerId());
		if(projectInfo == null) {
			logger.error("根据项目ID="+projectPrice.getOwnerId()+"未获取到项目信息");
		}
		
		String projectName = "";
		Map<String,Object>  projectLeader =  new HashMap<String,Object>();
		Map<String,Object>  custom =  new HashMap<String,Object>();
		if(projectInfo != null) {
			Map project = (Map)projectInfo;
			projectName = (String)project.get("projectName");
			//项目负责人Id
			String projectLeaderId = (String)project.get("projectLeaderId");
			//项目负责人
			Object projectLeaderUser = commonComponent.getUser(projectLeaderId, null, get_project_leader_url);
			if(projectLeaderUser != null) {
				projectLeader = (Map<String,Object>)projectLeaderUser;
			}
			String customId = (String)project.get("customId");
			//获取客户Map
			Object customUser = commonComponent.getUser(customId, null, get_custom_url);
			if(customUser != null) {
				custom = (Map<String,Object>)customUser;
			}
		}
		logger.info("项目名称:"+projectName+",项目负责人信息:"+projectLeader+",项目客户信息:"+custom);
		// 设置接受用户
		List<String> toAddressList = new ArrayList<String>();
		if(projectLeader != null){
			String projectLeaderMail = (String)projectLeader.get("mail");
			if(projectLeaderMail != null && !"".equals(projectLeaderMail)) {
				toAddressList.add(projectLeaderMail);
			}else {
				logger.error("项目名称:"+projectName+"的项目负责人未填写邮箱,无法发送邮件给项目负责人");
			}
		}else {
			logger.error("项目名称:"+projectName+"的项目未找到相应的项目负责人,无法发送邮件给项目负责人");
		}
		
		if("2".equals(type)) {//1:只给项目负责人发邮件,2:给项目负责人/领导/客户发邮件提醒
			if(custom != null){
				String customMail = (String)custom.get("mail");
				if(customMail != null && !"".equals(customMail)) {
					toAddressList.add(customMail);
				}else {
					logger.error("项目名称:"+projectName+"的项目客户未填写邮箱,无法发送邮件给客户");
				}
			}else {
				logger.error("项目名称:"+projectName+"的项目找到相应的客户,无法发送邮件给客户");
			}
			//公司领导邮箱 待加
			List<Object> companyLeaders = commonComponent.getCompanyLeaderList("", PropertiesConfig.getProperty("companyLeaderRoleCode"), PropertiesConfig.getProperty("companyLeaderBsId"), get_company_leader_url);
			
			if(companyLeaders != null && companyLeaders.size() > 0) {
				for(Object obj : companyLeaders) {
					Map companyLeader = (Map)obj;
					String companyLeaderMail = (String)companyLeader.get("mail");
					if(companyLeaderMail != null && !"".equals(companyLeaderMail)) {
						toAddressList.add(companyLeaderMail);
					}else {
						logger.error("公司领导:"+companyLeader.get("name")+"未填写邮箱,无法发送邮件给此公司领导");
					}
				}
			}else {
				logger.error("未查询到公司领导信息.............");
			}
		}
		if(toAddressList.size() == 0){
			logger.error("项目名称:"+projectName+"的项目未找到正确的收件人（1,未找到相应的项目负责人和客户以及公司领导;2,项目负责人和客户以及公司领导都未填写邮箱）");
			throw new BusinessException(BusinessCode.NO_ADDRESSEE+"", PropertiesConfig.getProperty(BusinessCode.NO_ADDRESSEE+""));//无收件人
		}else {
			logger.info("收件人为:"+toAddressList.toString());
		}
		//获取邮件对象
		MailInfo mailinfo = getMailInfo();
		//收件人
		String[] toAddress = (String[])toAddressList.toArray(new String[toAddressList.size()]);
		mailinfo.setToAddress(toAddress);
		// 设置附件
		//	String[] attach = { "F:\\login.properties" };
		//	mailinfo.setAttachFileNames(attach);
//		mailinfo.setSubject(PropertiesConfig.getProperty("sendMailTitle"));
		mailinfo.setSubject(messTitle);
		StringBuffer sb = new StringBuffer();
		sb.append("项目名称为:"+projectName);
		sb.append(",客户名称为:"+custom.get("name"));
		sb.append(","+mess+",当前项目的总余额为:"+projectPrice.getBalanceAmount()+",垫付金额为:"+projectPrice.getAdvanceAmount());
		mailinfo.setContent(sb.toString());// 网页内容
		if (sendMail.sendAttach(mailinfo))
			logger.info("邮件发送成功,发送时间:"+new Date());
		else
			logger.error("邮件发送失败,发送时间:"+new Date());
	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 获取发送邮件内容对象
	 * @return
	 * @throws Exception
	 */
	public MailInfo getMailInfo ()  throws Exception {
		MailInfo mailinfo = new MailInfo();
		// --设置邮件服务器开始
		mailinfo.setMailServerHost(PropertiesConfig.getProperty("mailServerHost"));
		mailinfo.setMailServerPort(PropertiesConfig.getProperty("mailServerPort"));
		mailinfo.setValidate(true);
		mailinfo.setUserName(PropertiesConfig.getProperty("mailUserName"));
		mailinfo.setPassword(PropertiesConfig.getProperty("mailPassword"));
		// --设置邮件服务器结束
		mailinfo.setFromAddress(PropertiesConfig.getProperty("mailUserName"));// 邮件发送者的地址
		return mailinfo;
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
	@Value("${" + PropConstants.UPDATE_PROJECT_STATUS_URL + "}")
	private String update_project_status_url;
	@Value("${" + PropConstants.GET_COMPANY_LEADER_URL + "}")
	private String get_company_leader_url;
	
	
}

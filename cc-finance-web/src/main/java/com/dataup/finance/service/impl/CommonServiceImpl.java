package com.dataup.finance.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dataup.finance.componet.CommonCacheComponent;
import com.dataup.finance.service.CommonService;
import com.dataup.finance.util.HttpRequestUtil;
import com.dataup.finance.util.PropConstants;
import com.dataup.finance.util.PropertiesConfig;
@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	private Logger logger = LogManager.getLogger(this.getClass());
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
	@Autowired
	private CommonCacheComponent commonCacheComponent;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询客户信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getCustomList(String userName) throws Exception {
		logger.info("查询客户信息,条件:userName="+userName);
		List<Object> objectList = commonCacheComponent.getCustomList(PropertiesConfig.getProperty("customUsertype",""));
		if(objectList == null) {
			String param = "userName="+userName;
			objectList = HttpRequestUtil.getUserList(get_custom_url, param);
		}
		return objectList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询审核人信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getAuditList(String userName) throws Exception {
		logger.info("查询审核人信息,条件:userName="+userName);
		List<Object> objectList = commonCacheComponent.getUserList(PropertiesConfig.getProperty("auditRoleCode",""), PropertiesConfig.getProperty("auditBsId",""));
		if(objectList == null) {
			String param = "userName="+userName;
			objectList = HttpRequestUtil.getUserList(get_audit_url, param);
		}
		return objectList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询申请人信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getApplyList(String userName) throws Exception {
		logger.info("查询申请人信息,条件:userName="+userName);
		List<Object> objectList = commonCacheComponent.getUserList(PropertiesConfig.getProperty("applyRoleCode",""), PropertiesConfig.getProperty("applyBsId",""));
		if(objectList == null) {
			String param = "userName="+userName;
			objectList = HttpRequestUtil.getUserList(get_apply_url, param);
		}
		return objectList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询项目负责人信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getProjectLeaderList(String userName) throws Exception {
		logger.info("查询项目负责人信息,条件:userName="+userName);
		List<Object> objectList = commonCacheComponent.getUserList(PropertiesConfig.getProperty("projectLeaderRoleCode",""), PropertiesConfig.getProperty("projectLeaderBsId",""));
		if(objectList == null) {
			String param = "userName="+userName;
			objectList = HttpRequestUtil.getUserList(get_project_leader_url, param);
		}
		return objectList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询项目信息
	 * @param custom
	 * @param custom
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getProjectList(String customId,int status) throws Exception {
		logger.info("查询项目信息,条件:customId="+customId+",status="+status);
		List<Object> projectList = commonCacheComponent.getProjectList(customId,status);
		if(projectList == null) {
			String param = "customId="+customId+"&status="+status;
			projectList = HttpRequestUtil.getProjectList(get_projects_url, param);
		}
		return projectList;
	}

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月21日
	 * @description 查询所有项目信息
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getAllProjectList(int status) throws Exception {
		logger.info("查询所有项目信息");
		List<Object> projectList = commonCacheComponent.getAllProjectInfo(status);
		if(projectList == null) {
			String param = "status="+status;
			projectList = HttpRequestUtil.getProjectList(get_projects_url, param);
		}
		return projectList;
	}
	
	
}

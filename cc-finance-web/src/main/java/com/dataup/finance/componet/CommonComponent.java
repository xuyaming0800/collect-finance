package com.dataup.finance.componet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataup.finance.util.HttpRequestUtil;
@Component
public class CommonComponent {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private CommonCacheComponent commonCacheComponent;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 获取用户详细信息 
	 * @param id
	 * @param userMapAll
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Object getUser(String id,Map<String, Object> userMapAll,String url) throws Exception {
		Object  user = commonCacheComponent.getUser(id);
		if(user == null) {
			logger.warn("获取用户 id=["+id+"] cache is null");
			if(userMapAll == null) {
				userMapAll = HttpRequestUtil.getUsers(url, "");
			}
			if(userMapAll != null) {
				user = userMapAll.get(id);
			}
		}
		return user;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description  获取项目信息
	 * @param id
	 * @param projectInfoMapAll
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Object getProjectInfo (String id,Map<String, Object> projectInfoMapAll,String url) throws Exception {
		Object  projectInfo = commonCacheComponent.getProjectInfo(id);
		if(projectInfo == null) {
			logger.warn("获取项目 id=["+id+"] cache is null");
			if(projectInfoMapAll == null) {
				projectInfoMapAll = HttpRequestUtil.getProjects(url, "");
			}
			if(projectInfoMapAll != null) {
				projectInfo = projectInfoMapAll.get(id);
			}
		}
		return projectInfo;
	} 
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月3日
	 * @description 获取申请人IDLIst
	 * @param applyName
	 * @param roleCode
	 * @param bsId
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public List<String> getApplyIdList(String applyName,String roleCode,String bsId,String url)  throws Exception{
		List<String> idList = new ArrayList<String>();
		List<Object> userList = commonCacheComponent.getUserList(roleCode, bsId);
		if(userList == null || userList.size() == 0) {
			Map<String,Object> userMap = HttpRequestUtil.getUsers(url,applyName);
			if(userMap != null) {
				for(Entry<String,Object > entry : userMap.entrySet()) {
					Map user = (Map)entry.getValue();
					idList.add((String)user.get("id"));
				}
			}
		}else {
			for(Object obj : userList) {
				Map user = (Map)obj;
				if(((String)user.get("name")).contains(applyName)) {
					idList.add((String)user.get("id"));
				}
			}
		}
		return idList;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月3日
	 * @description 获取公司领导List
	 * @param applyName
	 * @param roleCode
	 * @param bsId
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCompanyLeaderList(String applyName,String roleCode,String bsId,String url)  throws Exception{
		List<Object> userList = commonCacheComponent.getUserList(roleCode, bsId);
		if(userList == null && userList.size() > 0) {
			userList = HttpRequestUtil.getUserList(url,"");
		}
		return userList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月12日
	 * @description 获取客户ID
	 * @param customName
	 * @param userType
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public List<String> getCustomIdList(String customName,String userType,String url)  throws Exception{
		List<String> idList = new ArrayList<String>();
		List<Object> customList = commonCacheComponent.getCustomList(userType);
		if(customList == null || customList.size() == 0) {
			Map<String,Object> customMap = HttpRequestUtil.getUsers(url,customName);
			if(customMap != null) {
				for(Entry<String,Object > entry : customMap.entrySet()) {
					Map user = (Map)entry.getValue();
					idList.add((String)user.get("id"));
				}
			}
		}else {
			for(Object obj : customList) {
				Map custom = (Map)obj;
				if(((String)custom.get("name")).contains(customName)) {
					idList.add((String)custom.get("id"));
				}
			}
		}
		return idList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年11月12日
	 * @description 获取项目ID
	 * @param projectName
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public List<String> getOwnerIdList(String projectName,String url)  throws Exception{
		List<String> idList = new ArrayList<String>();
		List<Object> ownerList = commonCacheComponent.getAllProjectInfo(0);
		if(ownerList == null || ownerList.size() == 0) {
			Map<String,Object> projectMap = HttpRequestUtil.getProjects(url,"&projectName="+projectName);
			if(projectMap != null) {
				for(Entry<String,Object > entry : projectMap.entrySet()) {
					Map project = (Map)entry.getValue();
					idList.add((String)project.get("id"));
				}
			}
		}else {
			for(Object obj : ownerList) {
				Map project = (Map)obj;
				if(((String)project.get("projectName")).contains(projectName)) {
					idList.add((String)project.get("id"));
				}
			}
		}
		return idList;
	}
}

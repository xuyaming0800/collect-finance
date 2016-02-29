package com.dataup.finance.componet;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataup.finance.constant.StringConstant;
import com.dataup.finance.util.JsonBinder;
@Component
public class CommonCacheComponent {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private RedisUtilComponent redisUtilComponent;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据项目ID获取项目
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object getProjectInfo(String id)throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		Object projectInfo=redisUtilComponent.getRedisJsonCache(StringConstant.PROJECT_INFO_CACHE_PREFIX+id, 
				Object.class, jb);
		if(projectInfo==null){
			logger.warn("projectId=["+id+"] cache is null ");
		}
		return projectInfo;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月21日
	 * @description 获取所有项目信息
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllProjectInfo(int status)throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		List<Object> projectList= null;
		if(status == 0) {
			projectList = redisUtilComponent.getRedisJsonCache(StringConstant.ALL_PROJECT_INFO_CACHE_PREFIX,
					List.class, jb);
		}else if(status == 1) {
			projectList = redisUtilComponent.getRedisJsonCache(StringConstant.ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX,
					List.class, jb);
		}
		if(projectList ==null){
			logger.warn("all project  cache is null ");
		}
		return projectList;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据客户ID获取项目List
	 * @param customId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<Object> getProjectList(String customId,int status) throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		List<Object> projectList= null;
		if(status == 0) {
			projectList=redisUtilComponent.getRedisJsonCache(StringConstant.CUSTOM_PROJECT_INFO_CACHE_PREFIX+customId, 
					List.class, jb);
		}else if(status == 1) {
			projectList=redisUtilComponent.getRedisJsonCache(StringConstant.CUSTOM_NORMAL_PROJECT_INFO_CACHE_PREFIX+customId, 
					List.class, jb);
		}
	
		if(projectList==null){
			logger.warn("customId=["+customId+"] cache is null ");
		}
		return projectList;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据角色code和系统Id查询用户
	 * @param roleCode
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Object> getUserList(String roleCode,String bsId) throws Exception {
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		List<Object> userList=redisUtilComponent.getRedisJsonCache(StringConstant.USER_LIST_ROLE_BS_CACHE_PREFIX+roleCode+"_"+bsId, 
				List.class, jb);
		if(userList == null){
			logger.warn("roleCode=["+roleCode+"],bsId=["+bsId+"] cache is null ");
		}
		return userList;
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据系统类型获取用户 
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCustomList(String userType) throws Exception {
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		List<Object> userList=redisUtilComponent.getRedisJsonCache(StringConstant.USER_LIST_TYPE_CACHE_PREFIX+userType, 
				List.class, jb);
		if(userList == null){
			logger.warn("userType=["+userType+"] cache is null ");
		}
		return userList;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月20日
	 * @description 根据用户ID查询用户 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object getUser(String id)  throws Exception {
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		Object user =redisUtilComponent.getRedisJsonCache(StringConstant.USER_INFO_CACHE_PREFIX+id, 
				Object.class, jb);
		if(user == null){
			logger.warn("id=["+id+"] cache is null ");
		}
		return user;
	}
	

}

package com.dataup.finance.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.dataup.finance.bean.ArgsEntity;
import com.dataup.finance.constant.StringConstant;
import com.dataup.finance.entity.ResultEntity;
import com.dataup.finance.entity.TaskClazzMenuEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpRequestUtil {
	 private static final Logger logger = Logger.getLogger(HttpRequestUtil.class);

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description  获取所有客户信息或者获取项目负责人信息
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getUsers(String url,String userName)  throws Exception{
		logger.info("进入getUsers方法获取用户,url:"+url+",userName:"+userName+"-----start");
		try {
			String json = HttpClientUtil.get(url+"&userName="+userName, null);
			logger.info("获取内容："+json);
			if(StringUtils.isBlank(json)) {
				return null;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
			List<Object> userList = (List<Object>) resultEntity.getInfo();
			Map userMaps = null;
			if(userList != null && userList.size() > 0) {
				userMaps =new HashMap();
				for(Object obj : userList) {
					Map userMap = (Map)obj;
					userMaps.put(userMap.get("id"), userMap);
				}
			}
			logger.info("进入getUsers方法获取用户,url:"+url+"-----end");
			return userMaps;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月22日
	 * @description 获取项目信息
	 * @param url
	 * @param projectName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getProjects(String url,String param)  throws Exception{
		logger.info("进入getProjects方法获取项目,url:"+url+",param:"+param+"-----start");
		try {
			String json = HttpClientUtil.get(url+param, null);
			logger.info("获取内容："+json);
			if(StringUtils.isBlank(json)) {
				return null;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
			Map infoMap = (Map)resultEntity.getInfo();
			List<Object> projectList = (List<Object>)infoMap.get("objectList");
			Map projectMaps = null;
			if(projectList != null && projectList.size() > 0) {
				projectMaps =new HashMap();
				for(Object obj : projectList) {
					Map userMap = (Map)obj;
					projectMaps.put(userMap.get("id"), userMap);
				}
			}
			logger.info("进入getProjects方法获取项目,url:"+url+"-----end");
			return projectMaps;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 获取用户List（客户信息,审核人,申请人,项目负责人） 
	 * @param url
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getUserList(String url,String param)  throws Exception{
		logger.info("进入getUserList方法获取用户,url:"+url+",param:"+param+"-----start");
		try {
			String json = HttpClientUtil.get(url+"&"+param, null);
			logger.info("获取内容："+json);
			if(StringUtils.isBlank(json)) {
				return null;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
			List<Object> userList = (List<Object>) resultEntity.getInfo();
			logger.info("进入getUserList方法获取用户,url:"+url+"-----end");
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 获取项目信息 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getProjectList(String url,String param)  throws Exception{
		logger.info("进入getProjectList方法获取项目,url:"+url+",param:"+param+"-----start");
		try {
			String json = HttpClientUtil.get(url+"&"+param, null);
			logger.info("获取内容："+json);
			if(StringUtils.isBlank(json)) {
				return null;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
			Map infoMap = (Map)resultEntity.getInfo();
			List<Object> projectList = (List<Object>)infoMap.get("objectList");
			logger.info("进入getProjects方法获取项目,url:"+url+"-----end");
			return projectList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月22日
	 * @description 获取项目的品类树 
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static List<TaskClazzMenuEntity> getCollectClazzTree(String url ,String param)  throws Exception{
		logger.info("进入getCollectClazzTree方法获取项目的品类树,url:"+url+",param:"+param+"-----start");
		try {
			String json = HttpClientUtil.get(url+"&"+param, null);
			logger.info("获取内容："+json);
			if(StringUtils.isBlank(json)) {
				return null;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
			
			JsonBinder jb=JsonBinder.buildNormalBinder(false);
			List<TaskClazzMenuEntity> list=jb.fromJson(jb.toJson(resultEntity.getInfo()), List.class, jb.getCollectionType(List.class, TaskClazzMenuEntity.class));
			logger.info("进入getCollectClazzTree方法获取项目的品类树,url:"+url+"-----end");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月13日
	 * @description 更改项目状态
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static boolean updateProjects(String url,String param)  throws Exception{
		logger.info("进入updateProjects方法获取项目,url:"+url+",param:"+param+"-----start");
		try {
			String json = HttpClientUtil.get(url+param, null);
			logger.info("获取内容："+json);
			if(StringUtils.isBlank(json)) {
				return false;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
			logger.info("进入updateProjects方法获取项目,url:"+url+"-----end");
			return resultEntity.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月9日
	 * @description  获取所有客户信息或者获取项目负责人信息
	 * @return
	 * @throws Exception
	 */
//	public static ResultEntity  getStatiscs(String url,String param)  throws Exception{
//		logger.info("进入getStatiscs方法获取用户,url:"+url+",param:"+param+"-----start");
//		try {
//			String json = HttpClientUtil.get(url+"&"+param, null);
//			logger.info("获取内容："+json);
//			if(StringUtils.isBlank(json)) {
//				return null;
//			}
//			ObjectMapper objectMapper = new ObjectMapper();
//			ResultEntity resultEntity = objectMapper.readValue(json,ResultEntity.class);
//			logger.info("进入getStatiscs方法获取用户,url:"+url+"-----end");
//			return resultEntity;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
	
	public static ResultEntity getStatiscs(String url, ArgsEntity argsEntity)
			throws Exception {
		logger.info("进入查询列表方法getStatiscs:" + argsEntity);
		logger.info(argsEntity);
		logger.info(url);
		Map<String, String> entityMap = new HashMap<String, String>();
		packMap(argsEntity, entityMap);
		logger.info("参数对象::" + entityMap);
		String json = HttpClientUtil.post(url, entityMap, "utf-8");
		logger.info("远程接口:json==" + json);
		if (StringUtils.isBlank(json))
			return null;
		ObjectMapper objectMapper = new ObjectMapper();
		ResultEntity resultEntity = objectMapper.readValue(json,
				ResultEntity.class);
		logger.info("封装对象:resultEntity==" + resultEntity);
		return resultEntity;
	}
	
	/**
	 * @Description: 封装查询条件
	 * @author xusheng.liu
	 * @date 2015年10月19日 下午6:46:23
	 * @version V1.0
	 * @param argsEntity
	 * @param entityMap
	 */
	private static void packMap(ArgsEntity argsEntity, Map<String, String> entityMap) {
		Integer funType = argsEntity.getFunType();
		if (argsEntity.getClassisId() != null
				&& !"".equals(argsEntity.getClassisId())) {// 分类(明细中)
			entityMap.put("classis", argsEntity.getClassisId());
		}
		if (StringUtils.isNotBlank(argsEntity.getClassis())) { // 分类
			entityMap.put("classis", argsEntity.getClassis());
		}
		if (StringUtils.isNotBlank(argsEntity.getZone())) { // 地区
			entityMap.put("zone", argsEntity.getZone());
		}
		if (StringUtils.isNotBlank(argsEntity.getSystemId())) { // 系统Id
			entityMap.put("system_type", argsEntity.getSystemId());
		}
		if (StringUtils.isNotBlank(argsEntity.getStatus())) { // 审核状态
			entityMap.put("status", argsEntity.getStatus());
		}
		if (StringUtils.isNotBlank(argsEntity.getAuditDate())) { // 审核状态
			entityMap.put("auditDate", argsEntity.getAuditDate()); // 审核时间
		}
		if (argsEntity.getPageNo() != null && argsEntity.getPageNo() > -1) { // 页码
			entityMap.put("start", argsEntity.getPageNo() + "");
		}
		if (argsEntity.getPageSize() != null && argsEntity.getPageSize() > 0) { // 页距
			entityMap.put("limit", argsEntity.getPageSize() + "");
		}
		if (StringConstant.QUERY_TYPE_LIST.equals(funType)) {//区域分类列表
			entityMap.put("sortName", "start_time_");
		} else if(StringConstant.QUERY_TYPE_DETAIL.equals(funType)){//明细
			entityMap.put("sortName", "submit_time");
		} else if(StringConstant.QUERY_TYPE_PAY_LIST.equals(funType)){//费用支出
			entityMap.put("queryType", argsEntity.getPayCondition());
			entityMap.put("sortName", "auditdate");
		} else if(StringConstant.QUERY_TYPE_RECHARGE_LIST.equals(funType)){//充值记录
			entityMap.put("sortName", "AUDIT_TIME");
		}else if(StringConstant.QUERY_TYPE_BILL_LIST.equals(funType)){//账单详情
			entityMap.put("sortName", "_DATE");
		}
		entityMap.put("sortOrder", "desc"); // 排序规则
	}

}

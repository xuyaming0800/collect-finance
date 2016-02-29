package com.dataup.finance.service;

import java.util.List;

public interface CommonService {
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询客户信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCustomList(String userName) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询审核人信息
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAuditList(String userName) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询申请人信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getApplyList(String userName) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询项目负责人信息 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getProjectLeaderList(String userName) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月19日
	 * @description 查询项目信息
	 * @param custom
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<Object> getProjectList(String custom,int status) throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年10月21日
	 * @description 查询所有项目信息
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllProjectList(int status) throws Exception;
	

}

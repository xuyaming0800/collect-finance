package com.dataup.finance.openapi.service;

import java.util.List;

import com.dataup.finance.bean.CollectClassPrice;
import com.dataup.finance.bean.ProjectPrice;

public interface ProjectService {

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
	public CollectClassPrice queryCollectClassPrice(String ownerId, String collectClassParentId, String collectClassId)  throws Exception;
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
	public List<ProjectPrice> queryProjectPrices(String ownerId, String customId)  throws Exception;
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description  发送邮件
	 * @param projectPrice
	 * @param type 类型 1:只给项目负责人发邮件,2:给项目负责人/领导/客户发邮件提醒
	 * @throws Exception
	 */
	public void sendMail(ProjectPrice projectPrice,String type,String mess,String messTitle) throws Exception;
	
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月25日
	 * @description 客户扣款
	 * @param content 需要扣款的任务信息
	 * @return
	 * @throws Exception
	 */
	public String customDebit(String content)  throws Exception;
}

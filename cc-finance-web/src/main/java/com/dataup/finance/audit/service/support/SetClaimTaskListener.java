package com.dataup.finance.audit.service.support;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dataup.finance.base.spring.ApplicationContextUtils;
import com.dataup.finance.util.PropertiesConfig;

/**
 * @Title: setClaimExecutionListener.java
 * @Package com.autonavi.audit.service.support
 * @Description: 认领任务  未使用
 * @author 刘旭升
 * @date 2015年8月25日 上午9:44:33
 * @version V1.0
 */
public class SetClaimTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6716851714615103181L;
	private Logger logger = LogManager.getLogger(getClass());
	// 从Spring上下文中获取实例
	private TaskService taskService = ApplicationContextUtils
			.getApplicationContext().getBean(TaskService.class);
	private JedisPool jedisPool = ApplicationContextUtils
			.getApplicationContext().getBean(JedisPool.class);

	@Override
	public void notify(DelegateTask delegateTask) {
		Object obj = delegateTask.getVariable("bsType");
		Integer sysType = (Integer) obj;
		if(!new Integer(1).equals(sysType)){//兼容广告拍拍增加对项目的限制
			logger.info("进入初审设置认领人方法。。。。");
			String nameString = delegateTask.getName();
			logger.info("当前结点是："+nameString);
			if ("初审".equals(nameString)) { // 设置初审人
				taskService.setAssignee(delegateTask.getId(),
						getNameForAudit("audit_firstaudit_claim_name"));
				logger.info("初审认领人是："+getNameForAudit("audit_firstaudit_claim_name"));
			} else if("抽检".equals(nameString)){ // 设置二审人员
				taskService.setAssignee(delegateTask.getId(),
						getNameForAudit("audit_sampling_claim_name"));
				logger.info("抽检认领人是："+getNameForAudit("audit_sampling_claim_name"));
			} else if("申诉".equals(nameString)){
				taskService.setAssignee(delegateTask.getId(),
						getNameForAudit("audit_appeal_claim_name"));
				logger.info("申诉认领人是："+getNameForAudit("audit_appeal_claim_name"));
			}
		}
	}

	@SuppressWarnings("deprecation")
	private String getNameForAudit(String key) {
		logger.info("进入获取认领人的方法:key是"+key);
		Jedis jedis = jedisPool.getResource();
		logger.info("获取到的jedis是:"+jedis);
		try {
			if (!jedis.exists(key)) {// 如果不存在KEY，说明是第一次使用，添加初始量
				String initValStr = PropertiesConfig
						.getProperty(key);// 获取初始量
				jedis.set(key, initValStr);
				return initValStr;
			} else
				return jedis.get(key);
		} finally {
			logger.info("把redis放回池中...");
			jedisPool.returnResource(jedis);
		}
	}

}

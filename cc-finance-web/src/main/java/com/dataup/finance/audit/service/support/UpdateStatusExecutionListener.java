package com.dataup.finance.audit.service.support;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

import com.dataup.finance.audit.service.AuditService;
import com.dataup.finance.base.spring.ApplicationContextUtils;

/**
 * 修改状态  未使用
 * 
 * @author jia.miao
 *
 */
public class UpdateStatusExecutionListener implements ExecutionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6721997308329826218L;

	// 从Spring上下文中获取实例
	private AuditService auditService = ApplicationContextUtils
			.getApplicationContext().getBean(AuditService.class);

	private Expression status;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		if (status != null && status.getExpressionText() != null)
			auditService.updateStatusEvent(execution.getProcessInstanceId(),
					execution.getId(),
					Long.valueOf(status.getValue(execution).toString())
							.intValue());
	}

}

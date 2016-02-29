package com.dataup.finance.audit.service.support;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

import com.dataup.finance.audit.service.AuditService;
import com.dataup.finance.base.spring.ApplicationContextUtils;

/**
 * 修改状态
 * 
 * @author jia.miao
 *
 */
public class UpdateStatusTaskListener implements TaskListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8799371372266059152L;

	// 从Spring上下文中获取实例
	private AuditService auditService = ApplicationContextUtils
			.getApplicationContext().getBean(AuditService.class);

	private Expression status;

	@Override
	public void notify(DelegateTask delegateTask) {
		if (status != null && status.getExpressionText() != null)
			try {
				auditService.updateStatusEvent(
						delegateTask.getProcessInstanceId(),
						delegateTask.getExecutionId(),
						Long.valueOf(
								status.getValue(delegateTask.getExecution())
										.toString()).intValue());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

}

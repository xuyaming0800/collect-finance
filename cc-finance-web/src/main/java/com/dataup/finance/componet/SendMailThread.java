package com.dataup.finance.componet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataup.finance.base.spring.ApplicationContextUtils;
import com.dataup.finance.bean.ProjectPrice;
import com.dataup.finance.openapi.service.ProjectService;


public class SendMailThread implements Runnable {
	private Logger logger = LogManager.getLogger(this.getClass());
	private ProjectService projectService = ApplicationContextUtils.getApplicationContext().getBean(ProjectService.class);
	private ProjectPrice projectPrice;
	private String type;
	private String mess;
	private String messTitle;
	
	public SendMailThread(ProjectPrice projectPrice,String type,String mess,String messTitle) {
		this.projectPrice = projectPrice;
		this.type = type;
		this.mess = mess;
		this.messTitle =messTitle;
	}

	@Override
	public void run() {
		try {
			projectService.sendMail(projectPrice,type,mess,messTitle);
		} catch (Exception e) {
			logger.error("项目ID:"+projectPrice.getOwnerId()+"发送邮件失败");
			e.printStackTrace();
		}

	}


}

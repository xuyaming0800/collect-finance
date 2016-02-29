package com.dataup.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/index")
public class IndexController {

	
	
	
	/**
	 * 财务申请系统管理主页
	 * 
	 * @return
	 */
	@RequestMapping("/main")
	public String indexPage() {
		return "index/main";
	}
	
	/**
	 * 财务审核系统管理主页
	 * 
	 * @return
	 */
	@RequestMapping("/auditMain")
	public String auditMain() {
		return "index/auditMain";
	}
	
	
	/**
	 * 验证失败页面
	 * 
	 * @return
	 */
	@RequestMapping("/caserror")
	public String casErrorPage() {
		return "index/caserror";
	}
	
}

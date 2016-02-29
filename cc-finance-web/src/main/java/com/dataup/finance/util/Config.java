package com.dataup.finance.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

	public static String get_projects_url;
	
	public static String get_project_leader_url;
	
	public static String get_custom_url;
	
	public static String get_audit_url;
	
	@Value("${get_project_leader_url}")
	public void setGet_project_leader_url(String get_project_leader_url) {
		Config.get_project_leader_url = get_project_leader_url;
	}
	@Value("${get_custom_url}")
	public void setGet_custom_url(String get_custom_url) {
		Config.get_custom_url = get_custom_url;
	}
	
	@Value("${get_projects_url}")
	public  void setGet_projects_url(String get_projects_url) {
		Config.get_projects_url = get_projects_url;
	}
	@Value("${get_audit_url}")
	public  void setGet_audit_url(String get_audit_url) {
		Config.get_audit_url = get_audit_url;
	}
}

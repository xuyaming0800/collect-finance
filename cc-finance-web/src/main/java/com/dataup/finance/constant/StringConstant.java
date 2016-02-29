package com.dataup.finance.constant;

public class StringConstant {
	public static final String TASK_CLAZZ_CACHE_PREFIX = "TCCP_";
	public static final String TASK_CLAZZ_MENU_CACHE_PREFIX = "TCIMCP_";
	public static final String TASK_CLAZZ_PRICE_CACHE_PREFIX = "TCPCP_";
	//主动和被动暂未使用
	public static final String TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX = "TCIMCP_";
	public static final String TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX = "TCPMCP_";
	
	public static final String PROJECT_INFO_CACHE_PREFIX = "PICP_";
	public static final String ALL_PROJECT_INFO_CACHE_PREFIX = "ALL_PROJECT_INFO_CACHE_PREFIX";
	public static final String ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX = "ALL_NORMAL_PROJECT_INFO_CACHE_PREFIX";
	public static final String CUSTOM_PROJECT_INFO_CACHE_PREFIX = "CPICP_";
	public static final String CUSTOM_NORMAL_PROJECT_INFO_CACHE_PREFIX = "CNPICP_";
	
	public static final String USER_INFO_CACHE_PREFIX="BOSS_ONE_"; 
	
	public static final String USER_LIST_TYPE_CACHE_PREFIX="BOSS_TYPE_LIST_"; 
	
	public static final String USER_LIST_ROLE_BS_CACHE_PREFIX="BOSS_ROLE_BS_LIST_"; 
	
	/**
	 * 用来区分执行方法 1:分类统计和区域统计列表;	2:分类统计和区域统计明细;	3:支付统计列表; 4:充值统计列表
	 */
	public static Integer QUERY_TYPE_LIST = 1;
	/**
	 * 用来区分执行方法 1:分类统计和区域统计列表;	2:分类统计和区域统计明细;	3:支付统计列表; 4:充值统计列表
	 */
	public static Integer QUERY_TYPE_DETAIL = 2;
	/**
	 * 用来区分执行方法 1:分类统计和区域统计列表;	2:分类统计和区域统计明细;	3:支付统计列表; 4:充值统计列表
	 */
	public static Integer QUERY_TYPE_PAY_LIST = 3;
	/**
	 * 用来区分执行方法 1:分类统计和区域统计列表;	2:分类统计和区域统计明细;	3:支付统计列表; 4:充值统计列表
	 */
	public static Integer QUERY_TYPE_RECHARGE_LIST = 4;
	/**
	 * 账单详情
	 */
	public static Integer QUERY_TYPE_BILL_LIST = 5;
}

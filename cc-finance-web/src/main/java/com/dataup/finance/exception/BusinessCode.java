package com.dataup.finance.exception;

public class BusinessCode {
	
	/**
	 * 参数值不正确
	 */
	public static long PARAM_VALUE_ERROR = 100078L;
	
	/**
	 * 不能有两个同名参数
	 */
	public static long TWO_PARAMS_WITH_SAME_NAME = 300001L;
	
	/**
	 * 缺少必填参数
	 */
	public static long MISS_REQUIRED_PARAMS = 300004L;
	
	/**
	 * 加密错误
	 */
	public static long ENCRYPT_ERROR = 300005L;
	/**
	 * 解密错误
	 */
	public static long DECRYPT_ERROR = 300006L;
	
	/**
	 * 系统异常
	 */
	public static long SYS_ERROR_INFO = 400001L;
	
	/**
	 * 保存成功
	 */
	public static long SAVE_SUCC = 500001L;
	/**
	 * 查询成功
	 */
	public static long QUERY_SUCC = 500002L;
	
	/**
	 * 提交申请成功
	 */
	public static long SUBMIT_APPLY_SUCC = 500003L;
	
	/**
	 * 提交申请失败
	 */
	public static long SUBMIT_APPLY_FAIL = 500004L;
	
	/**
	 * 项目价格信息未初始化
	 */
	public static long PROJECT_PRICE_NO_INIT = 500005L;
	
	/**
	 * 记录不存在
	 */
	public static long APPLY_RECORD_NO_EXIST = 500006L;
	
	/**
	 * 当前任务taskId不存在
	 */
	public static long TASK_ID_NO_EXIST = 500007L;
	
	/**
	 * 审核成功
	 */
	public static long AUDIT_SUCC = 500008L;
	
	/**
	 * 审核失败
	 */
	public static long AUDIT_FAIL = 500009L;
	/**
	 * 退款金额不能大于有效余额（余额-垫付）
	 */
	public static long REFUND_MORE_BIG = 500010L;
	
	/**
	 * 无收件人
	 */
	public static long NO_ADDRESSEE = 500011L;
	
	/**
	 * 客户扣款成功
	 */
	public static long CUSTOM_DEBIT_SUCC = 500012L;
	/**
	 * 无任务可以扣款
	 */
	public static long NO_TASK_FLOW = 500013L;
	
	/**
	 * 项目信息有误
	 */
	public static long PROJ_INFO_ERROR = 500014L;
	/**
	 * 采集品类价格不存在
	 */
	public static long NO_COLLECT_CLASS_PRICE = 500015L;
	/**
	 * 扣款失败
	 */
	public static long CUSTOM_DEBIT_FAIL = 500016L;
	/**
	 * 已审核
	 */
	public static long IS_AUDIT = 500017L;
	/**
	 * 未审核
	 */
	public static long NO_AUDIT = 500018L;
	
	/**
	 * 此任务已被支付,不能重复支付
	 */
	public static long TASK_PAY = 500019L;
	
	
	/**
	 * redis异常
	 */
	public static long REDIS_EXCEPTION = 590001L;
	
	/**
	 * 被redis锁定
	 */
	public static long LOCK_BY＿REDIS = 590002L;
	
	/**
	 * 缓存中不存在
	 */
	public static long NO_EXIST_IN＿REDIS = 590003L;
	
	/**
	 * 查询品类价格不存在
	 */
	public static long NO_EXIST_COLLECT_CLASS_PRIECT = 510001L;

	
	
	
}

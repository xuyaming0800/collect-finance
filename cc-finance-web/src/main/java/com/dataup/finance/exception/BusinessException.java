package com.dataup.finance.exception;


/**
 * 客户中心财务-异常处理
 * 
 * @author wenpeng.jin
 * 
 */
public class BusinessException extends Exception {
	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8265750558888349801L;

	// error code
		private String errorCode = "-1";

		// error message
		private String errorMessage = null;


		public BusinessException(String message) {
			super(message);
		}
		public BusinessException(String message, Throwable cause) {
			super(message,cause);
		}

		/**
		 * 构造函数说明： 
		 * @param errorCode
		 * @param errorMessage
		 * @param sqlExpEnum
		 */
		public BusinessException(String errorCode, String errorMessage) {
			super(errorMessage);
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}




		public BusinessException(String errorCode, String message, Throwable cause) {
			super(message, cause);
			this.setErrorCode(errorCode);
			this.setErrorMessage(message);
		}


		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorCode() {
			return this.errorCode;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage() {
			return this.errorMessage;
		}

}

package com.dataup.finance.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.exception.BusinessException;




public class RequestParamsHandler {
	
	private static Logger log = LogManager.getLogger(RequestParamsHandler.class);

	private static String decryptKey = null;// 加密解密用的密匙

	public static String getDecryptKey() {
		if (decryptKey == null) {
			decryptKey =PropertiesConfig.getProperty("openapi_decrypt_key");
		}
		return decryptKey;
	}

	
	public static RequestParams handleRequest(HttpServletRequest request) throws BusinessException {
		//获取参数
		RequestParams params = getRequestParams(request);
		//解密参数
		params = decrypt(params);
		//添加IP地址
		params.setValue("remoteIP", getIpAddr(request));
		params.setValue("userAgent", request.getHeader("user-agent") == null ? "" : request.getHeader("user-agent"));
		
		log.info("传入参数: " + params.getAll().toString());
		
		//打印参数
		return params;
	}

	// 获取全部参数
	public static RequestParams getRequestParams(HttpServletRequest request) throws BusinessException {
		RequestParams params = new RequestParams();
		if(request instanceof MultipartHttpServletRequest){
		
			
/*			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);*/
			
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
				Enumeration<String> paramNames = multipartRequest.getParameterNames();
				while (paramNames.hasMoreElements()) {
					String paramName =  paramNames.nextElement();
					String[] paramValues = request.getParameterValues(paramName);
					if (paramValues.length == 1) {
						String paramValue = paramValues[0];
						params.setValue(paramName, paramValue);
					} else {
						throw new BusinessException(BusinessCode.TWO_PARAMS_WITH_SAME_NAME+"" ,"不能有两个同名参数!");
					}
				}
				
				try {
					Iterator<String> fileNames = multipartRequest.getFileNames();
					while(fileNames.hasNext()) {
						String fileName = fileNames.next();
						MultipartFile multipartFile = multipartRequest.getFile(fileName);
						params.setValue(fileName, multipartFile.getBytes());
					}
				}catch(Exception e) {
	            	log.error("参数值不正确", e.getMessage() );
	                throw new BusinessException(BusinessCode.PARAM_VALUE_ERROR+"","参数值不正确!" );
				}
			
            }catch (Exception e) {
            	log.error(e.getMessage() );
            }
		} else {
			Enumeration<String> paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName =  paramNames.nextElement();
				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					params.setValue(paramName, paramValue);
				} else {
					throw new BusinessException(BusinessCode.TWO_PARAMS_WITH_SAME_NAME+"","不能有两个同名参数!");
				}
			}
		}
		return params;
	}

	// 如果有加密标示，解密全部参数
	@SuppressWarnings("unchecked")
	public static RequestParams decrypt(RequestParams params) throws BusinessException {

		if (params.getValue("encrypt") != null) {
			if ("AES".equals(params.getValue("encrypt"))) {

					//静态秘钥解密方式
					RequestParams rp = new RequestParams();
					HashMap<Object, Object> map = params.getAll();
					Iterator it = map.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry entry = (Map.Entry) it.next();
							if (entry.getKey() == null)
								continue;
							Object key = entry.getKey();
							Object value = entry.getValue();
							if (!"encrypt".equals(key) && !"serviceid".equals(key) && !"encryptback".equals(key)){
								if(value instanceof String){
									String tempValue = null;
									if((tempValue = AES.Decrypt(value.toString(), getDecryptKey()))==null){
										throw new BusinessException(BusinessCode.DECRYPT_ERROR+"" ,"解密错误!");
									}
									value = tempValue;
								}
							}
							rp.setValue(key.toString(), value);
						}
						return rp;
				//end else
			}//end encrypt AES
		}
		return params;
	}
	
	
	
	/**
	 * 
	 * @Methods Name getIpAddr
	 * @param request
	 * @return String
	 */
	private static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		if(!StringUtils.isEmpty(ip)){
			String[] ipArr = ip.split(",");
			for (int i = 0; i < ipArr.length; i++) {
				String tempIp = ipArr[i];
				if(!"unknown".equalsIgnoreCase(tempIp)){
					ip = tempIp;
					break;
				}
			}
		}
		return ip;
	}
}

package com.dataup.finance.componet;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataup.finance.bean.CollectTaskClazz;
import com.dataup.finance.constant.StringConstant;
import com.dataup.finance.entity.TaskClazzMenuEntity;
import com.dataup.finance.exception.BusinessCode;
import com.dataup.finance.util.JsonBinder;
import com.dataup.finance.util.PropertiesConfig;

@Component
public class TaskClazzCacheComponent {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private RedisUtilComponent redisUtilComponent;

	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 根据品类Id获取品类对象
	 * @param clazzId
	 * @return
	 * @throws Exception
	 */
	public CollectTaskClazz getCollectTaskClazz(Long clazzId)throws Exception{
		JsonBinder jb=JsonBinder.buildNormalBinder(false);
		CollectTaskClazz taskClazz=redisUtilComponent.getRedisJsonCache(StringConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId, 
				CollectTaskClazz.class, jb);
		if(taskClazz==null){
			logger.error(StringConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId+PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
			//throw new BusinessException(BusinessCode.NO_EXIST_IN＿REDIS+"",PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
		}
		return taskClazz;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 根据品类Id获取品类信息字符串
	 * @param clazzId
	 * @return
	 * @throws Exception
	 */
	public String getCollectTaskClazzJson(Long clazzId)throws Exception{
		String taskClazzJson=redisUtilComponent.getRedisStringCache(StringConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId);
		if(taskClazzJson==null){
			logger.error(StringConstant.TASK_CLAZZ_CACHE_PREFIX+clazzId+PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
			//throw new BusinessException(BusinessCode.NO_EXIST_IN＿REDIS+"",PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
		}
		return taskClazzJson;
	}
	/**
	 * 
	 * @author wenpeng.jin
	 * @date 2015年9月17日
	 * @description 根据ownerId获取大品类信息
	 * @param ownerId
	 * @return
	 * @throws Exception
	 */
//	public List<TaskClazzMenuEntity> getCollectTaskClazzMenuList(String ownerId) throws Exception {
//		JsonBinder jb=JsonBinder.buildNormalBinder(false);
//		ObjectMapper mapper = new ObjectMapper();
//		List<TaskClazzMenuEntity> menuList =redisUtilComponent.getRedisJsonCache(StringConstant.TASK_CLAZZ_MENU_CACHE_PREFIX+ownerId, 
//				List.class, jb,mapper.getTypeFactory().constructParametricType(List.class, TaskClazzMenuEntity.class));
//		if(menuList==null){
//			logger.error(StringConstant.TASK_CLAZZ_MENU_CACHE_PREFIX+ownerId+PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
//			//throw new BusinessException(BusinessCode.NO_EXIST_IN＿REDIS+"",PropertiesConfig.getProperty(BusinessCode.NO_EXIST_IN＿REDIS+""));
//		}
//		return menuList;
//	}
	
	public List<TaskClazzMenuEntity> getCollectClazzTree(String ownerId,Boolean isPassive)throws Exception{
		String taskClazzJson=this.getCollectClazzTreeJson(ownerId, isPassive);
		if(taskClazzJson!=null&&!taskClazzJson.equals("")){
			JsonBinder jb=JsonBinder.buildNormalBinder(false);
			List<TaskClazzMenuEntity> list=jb.fromJson(taskClazzJson, List.class, jb.getCollectionType(List.class, TaskClazzMenuEntity.class));
			return list;
		}else{
			return new ArrayList<TaskClazzMenuEntity>();
		}
		
	}
	
	public String getCollectClazzTreeJson(String ownerId,Boolean isPassive)throws Exception{
		String taskClazzJson="";
		if(isPassive==null||!isPassive){
			taskClazzJson=redisUtilComponent.getRedisStringCache(StringConstant.TASK_CLAZZ_INITIATIVE_MENU_CACHE_PREFIX+ownerId);
		}else{
			taskClazzJson=redisUtilComponent.getRedisStringCache(StringConstant.TASK_CLAZZ_PASSIVE_MENU_CACHE_PREFIX+ownerId);
		}
		if(taskClazzJson==null){
				logger.error("ownerId=["+ownerId+"],isPassive=["+isPassive+"] cache is null");
				return "";
		}
		return taskClazzJson;
		
	}

}

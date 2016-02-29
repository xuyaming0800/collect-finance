package com.dataup.finance.audit.dao;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.ibatis.annotations.Param;

import com.dataup.finance.base.mybatis.annotation.MyBatisRepository;
import com.dataup.finance.entity.AuditProchook;


@MyBatisRepository
public interface AuditProcHookDao {

	List<AuditProchook> selectExpression(List<ProcessDefinition> pds);

	List<AuditProchook> findAll();

	void insert(
			@Param(value = "processDefinitionId") String processDefinitionId,
			@Param(value = "expression") String expression,
			@Param(value = "userName") String userName);

	void delete(@Param(value = "processDefinitionId") String processDefinitionId);

	void updateExpression(
			@Param(value = "processDefinitionId") String processDefinitionId,
			@Param(value = "expression") String expression);

}

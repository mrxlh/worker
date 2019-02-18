package com.collmall.service.impl;


import com.alibaba.fastjson.JSON;
import com.collmall.exception.BusinessException;
import com.collmall.mapper.ScheduleTablefixMapper;
import com.collmall.model.ScheduleTablefix;
import com.collmall.model.ScheduleTask;
import com.collmall.model.TaskResponse;
import com.collmall.query.WorkerQuery;
import com.collmall.result.Result;
import com.collmall.service.ScheduleTaskService;
import com.collmall.service.WorkerService;
import com.collmall.util.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;


@Service
public class WorkerServiceImpl implements WorkerService {

	private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

	@Autowired
	private ScheduleTaskService scheduleTaskService;

	@Autowired
	private ScheduleTablefixMapper scheduleTablefixMapper;


	@Override
	public Result<List> queryList(WorkerQuery query) {
		try {
			int fix = this.getTableFix(query.getTaskType());
			query.setTableFix(fix+"");
			List<ScheduleTask> queryList = scheduleTaskService.queryList(query);
			int count = scheduleTaskService.count(query);
			if (queryList == null || queryList.size()< 0) {
				return new Result<List>(new ArrayList<>());
			}
			return new Result<List>(count, query.getPageSize(), query.getPage(),queryList);
		}catch (Exception e) {
			throw new BusinessException("查询worker时报错");
		}

	}

	private int getTableFix(String taskType) throws SQLException {
		Map<String, Object> param = new HashMap<>();
		param.put("taskType", taskType);
		ScheduleTablefix queryOne = scheduleTablefixMapper.queryOne(param);
		if (queryOne == null) {
			throw new BusinessException("找不到任务设置：" + taskType);
		}
		return queryOne.getTableFix();
	}

	public Result<Integer> resetTask(String taskType, String fingerprint, int status, int executeCount) {
		com.sprucetec.osc.schedule.MyScheduleTaskProcess<Object> taskProcess = getScheduleTaskProcess(taskType);
		return new Result<>(taskProcess.resetTask(fingerprint, status, executeCount));
	}

	public Result<Integer> executeTask(String taskType, String fingerprint) {

		com.sprucetec.osc.schedule.MyScheduleTaskProcess<Object> taskProcess = getScheduleTaskProcess(taskType);
		TaskResponse<Object> response = taskProcess.queryTaskByFingerprint(fingerprint);
		if (response != null) {
			try {
				taskProcess.executeTask(response.getTaskObject());
				this.scheduleTaskService.doneTask(response);
			} catch (Exception e) {
				logger.error("手动执行任务" + taskType + "出错：" + JSON.toJSONString(response.getTaskObject()), e);
				this.scheduleTaskService.errorTask(response, e);
			}
			return new Result<>(1);
		} else
			return new Result<>(0);
	}

	public Result<Integer> resetTaskByType(String taskType) {
		try {
			int tableFix = this.getTableFix(taskType);
			scheduleTaskService.resetTaskByType(tableFix);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Result<>(1);

	}

	private <T> com.sprucetec.osc.schedule.MyScheduleTaskProcess<T> getScheduleTaskProcess(String taskType) {
		Map<String, com.sprucetec.osc.schedule.MyScheduleTaskProcess> type = SpringContext.getBeansOfType(com.sprucetec.osc.schedule.MyScheduleTaskProcess.class);
		Iterator<com.sprucetec.osc.schedule.MyScheduleTaskProcess> iterator = type.values().iterator();
		while (iterator.hasNext()) {
			com.sprucetec.osc.schedule.MyScheduleTaskProcess taskProcess = iterator.next();
			if (taskProcess.getTaskType().getCode().equals(taskType))
				return taskProcess;
		}
		return null;
	}

}

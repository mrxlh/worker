package com.sprucetec.osc.schedule;


import com.alibaba.fastjson.JSON;
import com.collmall.constant.TaskType;
import com.collmall.constant.WorkerStatus;
import com.collmall.model.ScheduleParam;
import com.collmall.model.TaskRequest;
import com.collmall.model.TaskResponse;
import com.collmall.schedule.AbstractScheduleTaskProcess;
import com.collmall.service.ScheduleTaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  任务进程
 * @param <T>
 * @author  xulihui
 * @date 2019-0125
 */
public abstract class MyScheduleTaskProcess<T> extends AbstractScheduleTaskProcess<TaskResponse<T>> {

	private  Logger logger = Logger.getLogger(AbstractScheduleTaskProcess.class);

	@Autowired
	protected ScheduleTaskService scheduleTaskService;

	public abstract TaskType getTaskType();

	@Override
	protected List<TaskResponse<T>> selectTasks(ScheduleParam param, Integer curServer) {
		return this.scheduleTaskService.queryExecuteTasks(getTaskType().getCode(), param, curServer);
	}

	@Override
	public void executeTasks(ScheduleParam param, List<TaskResponse<T>> tasks){
		this.scheduleTaskService.lockTasks(tasks);
		for (TaskResponse<T> response : tasks) {
			T t = response.getTaskObject();
			try {
				executeTask(t);
				this.scheduleTaskService.doneTask(response);
			} catch (Exception e) {
				logger.error("执行任务" + getTaskType().getCode() + "出错：" + JSON.toJSONString(t), e);
				this.scheduleTaskService.errorTask(response, e);
			}
		}
	}


	/**
	 * 执行一条任务
	 * @param t
	 */
	public abstract void executeTask(T t);

	public TaskResponse<T> queryTaskByFingerprint(String fingerprint) {
		return this.scheduleTaskService.queryTaskByFingerprint(getTaskType().getCode(), fingerprint);
	}

	public void submitTask(T t, String fingerprint) {
		TaskRequest<T> request = new TaskRequest<>();
		request.setTaskType(getTaskType().getCode());
		request.setTaskObject(t);
		request.setFingerPrint(fingerprint);
		submitTask(request);
	}

	public void submitTask(TaskRequest<T> request) {
		request.setTaskType(getTaskType().getCode());
		executeTask(request.getTaskObject());
	}

	/**
	 * 重置任务为初始状态
	 * @param fingerprint
	 * @return 影响条数
	 */
	public int initialTask(String fingerprint) {
		return resetTask(fingerprint, WorkerStatus.INITIAL.getCode(), 0);
	}

	public int resetTask(String fingerprint, int status, int executeCount) {
		TaskResponse<T> response = queryTaskByFingerprint(fingerprint);
		if (response != null) {
			this.scheduleTaskService.resetTask(getTaskType().getCode(), fingerprint, status, executeCount);
			return 1;
		} else
			return 0;
	}


}
